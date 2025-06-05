package ar.edu.unlam.tpi.match_api.service;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;

import java.util.List;

/**
 * Servicio para la gestión de proveedores.
 */
public interface MatchService {
    /**
     * Obtiene la lista de proveedores.
     * @return Lista de proveedores.
     */
    public List<SupplierDetailResponse> getSuppliers(String category, Float lat, Float ln);
}
