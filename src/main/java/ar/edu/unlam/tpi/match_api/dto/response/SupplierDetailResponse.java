package ar.edu.unlam.tpi.match_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierDetailResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String cuit;
    private String description;
    private String type;
    private String distance;
    private Float score;
    private Float avgPrice;
    private Integer commentsCount;
    private Boolean isVerified;
}
