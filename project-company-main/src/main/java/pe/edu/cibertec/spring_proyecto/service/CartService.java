package pe.edu.cibertec.spring_proyecto.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    public void clearCart(HttpSession session) {
        // Limpiar el carrito de la sesión
        session.removeAttribute("shoppingCart"); // Eliminar el atributo "shoppingCart" de la sesión
    }
}
