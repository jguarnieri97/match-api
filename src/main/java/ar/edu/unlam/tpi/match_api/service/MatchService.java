package ar.edu.unlam.tpi.match_api.service;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.RecommendationDetailResponse;

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

    /**
     * Obtiene recomendaciones basadas en el ID del solicitante, límite y coordenadas.
     *
     * @param applicantId ID del solicitante.
     * @param limit Límite de recomendaciones a obtener.
     * @param lat Latitud para la ubicación.
     * @param ln Longitud para la ubicación.
     * @return Lista de detalles de recomendaciones.
     */
    public List<RecommendationDetailResponse> getRecommendations(
            Long applicantId,
            int limit,
            Float lat,
            Float ln
    );
}

