package pe.edu.cibertec.spring_proyecto.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.spring_proyecto.dto.ReporteDetailDto;
import pe.edu.cibertec.spring_proyecto.dto.ReporteDto;
import pe.edu.cibertec.spring_proyecto.response.FindProductosByIdResponse;
import pe.edu.cibertec.spring_proyecto.response.FindProductosResponse;
import pe.edu.cibertec.spring_proyecto.response.UpdateProductosResponse;
import pe.edu.cibertec.spring_proyecto.service.MaintenanceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/manage-producto")
public class ManageApi {

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/all")
    public FindProductosResponse findReportes(@RequestParam(value = "id", defaultValue = "0") String id) {
        try {
            if (Integer.parseInt(id) > 0) {
                Optional<ReporteDto> optional = maintenanceService.findRepodById(Integer.parseInt(id));
                if (optional.isPresent()) {
                    ReporteDto reporteDto = optional.get();
                    return new FindProductosResponse("01", "", List.of(reporteDto));
                } else {
                    return new FindProductosResponse("02", "No se encontro producto", null);
                }
            } else {
                List<ReporteDto> reportes = maintenanceService.findAllReportes();
                if (!reportes.isEmpty())
                    return new FindProductosResponse("01", "", reportes);
                else
                    return new FindProductosResponse("03", "No se encontro producto", reportes);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new FindProductosResponse("99", "Error en el servicio", null);
        }
    }


    @GetMapping("/detail")
    public FindProductosByIdResponse findProductoById(@RequestParam(value = "id", defaultValue = "0") String id) {
        try {
            if (Integer.parseInt(id) > 0) {
                Optional<ReporteDetailDto> optional = Optional.ofNullable(maintenanceService.findReporteById(Integer.parseInt(id)));
                if (optional.isPresent()) {
                    ReporteDetailDto productoDetailDto = optional.get();
                    return new FindProductosByIdResponse("01", "", productoDetailDto);
                } else {
                    return new FindProductosByIdResponse("02", "No se encontro producto", null);
                }
            } else {
                return new FindProductosByIdResponse("03", "No se encontro producto", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new FindProductosByIdResponse("99", "Error en el servicio", null);
        }
    }

    @PostMapping("/update")
    public UpdateProductosResponse updateProducto(@RequestBody ReporteDetailDto productoDetailDto) {
        try {
                if (maintenanceService.updateReporte(productoDetailDto)){
                    return new UpdateProductosResponse("01", "");
                } else {
                    return new UpdateProductosResponse("02", "No se encontro producto");
                }
        } catch (Exception e) {
            e.printStackTrace();
            return new UpdateProductosResponse("99", "Error en el servicio");
        }
    }


}