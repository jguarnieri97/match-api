package ar.edu.unlam.tpi.match_api.utils;

import ar.edu.unlam.tpi.match_api.dto.response.RecommendationDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierResponseDto;
import org.modelmapper.ModelMapper;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class Converter {
    
    private static ModelMapper modelMapper = new ModelMapper();

    public static <T> T convertToEntity(Object object, Class<T> entityClass) {
        return modelMapper.map(object, entityClass);
    }

    public static <T> T convertToDto(Object object, Class<T> dtoClass) {
        return modelMapper.map(object, dtoClass);
    }

    public List<RecommendationDetailResponse> toRecommendationDetailList(List<SupplierResponseDto> suppliers) {
        return suppliers.stream()
                .map(supplier -> RecommendationDetailResponse.builder()
                        .id(supplier.getId())
                        .name(supplier.getName())
                        .description(supplier.getDescription())
                        .type(supplier.getType())
                        .build())
                .collect(Collectors.toList());
    }

}
