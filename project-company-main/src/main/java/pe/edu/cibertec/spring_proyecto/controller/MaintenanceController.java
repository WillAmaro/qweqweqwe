package pe.edu.cibertec.spring_proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.spring_proyecto.dto.CategoriaDto;
import pe.edu.cibertec.spring_proyecto.dto.ReporteDetailDto;
import pe.edu.cibertec.spring_proyecto.dto.ReporteDto;
import pe.edu.cibertec.spring_proyecto.entity.Reporte;
import pe.edu.cibertec.spring_proyecto.entity.ShoppingCart;
import pe.edu.cibertec.spring_proyecto.repository.CategoriaRepository;
import pe.edu.cibertec.spring_proyecto.service.MaintenanceService;

import java.util.List;

@SessionAttributes("shoppingCart")
@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ShoppingCart shoppingCart;


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/restricted")
    public String restricted(Model model) {
        return "restricted";
    }





    /* LISTA GENERAL REPORTE */
    @GetMapping("/start")
    public String start(Model model) {
        List<ReporteDto> reportes = maintenanceService.findAllReportes();
        model.addAttribute("reportes", reportes);
        return "maintenance";
    }

    /* DETALLE REPORTE */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        ReporteDetailDto reporteDetailDto = maintenanceService.findReporteById(id);
        model.addAttribute("reporte", reporteDetailDto);
        return "maintenance_detail";
    }

    /* EDITAR REPORTE  */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        ReporteDetailDto reporteDetailDto = maintenanceService.findReporteById(id); // Obtienes el DTO con los datos del producto
        List<CategoriaDto> categorias = maintenanceService.getAllCategorias(); // Obtienes las categorías

        model.addAttribute("reporte", reporteDetailDto); // Le pasas el DTO al modelo
        model.addAttribute("categorias", categorias); // Y las categorías al modelo
        return "maintenance_edit";  // Vista de edición
    }

    /* MODAL CONFIRMAR EDITAR */
    @PostMapping("/edit-confirm")
    public String editConfirm(@ModelAttribute ReporteDetailDto reporteDetailDto, Model model) {
        System.out.println("Datos recibidos del formulario: " + reporteDetailDto);

        maintenanceService.updateReporte(reporteDetailDto);
        return "redirect:/maintenance/start";
    }

    /* MODAL ELIMIAR REPORTE */
    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Integer id, Model model) {
        boolean isDeleted = maintenanceService.deleteReporteById(id);
        if (isDeleted) {
            return "redirect:/maintenance/start";  // Redirige a la página de inicio o listado de productos
        } else {
            model.addAttribute("errorMessage", "Reporte no encontrado o no pudo ser eliminado.");
            return "maintenance";
        }
    }

    /* CREAR REPORTE */
    @GetMapping("/create")
    public String showCreateReportForm(Model model) {
        List<CategoriaDto> categorias = maintenanceService.getAllCategorias(); // Obtienes las categorías
        model.addAttribute("reporte", new Reporte()); // Asegúrate de pasar un objeto vacío para el formulario
        model.addAttribute("categorias", categorias); // Pasar las categorías al formulario
        return "maintenance_create"; // El nombre de la vista
    }

    /* MODAL CREAR REPORTE */
    @PostMapping("/create-confirm")
    public String createConfirm(@ModelAttribute Reporte reporte, Model model) {
        boolean isCreated = maintenanceService.createReporte(reporte);
        if (isCreated) {
            return "redirect:/maintenance/start";  // Redirige al listado de productos (ajusta esta URL si es necesario)
        } else {
            model.addAttribute("error", "Error al crear el producto.");
            return "maintenance_create";  // Vuelve al formulario con el error
        }
    }

    /* EXTRA FUNCIONES */
    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Integer id, Model model) {
        ReporteDetailDto reporteDetailDto = maintenanceService.findReporteById(id); // Obtienes el DTO con los datos del producto
        List<CategoriaDto> categorias = maintenanceService.getAllCategorias(); // Obtienes las categorías

        model.addAttribute("producto", reporteDetailDto); // Le pasas el DTO al modelo
        model.addAttribute("categorias", categorias);
        return "cart_item";
    }

    @PostMapping("/cart-confirm")
    public String viewC(@ModelAttribute Reporte reportex, Model model) {
        ReporteDetailDto reporte = maintenanceService.findReporteById(reportex.getId());
        shoppingCart.addItem(reporte);

        System.out.println("Datos recibidos del formulario: " + shoppingCart);
        model.addAttribute("categorias", shoppingCart);

        return "redirect:/maintenance/cart";
    }

    // Metodo para ver el carrito de compras
    @GetMapping("/cart")
    public String viewCart(Model model) {
        // Pasar los items del carrito a la vista
        model.addAttribute("cartItems", shoppingCart.getItems());
        model.addAttribute("total", shoppingCart.getTotal()); // Asegúrate de tener un metodo getTotal() en ShoppingCart
        return "shoping_cart"; // Nombre de la vista para mostrar el carrito
    }

    // Metodo para limpiar el carrito de compras
    @GetMapping("/cart/clear")
    public String clearCart() {
        shoppingCart.clear(); // Limpiar el carrito
        return "redirect:/maintenance/cart"; // Redirigir al carrito vacío
    }

    @GetMapping("/shop")
    public String operator(Model model) {
        List<ReporteDto> reportes = maintenanceService.findAllReportes();
        model.addAttribute("reportes", reportes);
        return "shop";
    }

    // Metodo para eliminar un producto específico del carrito
    @GetMapping("/cart/remove/{id}")
    public String removeItem(@PathVariable Integer id) {
        shoppingCart.removeCartItemById(id); // Metodo para eliminar el ítem
        return "redirect:/maintenance/cart"; // Redirige al carrito actualizado
    }

}
