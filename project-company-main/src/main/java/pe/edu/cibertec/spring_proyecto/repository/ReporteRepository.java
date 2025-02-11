package pe.edu.cibertec.spring_proyecto.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.spring_proyecto.entity.Reporte;

import java.util.Optional;

public interface ReporteRepository extends CrudRepository<Reporte, Integer> {
    Optional<Reporte> findById(Integer id);  // Método para buscar por ID

}
