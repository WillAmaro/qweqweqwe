package pe.edu.cibertec.spring_proyecto.dto;

public record ReporteDetailDto(Integer id,
                               String titulo,
                               String descripcion,
                               String prioridad,
                               String autor,
                               Integer estado,
                               String categoriaNombre,
                               Integer categoriaId ){


}
