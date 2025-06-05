package ar.edu.unlam.tpi.match_api.utils;

import ar.edu.unlam.tpi.match_api.dto.response.GeolocationResponseDto;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierResponseDto;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SupplierConverter {
    public static SupplierDetailResponse toDetailResponse(SupplierResponseDto supplier, Float lat, Float ln) {
        String distance = null;
        GeolocationResponseDto geo = supplier.getGeolocation();
        if (lat != null && ln != null && geo != null && geo.getLat() != null && geo.getLn() != null) {
            double dist = GeoUtils.calcularDistancia(lat, ln, geo.getLat(), geo.getLn());
            distance = String.format("%.2f km", dist);
        }
        return SupplierDetailResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .address(supplier.getAddress())
                .cuit(supplier.getCuit())
                .description(supplier.getDescription())
                .type(supplier.getType())
                .distance(distance)
                .score(supplier.getScore())
                .avgPrice(supplier.getAvgPrice())
                .commentsCount(supplier.getCommentsCount())
                .isVerified(supplier.getIsVerified())
                .build();
    }
}
