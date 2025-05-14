package ar.edu.unlam.tpi.match_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeolocationResponseDto {
    private Long id;
    private Float lat;
    private Float ln;
} 