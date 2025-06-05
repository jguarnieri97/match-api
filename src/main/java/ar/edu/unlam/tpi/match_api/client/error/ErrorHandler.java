package ar.edu.unlam.tpi.match_api.client.error;

import ar.edu.unlam.tpi.match_api.dto.response.ErrorResponseDto;
import ar.edu.unlam.tpi.match_api.exceptions.AccountClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ErrorHandler {

    public Mono<Throwable> handle4xxError(ErrorResponseDto error) {
        log.error("Error del cliente externo: {}", error);
        return Mono.error(new AccountClientException(error));
    }

    public Mono<Throwable> handle5xxError(ErrorResponseDto error) {
        log.error("Error del servidor externo: {}", error);
        return Mono.error(new AccountClientException(error));
    }

    public boolean onClientError(Throwable e) {
        log.error("Error al ejecutar el request: {}", e.getMessage());
        throw new AccountClientException(e.getMessage());
    }

}