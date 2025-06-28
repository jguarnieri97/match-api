package ar.edu.unlam.tpi.match_api.service.impl;

import ar.edu.unlam.tpi.match_api.client.AccountsClient;
import ar.edu.unlam.tpi.match_api.client.RecommendationClient;
import ar.edu.unlam.tpi.match_api.dto.response.RecommendationDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.RecommendationResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierResponseDto;
import ar.edu.unlam.tpi.match_api.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchServiceImplTest {

    @Mock
    private RecommendationClient recommendationClient;

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

        when(accountsClient.getSuppliers("cat", 10.0f, 20.0f, "wall_repair")).thenReturn(supplierList);

        // when
        List<SupplierDetailResponse> result = matchService.getSuppliers("cat", 10.0f, 20.0f, "wall_repair");

        // then
        assertEquals(1, result.size());
        assertEquals("Proveedor 1", result.get(0).getName());
    }

    @Test
    void givenRecommendationsExist_whenGetRecommendations_thenReturnSuppliersMapped() {
        // Arrange
        Long applicantId = 1L;
        int limit = 2;
        Float lat = -34.6f;
        Float ln = -58.4f;

        RecommendationResponse reco1 = RecommendationResponse.builder()
                .category("electricidad")
                .label("instalación")
                .build();

        List<RecommendationResponse> recommendations = List.of(reco1);

        SupplierResponseDto supplier1 = SupplierResponseDto.builder()
                .id(1L).name("Proveedor A").build();

        when(recommendationClient.get(applicantId, limit)).thenReturn(recommendations);
        when(accountsClient.getSuppliers("electricidad", lat, ln, "instalación"))
                .thenReturn(List.of(supplier1));

        // Act
        List<RecommendationDetailResponse> result = matchService.getRecommendations(applicantId, limit, lat, ln);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Proveedor A", result.get(0).getName());
    }

    @Test
    void givenNoRecommendations_whenGetRecommendations_thenReturnFallbackSuppliers() {
        // Arrange
        Long applicantId = 2L;
        int limit = 1;
        Float lat = -34.6f;
        Float ln = -58.4f;

        when(recommendationClient.get(applicantId, limit)).thenReturn(Collections.emptyList());

        SupplierResponseDto fallback = SupplierResponseDto.builder()
                .id(2L).name("Fallback Supplier").build();

        when(accountsClient.getSuppliers("CLEANING", lat, ln, null))
                .thenReturn(List.of(fallback));

        // Act
        List<RecommendationDetailResponse> result = matchService.getRecommendations(applicantId, limit, lat, ln);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Fallback Supplier", result.get(0).getName());
    }

    @Test
    void givenSupplierApiFailsForARecommendation_whenGetRecommendations_thenSkipThatRecommendation() {
        // Arrange
        Long applicantId = 3L;
        int limit = 5;
        Float lat = -34.6f;
        Float ln = -58.4f;

        RecommendationResponse reco = RecommendationResponse.builder()
                .category("plomería")
                .label("gas")
                .build();

        when(recommendationClient.get(applicantId, limit)).thenReturn(List.of(reco));
        when(accountsClient.getSuppliers("plomería", lat, ln, "gas"))
                .thenThrow(new NotFoundException("No se encontró"));

        // Act
        List<RecommendationDetailResponse> result = matchService.getRecommendations(applicantId, limit, lat, ln);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
