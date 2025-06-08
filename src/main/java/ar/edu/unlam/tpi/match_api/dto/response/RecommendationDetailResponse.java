package ar.edu.unlam.tpi.match_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDetailResponse {
    private Long id;
    private String name;
    private String description;
    private String type;
}
