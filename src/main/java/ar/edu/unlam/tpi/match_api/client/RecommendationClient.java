package ar.edu.unlam.tpi.match_api.client;

import ar.edu.unlam.tpi.match_api.dto.response.RecommendationResponse;

import java.util.List;

public interface RecommendationClient {

    /**
     * Obtiene las recomendaciones para un solicitante.
     * @param applicantId
     * @param limit
     * @return
     */
    List<RecommendationResponse> get(Long applicantId, Integer limit);

}
