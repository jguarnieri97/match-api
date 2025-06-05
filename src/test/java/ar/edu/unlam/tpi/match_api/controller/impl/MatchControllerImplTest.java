package ar.edu.unlam.tpi.match_api.controller.impl;

import ar.edu.unlam.tpi.match_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import ar.edu.unlam.tpi.match_api.service.MatchService;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class MatchControllerImplTest {

    @Test
    @DisplayName("Given category and location, when getSuppliers is called, then return list of suppliers")
    void givenCategoryAndLocation_whenGetSuppliers_thenReturnsListOfSuppliers() {
        // Given
        MatchService matchService = Mockito.mock(MatchService.class);
        MatchControllerImpl controller = new MatchControllerImpl(matchService);

        String category = "comida";
        Float lat = -34.6f;
        Float ln = -58.4f;
        SupplierDetailResponse supplier = SupplierDetailResponse.builder().build();
        List<SupplierDetailResponse> mockSuppliers = List.of(supplier);

        when(matchService.getSuppliers(category, lat, ln)).thenReturn(mockSuppliers);

        // When
        GenericResponse<List<SupplierDetailResponse>> response = controller.getSuppliers(category, lat, ln);

        // Then
        assertThat(response.getCode()).isEqualTo(200);
        assertThat(response.getData()).hasSize(1);
    }
}