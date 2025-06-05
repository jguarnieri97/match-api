package ar.edu.unlam.tpi.match_api.service.impl;

import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import ar.edu.unlam.tpi.match_api.utils.SupplierConverter;
import org.springframework.stereotype.Service;
import ar.edu.unlam.tpi.match_api.client.AccountsClient;
import ar.edu.unlam.tpi.match_api.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierResponseDto;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final AccountsClient accountsClient;

    @Override
    public List<SupplierDetailResponse> getSuppliers(String category, Float lat, Float ln) {
        List<SupplierResponseDto> suppliersResponse = accountsClient.getSuppliers(category, lat, ln);
        return suppliersResponse.stream()
                .map(s -> SupplierConverter.toDetailResponse(s, lat, ln))
                .toList();
    }

}
