package pe.edu.cibertec.spring_proyecto.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import pe.edu.cibertec.spring_proyecto.entity.ShoppingCart;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final ShoppingCart shoppingCart;

    public CustomLogoutSuccessHandler(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Limpiar el carrito
        shoppingCart.clear();

        // Redirigir al login con un mensaje de logout exitoso
        response.sendRedirect("/maintenance/login?logout");
    }
}

