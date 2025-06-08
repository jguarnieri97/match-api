package ar.edu.unlam.tpi.match_api.client;

import java.util.List;
import ar.edu.unlam.tpi.match_api.dto.response.SupplierResponseDto;

/**
 * Interfaz para el cliente de accounts
 */
public interface AccountsClient {

    /**
     * Obtener todos los proveedores
     *
     * @return Lista de proveedores
     */
    List<SupplierResponseDto> getSuppliers(String category, Float lat, Float ln);
}
