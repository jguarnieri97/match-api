package ar.edu.unlam.tpi.match_api.controller.impl;

import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ar.edu.unlam.tpi.match_api.controller.MatchController;
import ar.edu.unlam.tpi.match_api.dto.GenericResponse;
import ar.edu.unlam.tpi.match_api.dto.SupplierResponseDto;
import ar.edu.unlam.tpi.match_api.service.MatchService;
import java.util.List;
import static ar.edu.unlam.tpi.match_api.utils.Constants.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchControllerImpl implements MatchController {
 
    private final MatchService matchService;

    @Override
    public GenericResponse<List<SupplierResponseDto>> getSuppliers() {
        List<SupplierResponseDto> suppliers = matchService.getSuppliers();
        return GenericResponse.<List<SupplierResponseDto>>builder()
                .code(STATUS_OK)
                .data(suppliers)
                .message(SUCCESS_MESSAGE)
                .build();
    }
}