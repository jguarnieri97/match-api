package ar.edu.unlam.tpi.match_api.controller.impl;

import ar.edu.unlam.tpi.match_api.dto.response.RecommendationDetailResponse;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierDetailResponse;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ar.edu.unlam.tpi.match_api.controller.MatchController;
import ar.edu.unlam.tpi.match_api.dto.response.GenericResponse;
import ar.edu.unlam.tpi.match_api.service.MatchService;
import java.util.List;
import static ar.edu.unlam.tpi.match_api.utils.Constants.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchControllerImpl implements MatchController {
 
    private final MatchService matchService;

    @Override
    public GenericResponse<List<SupplierDetailResponse>> getSuppliers(String category, Float lat, Float ln) {
        List<SupplierDetailResponse> suppliers = matchService.getSuppliers(category, lat, ln);
        return GenericResponse.<List<SupplierDetailResponse>>builder()
                .code(STATUS_OK)
                .data(suppliers)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public GenericResponse<List<RecommendationDetailResponse>> getRecommendations(Long applicantId, int limit, Float lat, Float ln) {
        return GenericResponse.<List<RecommendationDetailResponse>>builder()
                .code(STATUS_OK)
                .data(matchService.getRecommendations(applicantId, limit, lat, ln))
                .message(SUCCESS_MESSAGE)
                .build();
    }
}