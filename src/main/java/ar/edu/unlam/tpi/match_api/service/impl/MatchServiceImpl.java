package ar.edu.unlam.tpi.match_api.service.impl;

import org.springframework.stereotype.Service;

import ar.edu.unlam.tpi.match_api.client.AccountsClient;
import ar.edu.unlam.tpi.match_api.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ar.edu.unlam.tpi.match_api.dto.SupplierResponseDto;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    // Implement the methods defined in the SupplierService interface here
    private final AccountsClient accountsClient;

    @Override
    public List<SupplierResponseDto> getSuppliers() {        
        return accountsClient.getSuppliers();
    }

}
