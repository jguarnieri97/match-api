//package ar.edu.unlam.tpi.match_api.client.impl;
//
//import ar.edu.unlam.tpi.match_api.client.error.ErrorHandler;
//import ar.edu.unlam.tpi.match_api.dto.response.ErrorResponseDto;
//import ar.edu.unlam.tpi.match_api.dto.response.GenericResponse;
//import ar.edu.unlam.tpi.match_api.dto.response.RecommendationResponse;
//import ar.edu.unlam.tpi.match_api.exceptions.NotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.web.reactive.function.client.*;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_TYPE;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
//
//@ExtendWith(MockitoExtension.class)
//public class RecommendationClientImplTest {
//
//    @Mock
//    private WebClient webClient;
//
//    @Mock
//    private WebClient.RequestHeadersUriSpec<?> uriSpec;
//
//    @Mock
//    private WebClient.RequestHeadersSpec<?> headersSpec;
//
//    @Mock
//    private WebClient.ResponseSpec responseSpec;
//
//    @Mock
//    private ErrorHandler errorHandler;
//
//    private RecommendationClientImpl recommendationClient;
//
//    private static final String HOST = "http://localhost/recommendations";
//
//    @BeforeEach
//    void setUp() {
//        recommendationClient = new RecommendationClientImpl(webClient, errorHandler);
//    }
//
//    @Test
//    void givenValidRequest_whenGet_thenReturnRecommendationList() {
//        Long applicantId = 1L;
//        Integer limit = 3;
//        String url = HOST + "?applicantId=1&limit=3";
//
//        List<RecommendationResponse> recommendations = List.of(
//                RecommendationResponse.builder().applicantId(1L).build()
//        );
//
//        GenericResponse<List<RecommendationResponse>> genericResponse = GenericResponse.<List<RecommendationResponse>>builder()
//                .code(200)
//                .message("OK")
//                .data(recommendations)
//                .build();
//
//        when(webClient.get()).thenReturn(uriSpec);
//        when(uriSpec.uri(url)).thenReturn(headersSpec);
//        when(headersSpec.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)).thenReturn(headersSpec);
//        when(headersSpec.retrieve()).thenReturn(responseSpec);
//        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
//        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
//                .thenReturn(Mono.just(genericResponse));
//
//        List<RecommendationResponse> result = recommendationClient.get(applicantId, limit);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(1L, result.get(0).getApplicantId());
//    }
//
//    @Test
//    void given404Response_whenGet_thenReturnsEmptyList() {
//        Long applicantId = 1L;
//        Integer limit = 3;
//        String url = HOST + "?applicantId=1&limit=3";
//
//        when(webClient.get()).thenReturn(uriSpec);
//        when(uriSpec.uri(url)).thenReturn(headersSpec);
//        when(headersSpec.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)).thenReturn(headersSpec);
//        when(headersSpec.retrieve()).thenReturn(responseSpec);
//        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
//        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
//                .thenReturn(Mono.error(new NotFoundException("No recommendations")));
//
//        List<RecommendationResponse> result = recommendationClient.get(applicantId, limit);
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void given500Response_whenGet_thenThrowsExceptionHandledByHandler() {
//        Long applicantId = 1L;
//        Integer limit = 3;
//        String url = HOST + "?applicantId=1&limit=3";
//
//        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
//                .code(500)
//                .message("Internal Error")
//                .detail("Internal failure")
//                .build();
//
//        when(webClient.get()).thenReturn(uriSpec);
//        when(uriSpec.uri(url)).thenReturn(headersSpec);
//        when(headersSpec.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)).thenReturn(headersSpec);
//        when(headersSpec.retrieve()).thenReturn(responseSpec);
//        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
//        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
//                .thenReturn(Mono.error(new RuntimeException("boom")));
//
//        List<RecommendationResponse> result = recommendationClient.get(applicantId, limit);
//        assertTrue(result.isEmpty());
//    }
//}
