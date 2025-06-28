package ar.edu.unlam.tpi.match_api.client.impl;

import ar.edu.unlam.tpi.match_api.client.RecommendationClient;
import ar.edu.unlam.tpi.match_api.client.error.ErrorHandler;
import ar.edu.unlam.tpi.match_api.dto.response.ErrorResponseDto;
import ar.edu.unlam.tpi.match_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.match_api.dto.response.RecommendationResponse;
import ar.edu.unlam.tpi.match_api.exceptions.NotFoundException;
import ar.edu.unlam.tpi.match_api.utils.annotations.WebHttpClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@WebHttpClient
@RequiredArgsConstructor
public class RecommendationClientImpl implements RecommendationClient {

    private final WebClient webClient;
    private final ErrorHandler errorHandler;

    @Value("${recommendation.host}")
    private String host;

    @Override
    public List<RecommendationResponse> get(Long applicantId, Integer limit) {
        String url = host + "?applicantId=" + applicantId + "&limit=" + limit;

        return webClient.get()
                .uri(url.toString())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        response -> {
                            if (response.statusCode().value() == 404) {
                                return Mono.error(new NotFoundException("No recommendations found for applicantId: " + applicantId));
                            }
                            return response.bodyToMono(ErrorResponseDto.class)
                                    .flatMap(errorHandler::handle4xxError);
                        })
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> response.bodyToMono(ErrorResponseDto.class)
                                .flatMap(errorHandler::handle5xxError))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<List<RecommendationResponse>>>() {})
                .map(GenericResponse::getData)
                .onErrorResume(NotFoundException.class, ex -> Mono.just(Collections.emptyList()))
                .block();
    }
}
