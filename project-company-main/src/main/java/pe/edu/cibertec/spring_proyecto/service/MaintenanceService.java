package pe.edu.cibertec.spring_proyecto.service;

import pe.edu.cibertec.spring_proyecto.dto.CategoriaDto;
import pe.edu.cibertec.spring_proyecto.dto.ReporteDetailDto;
import pe.edu.cibertec.spring_proyecto.dto.ReporteDto;
import pe.edu.cibertec.spring_proyecto.entity.Reporte;

import java.util.List;
import java.util.Optional;

public interface MaintenanceService {
    List<ReporteDto> findAllReportes();

    Optional<ReporteDto> findRepodById(int id);

    ReporteDetailDto findReporteById(int id);

    Boolean updateReporte(ReporteDetailDto productoDetailDto);

    Boolean deleteReporteById(int id);

    Boolean createReporte(Reporte reporte);

    List<CategoriaDto> getAllCategorias();

}
