package ar.edu.unlam.tpi.match_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


import ar.edu.unlam.tpi.match_api.dto.GenericResponse;
import ar.edu.unlam.tpi.match_api.dto.SupplierResponseDto;
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
    GenericResponse<List<SupplierResponseDto>> getSuppliers();
}
