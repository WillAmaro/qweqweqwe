package pe.edu.cibertec.spring_proyecto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.cibertec.spring_proyecto.dto.CategoriaDto;
import pe.edu.cibertec.spring_proyecto.dto.ReporteDetailDto;
import pe.edu.cibertec.spring_proyecto.dto.ReporteDto;
import pe.edu.cibertec.spring_proyecto.entity.Categoria;
import pe.edu.cibertec.spring_proyecto.entity.Reporte;
import pe.edu.cibertec.spring_proyecto.repository.CategoriaRepository;
import pe.edu.cibertec.spring_proyecto.repository.ReporteRepository;
import pe.edu.cibertec.spring_proyecto.service.MaintenanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public List<ReporteDto> findAllReportes() {

        List<ReporteDto> reportes = new ArrayList<ReporteDto>();
        Iterable<Reporte> iterable = reporteRepository.findAll();
        iterable.forEach(reporte -> {
            ReporteDto reporteDto = new ReporteDto(
                    reporte.getId(),
                    reporte.getTitulo(),
                    reporte.getDescripcion(),
                    reporte.getPrioridad(),
                    reporte.getAutor()
            );
            reportes.add(reporteDto);
        });
        return reportes;
    }

    public Optional<ReporteDto> findRepodById(int id) {

        Optional<Reporte> optional = reporteRepository.findById(id);
        return optional.map(reporte -> new ReporteDto(
                reporte.getId(),
                reporte.getTitulo(),
                reporte.getDescripcion(),
                reporte.getPrioridad(),
                reporte.getAutor()));
                // Lanzar una excepción si no se encuentra
    }


    @Override
    public ReporteDetailDto findReporteById(int id) {
        Optional<Reporte> optional = reporteRepository.findById(id);
        return optional.map(reporte -> new ReporteDetailDto(
                reporte.getId(),
                reporte.getTitulo(),
                reporte.getDescripcion(),
                reporte.getPrioridad(),
                reporte.getAutor(),
                reporte.getEstado(),
                reporte.getCategoria().getCategoriaNombre(),
                reporte.getCategoria().getCategoriaId()
        )).orElseThrow(() -> new RuntimeException("Reporte no encontrado")); // Lanzar una excepción si no se encuentra
    }

    @Transactional
    @Override
    public Boolean updateReporte(ReporteDetailDto reporteDetailDto) {
        // Buscar el reporte en el repositorio por su ID
        Optional<Reporte> optionalReport = reporteRepository.findById(reporteDetailDto.id());
        if (!optionalReport.isPresent()) {
            // Si el producto no se encuentra, retornamos false
            return false;
        }

        // Buscar la categoría en el repositorio por su nombre
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(reporteDetailDto.categoriaId());

        System.out.println("categoria: " + optionalCategoria);


        if (!optionalCategoria.isPresent()) {
            // Si la categoría no se encuentra, retornamos false
            return false;
        }





        // Obtener el reporte y actualizar sus valores
        Reporte reporte = optionalReport.get();
        reporte.setTitulo(reporteDetailDto.titulo());
        reporte.setDescripcion(reporteDetailDto.descripcion());
        reporte.setPrioridad(reporteDetailDto.prioridad());
        Categoria categoria = optionalCategoria.get();
        reporte.setCategoria(categoria);

        // Agrega las líneas de debug para verificar los valores antes de guardar
        System.out.println("Reporte a guardar: " + reporte);

        // Guardar el producto actualizado en el repositorio
        reporteRepository.save(reporte);

        // Agrega una línea después de guardar para confirmar que se guardó
        System.out.println("Reporte guardado correctamente");

        // Retornar true para indicar que la actualización fue exitosa
        return true;
    }



    @Transactional
    @Override
    public Boolean deleteReporteById(int id) {
        Optional<Reporte> productoOptional = reporteRepository.findById(id);

        if (productoOptional.isPresent()) {
            reporteRepository.deleteById(id);
            return true;  // reporte eliminado exitosamente
        } else {
            return false;  // reporte no encontrado
        }
    }
    @Override
    public Boolean createReporte(Reporte reporte) {
        try {
            // Se guarda el reporte en la base de datos
            reporteRepository.save(reporte);
            return true;  // Indicar que el reporte fue creado con éxito
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Indicar que hubo un error
        }
    }

    @Override
    public List<CategoriaDto> getAllCategorias() {
        List<CategoriaDto> categorias = new ArrayList<>();
        Iterable<Categoria> iterable = categoriaRepository.findAll();
        iterable.forEach(categoria -> {
            // Creamos el DTO solo con el ID y el nombre de la categoría
            CategoriaDto categoriaDto = new CategoriaDto(
                    categoria.getCategoriaId(),
                    categoria.getCategoriaNombre()
            );
            categorias.add(categoriaDto);
        });
        return categorias;
    }



}



