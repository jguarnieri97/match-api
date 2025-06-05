package ar.edu.unlam.tpi.match_api.client.impl;

import java.util.List;

import ar.edu.unlam.tpi.match_api.client.error.ErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.unlam.tpi.match_api.client.AccountsClient;
import ar.edu.unlam.tpi.match_api.dto.response.ErrorResponseDto;
import ar.edu.unlam.tpi.match_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierResponseDto;
import ar.edu.unlam.tpi.match_api.utils.annotations.WebHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@WebHttpClient
@RequiredArgsConstructor
public class AccountClientImpl implements AccountsClient{

    private final WebClient webClient;
    private final ErrorHandler errorHandler;

    @Value("${accounts.host}")
    private String host;

    // Usa directamente el host completo y agrega los query params manualmente
    @Override
    public List<SupplierResponseDto> getSuppliers(String category, Float lat, Float ln) {
        StringBuilder url = new StringBuilder(host);
        boolean hasParam = false;
        if (category != null) {
            url.append(hasParam ? "&" : "?").append("category=").append(category);
            hasParam = true;
        }
        if (lat != null) {
            url.append(hasParam ? "&" : "?").append("lat=").append(lat);
            hasParam = true;
        }
        if (ln != null) {
            url.append(hasParam ? "&" : "?").append("ln=").append(ln);
        }

        return webClient.get()
                .uri(url.toString())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(ErrorResponseDto.class)
                                .flatMap(errorHandler::handle4xxError))
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> response.bodyToMono(ErrorResponseDto.class)
                                .flatMap(errorHandler::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<List<SupplierResponseDto>>>() {})
                .map(GenericResponse::getData)
                .block();
    }

}