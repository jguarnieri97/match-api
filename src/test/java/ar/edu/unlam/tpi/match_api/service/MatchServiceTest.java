package ar.edu.unlam.tpi.match_api.service;

import ar.edu.unlam.tpi.match_api.client.AccountsClient;
import ar.edu.unlam.tpi.match_api.dto.SupplierResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MatchServiceTest {

    @Mock
    private AccountsClient accountsClient;

    @InjectMocks
    private MatchService matchService;

    @Test
    void shouldReturnListOfSuppliers() {
        // Arrange
        SupplierResponseDto supplier1 = SupplierResponseDto.builder()
                .id(1L)
                .name("AgroFertil")
                .build();

        SupplierResponseDto supplier2 = SupplierResponseDto.builder()
                .id(2L)
                .name("TechSolutions")
                .build();

        List<SupplierResponseDto> expectedSuppliers = List.of(supplier1, supplier2);

        when(accountsClient.getSuppliers()).thenReturn(expectedSuppliers);

        // Act
        List<SupplierResponseDto> actualSuppliers = matchService.getSuppliers();

        // Assert
        assertEquals(expectedSuppliers, actualSuppliers);
    }
}
