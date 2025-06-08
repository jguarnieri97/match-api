package ar.edu.unlam.tpi.match_api.exceptions;

import ar.edu.unlam.tpi.match_api.dto.response.ErrorResponseDto;

import static ar.edu.unlam.tpi.match_api.utils.Constants.INTERNAL_ERROR;
import static ar.edu.unlam.tpi.match_api.utils.Constants.STATUS_INTERNAL;

public class RecommendationClientException extends GenericException {
    public RecommendationClientException(ErrorResponseDto errorResponseDto){
        super(errorResponseDto.getCode(), errorResponseDto.getMessage(), errorResponseDto.getDetail());
    }
    public RecommendationClientException(String detail) {
        super(STATUS_INTERNAL, INTERNAL_ERROR, detail);
    }
}
