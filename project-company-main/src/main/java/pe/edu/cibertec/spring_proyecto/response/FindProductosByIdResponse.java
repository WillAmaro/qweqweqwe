package pe.edu.cibertec.spring_proyecto.response;

import pe.edu.cibertec.spring_proyecto.dto.ReporteDetailDto;

public record FindProductosByIdResponse(String code,
                                        String error,
                                        ReporteDetailDto producto) {

}
