package ar.edu.unlam.tpi.match_api.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.unlam.tpi.match_api.client.AccountsClient;
import ar.edu.unlam.tpi.match_api.dto.ErrorResponseDto;
import ar.edu.unlam.tpi.match_api.dto.GenericResponse;
import ar.edu.unlam.tpi.match_api.dto.SupplierResponseDto;
import ar.edu.unlam.tpi.match_api.exceptions.AccountClientException;
import ar.edu.unlam.tpi.match_api.utils.annotations.WebHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@WebHttpClient
@RequiredArgsConstructor
public class AccountClientImpl implements AccountsClient{

    private final WebClient webClient;

    @Value("${accounts.host}")
    private String host;

    @Override
    public List<SupplierResponseDto> getSuppliers(){
        return webClient.get()
                .uri(host)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                    response-> response.bodyToMono(ErrorResponseDto.class)
                        .flatMap(AccountClientImpl::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                    response-> response.bodyToMono(ErrorResponseDto.class)
                        .flatMap(AccountClientImpl::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<List<SupplierResponseDto>>>(){})
                .map(GenericResponse::getData)
                .block();
    }

    private static Mono<Throwable> handle4xxError(ErrorResponseDto error){
        log.error("Error del cliente externo de Accounts API: {}", error);
        return Mono.error(new AccountClientException(error));  
    }

    private static Mono<Throwable> handle5xxError(ErrorResponseDto error){
        log.error("Error del servidor externo de Accounts API: {}", error);
        return Mono.error(new AccountClientException(error));
    }
    
}