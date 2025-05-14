package ar.edu.unlam.tpi.match_api.utils;

import org.modelmapper.ModelMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Converter {
    
    private static ModelMapper modelMapper = new ModelMapper();

    public static <T> T convertToEntity(Object object, Class<T> entityClass) {
        return modelMapper.map(object, entityClass);
    }

    public static <T> T convertToDto(Object object, Class<T> dtoClass) {
        return modelMapper.map(object, dtoClass);
    }
}
