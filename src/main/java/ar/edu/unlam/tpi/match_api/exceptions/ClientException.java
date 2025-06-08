package ar.edu.unlam.tpi.match_api.exceptions;

import ar.edu.unlam.tpi.match_api.dto.response.ErrorResponseDto;

import static ar.edu.unlam.tpi.match_api.utils.Constants.INTERNAL_ERROR;
import static ar.edu.unlam.tpi.match_api.utils.Constants.STATUS_INTERNAL;

public class ClientException extends GenericException {
    public ClientException(ErrorResponseDto error) {
        super(error.getCode(), error.getMessage(), error.getDetail());
    }

    public ClientException(String detail) {
        super(STATUS_INTERNAL, INTERNAL_ERROR, detail);
    }
}
