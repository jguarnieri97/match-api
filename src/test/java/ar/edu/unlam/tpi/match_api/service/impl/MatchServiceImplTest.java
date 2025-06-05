package ar.edu.unlam.tpi.match_api.service.impl;

import ar.edu.unlam.tpi.match_api.client.AccountsClient;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchServiceImplTest {

    @Mock
    private AccountsClient accountsClient;

    @InjectMocks
    private MatchServiceImpl matchService;

    @Test
    void givenSuppliersFromClient_whenGetSuppliers_thenReturnSupplierDetailResponses() {
        // given
        SupplierResponseDto supplier = SupplierResponseDto.builder()
                .id(1L)
                .name("Proveedor 1")
                .build();
        List<SupplierResponseDto> supplierList = List.of(supplier);

        when(accountsClient.getSuppliers("cat", 10.0f, 20.0f)).thenReturn(supplierList);

        // when
        List<SupplierDetailResponse> result = matchService.getSuppliers("cat", 10.0f, 20.0f);

        // then
        assertEquals(1, result.size());
        assertEquals("Proveedor 1", result.get(0).getName());
    }

}
