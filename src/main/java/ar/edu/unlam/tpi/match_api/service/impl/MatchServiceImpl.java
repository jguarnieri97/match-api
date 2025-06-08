package ar.edu.unlam.tpi.match_api.service.impl;

import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
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

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final AccountsClient accountsClient;
    private final RecommendationClient recommendationClient;

    @Override
    public List<SupplierDetailResponse> getSuppliers(String category, Float lat, Float ln) {
        List<SupplierResponseDto> suppliersResponse = accountsClient.getSuppliers(category, lat, ln);
        return suppliersResponse.stream()
                .map(s -> SupplierConverter.toDetailResponse(s, lat, ln))
                .toList();
    }

    @Override
    public List<RecommendationDetailResponse> getRecommendations(Long applicantId, int limit, Float lat, Float ln) {
        List<RecommendationResponse> recommendations = recommendationClient.get(applicantId, limit);

        if (recommendations.isEmpty()) {
            recommendations = List.of(
                    RecommendationResponse.builder().category("ELECTRICIAN").build(),
                    RecommendationResponse.builder().category("CONTRACTOR").build(),
                    RecommendationResponse.builder().category("CLEANING").build()
            );
        }

        List<SupplierResponseDto> result = new ArrayList<>();

        for (RecommendationResponse recommendation : recommendations) {
            List<SupplierResponseDto> suppliers = accountsClient.getSuppliers(recommendation.getCategory(), lat, ln);
            if (!suppliers.isEmpty()) {
                result.add(suppliers.get(0));
            }
        }
        return Converter.toRecommendationDetailList(result);
    }


}
