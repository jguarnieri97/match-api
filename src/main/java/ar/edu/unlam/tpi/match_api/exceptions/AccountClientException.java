package ar.edu.unlam.tpi.match_api.exceptions;

import ar.edu.unlam.tpi.match_api.dto.ErrorResponseDto;

public class AccountClientException extends GenericException {
    public AccountClientException(ErrorResponseDto errorResponseDto){
        super(errorResponseDto.getCode(), errorResponseDto.getMessage(), errorResponseDto.getDetail());
    }
}
