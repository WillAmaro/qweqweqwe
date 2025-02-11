package pe.edu.cibertec.spring_proyecto.response;

import pe.edu.cibertec.spring_proyecto.dto.ReporteDto;

public record FindProductosResponse(String code,
                                    String error,
                                    Iterable<ReporteDto> reportes) {

}
