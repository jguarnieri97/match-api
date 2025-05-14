package ar.edu.unlam.tpi.match_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class WorkerResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;   
    private String address;
    private SupplierResponseDto company;
}
