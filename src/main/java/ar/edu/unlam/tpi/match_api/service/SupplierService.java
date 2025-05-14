package ar.edu.unlam.tpi.match_api.service;
import ar.edu.unlam.tpi.match_api.dto.SupplierResponseDto;
import java.util.List;

public interface SupplierService {
    public List<SupplierResponseDto> getSuppliers();
}
