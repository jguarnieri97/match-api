package ar.edu.unlam.tpi.match_api.controller;

import ar.edu.unlam.tpi.match_api.dto.response.RecommendationDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ar.edu.unlam.tpi.match_api.dto.response.GenericResponse;

import java.util.List;

/**
 * Controlador de matching de proveedores.
 */
@RequestMapping("/match-api/v1")
public interface MatchController {
    

    /**
     * Obtiene la lista de proveedores.
     * @return Lista de proveedores.
     */
    @GetMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all suppliers")
    GenericResponse<List<SupplierDetailResponse>> getSuppliers(@RequestParam(required = false) String category,
                                                               @RequestParam(required = false) Float lat,
                                                               @RequestParam(required = false) Float ln,
                                                               @RequestParam(required = false) String workResume);

    @GetMapping("/recommendations")
    @ResponseStatus(HttpStatus.OK)
    GenericResponse<List<RecommendationDetailResponse>> getRecommendations(
            @RequestParam Long applicantId,
            @RequestParam(required = false, defaultValue = "3") int limit,
            @RequestParam Float lat,
            @RequestParam Float ln
    );
}
