package ar.edu.unlam.tpi.match_api.controller.impl;

import ar.edu.unlam.tpi.match_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.match_api.dto.response.RecommendationDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import ar.edu.unlam.tpi.match_api.service.MatchService;
import ar.edu.unlam.tpi.match_api.utils.Constants;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchControllerImplTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchControllerImpl matchController;

    @Test
    void givenValidParams_whenGetSuppliers_thenReturnGenericResponseWithList() {
        // Given
        String category = "electricidad";
        Float lat = -34.6f;
        Float ln = -58.4f;
        String workResume = "instalación de cableado";

        List<SupplierDetailResponse> mockSuppliers = List.of(
                SupplierDetailResponse.builder().id(1L).name("Carlos").build(),
                SupplierDetailResponse.builder().id(2L).name("Lucía").build()
        );

        when(matchService.getSuppliers(category, lat, ln, workResume)).thenReturn(mockSuppliers);

        // When
        GenericResponse<List<SupplierDetailResponse>> result =
                matchController.getSuppliers(category, lat, ln, workResume);

        // Then
        assertNotNull(result);
        assertEquals(Constants.STATUS_OK, result.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, result.getMessage());
        assertEquals(2, result.getData().size());
        assertEquals("Carlos", result.getData().get(0).getName());
        verify(matchService).getSuppliers(category, lat, ln, workResume);
    }

    @Test
    void givenValidParams_whenGetRecommendations_thenReturnGenericResponseWithList() {
        // Given
        Long applicantId = 99L;
        int limit = 3;
        Float lat = -34.6f;
        Float ln = -58.4f;

        List<RecommendationDetailResponse> mockRecommendations = List.of(
                RecommendationDetailResponse.builder().id(10L).build()
        );

        when(matchService.getRecommendations(applicantId, limit, lat, ln)).thenReturn(mockRecommendations);

        // When
        GenericResponse<List<RecommendationDetailResponse>> result =
                matchController.getRecommendations(applicantId, limit, lat, ln);

        // Then
        assertNotNull(result);
        assertEquals(Constants.STATUS_OK, result.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, result.getMessage());
        assertEquals(1, result.getData().size());
        assertEquals(10L, result.getData().get(0).getId());
        verify(matchService).getRecommendations(applicantId, limit, lat, ln);
    }
}