package ar.edu.unlam.tpi.match_api.service.impl;

import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import ar.edu.unlam.tpi.match_api.utils.CompanyTypeEnum;
import ar.edu.unlam.tpi.match_api.utils.Converter;
import ar.edu.unlam.tpi.match_api.utils.SupplierConverter;
import ar.edu.unlam.tpi.match_api.client.RecommendationClient;
import ar.edu.unlam.tpi.match_api.dto.response.RecommendationDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.RecommendationResponse;
import org.springframework.stereotype.Service;
import ar.edu.unlam.tpi.match_api.client.AccountsClient;
import ar.edu.unlam.tpi.match_api.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierResponseDto;
import ar.edu.unlam.tpi.match_api.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final AccountsClient accountsClient;
    private final RecommendationClient recommendationClient;

    @Override
    public List<SupplierDetailResponse> getSuppliers(String category, Float lat, Float ln, String workResume) {
        List<SupplierResponseDto> suppliersResponse = accountsClient.getSuppliers(category, lat, ln, workResume);
        return suppliersResponse.stream()
                .map(s -> SupplierConverter.toDetailResponse(s, lat, ln))
                .toList();
    }

    @Override
    public List<RecommendationDetailResponse> getRecommendations(Long applicantId, int limit, Float lat, Float ln) {
        List<RecommendationResponse> recommendations = recommendationClient.get(applicantId, limit);

        List<SupplierResponseDto> result = new ArrayList<>();

        if (recommendations.isEmpty()) {
            List<SupplierResponseDto> fallbackSuppliers = accountsClient.getSuppliers(
                    CompanyTypeEnum.CLEANING.name(), lat, ln, null
            );
            int max = limit > 0 ? Math.min(limit, fallbackSuppliers.size()) : fallbackSuppliers.size();
            result.addAll(fallbackSuppliers.subList(0, max));
        } else {
            for (RecommendationResponse recommendation : recommendations) {
                if (limit > 0 && result.size() >= limit) break;

                try {
                    List<SupplierResponseDto> suppliers = accountsClient.getSuppliers(
                            recommendation.getCategory(), lat, ln, recommendation.getLabel()
                    );

                    for (SupplierResponseDto supplier : suppliers) {
                        if (limit > 0 && result.size() >= limit) break;
                        result.add(supplier);
                    }
                } catch (NotFoundException e) {
                    log.warn("⚠️ No se encontraron proveedores para tag '{}'", recommendation.getLabel());
                    // simplemente continúa con la siguiente recomendación
                }
            }
        }
        return Converter.toRecommendationDetailList(result);
    }

}
