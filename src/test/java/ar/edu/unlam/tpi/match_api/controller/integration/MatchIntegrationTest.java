package ar.edu.unlam.tpi.match_api.controller.integration;

import ar.edu.unlam.tpi.match_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.match_api.dto.response.RecommendationDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import ar.edu.unlam.tpi.match_api.service.MatchService;
import ar.edu.unlam.tpi.match_api.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import static org.mockito.Mockito.mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MatchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MatchService matchService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public MatchService matchService() {
            return mock(MatchService.class);
        }
    }

    @Test
    void givenValidParams_whenGetSuppliers_thenReturnsMockedSupplierList() throws Exception {
        // given
        String category = "electricidad";
        Float lat = -34.6f;
        Float ln = -58.4f;
        String workResume = "cambio de cableado";

        List<SupplierDetailResponse> mockSuppliers = List.of(
                SupplierDetailResponse.builder().id(1L).name("Juan").build(),
                SupplierDetailResponse.builder().id(2L).name("Pedro").build()
        );

        when(matchService.getSuppliers(category, lat, ln, workResume)).thenReturn(mockSuppliers);

        // when
        MvcResult result = mockMvc.perform(get("/match-api/v1/find")
                        .param("category", category)
                        .param("lat", lat.toString())
                        .param("ln", ln.toString())
                        .param("workResume", workResume)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        GenericResponse<List<SupplierDetailResponse>> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(
                        GenericResponse.class,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, SupplierDetailResponse.class)
                )
        );

        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
    }

    @Test
    void givenValidParams_whenGetRecommendations_thenReturnsMockedRecommendations() throws Exception {
        // given
        Long applicantId = 10L;
        int limit = 5;
        Float lat = -34.6f;
        Float ln = -58.4f;

        List<RecommendationDetailResponse> mockRecs = List.of(
                RecommendationDetailResponse.builder().id(1L).build()
        );

        when(matchService.getRecommendations(applicantId, limit, lat, ln)).thenReturn(mockRecs);

        // when
        MvcResult result = mockMvc.perform(get("/match-api/v1/recommendations")
                        .param("applicantId", applicantId.toString())
                        .param("limit", String.valueOf(limit))
                        .param("lat", lat.toString())
                        .param("ln", ln.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        GenericResponse<List<RecommendationDetailResponse>> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructParametricType(
                        GenericResponse.class,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, RecommendationDetailResponse.class)
                )
        );

        assertEquals(Constants.STATUS_OK, response.getCode());
        assertEquals(Constants.SUCCESS_MESSAGE, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals(1L, response.getData().get(0).getId());
    }
}
