package pe.edu.cibertec.spring_proyecto.entity;

import lombok.Data;
import org.springframework.stereotype.Component;
import pe.edu.cibertec.spring_proyecto.dto.ReporteDetailDto;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class ShoppingCart {
    private List<CartItem> items = new ArrayList<>();
    public List<CartItem> getItems() {
        return items;
    }


    //AGREGAR AL CARRITO
    public void addItem(ReporteDetailDto producto) {
        // Busca si ya existe el producto en el carrito
        for (CartItem item : items) {
            if (item.getProducto().id().equals(producto.id())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        // Si no existe, lo agrega
        items.add(new CartItem(producto, 1));
    }

    public void removeCartItemById(Integer id) {
        if (items == null || items.isEmpty()) return; // Validación de lista vacía
        items.removeIf(cartItem -> cartItem.getProducto() != null &&
                id.equals(cartItem.getProducto().id())); // Usar id()
    }


    // Metodo para limpiar el carrito
    public void clear() {
        items.clear(); // Elimina todos los elementos del carrito
    }

    //mostart el total de
    public double getTotal() {
        return 0.0;


        /*return items.stream()
                .mapToDouble(item -> item.getProducto().precioU() * item.getQuantity())
                .sum();*/
    }


    //removerTodoslosProductosDeLaLISTA


}

@Data
class CartItem {
    private ReporteDetailDto producto; // Producto asociado al carrito
    private int quantity;

    public CartItem(ReporteDetailDto producto, int quantity) {
        this.producto = producto;
        this.quantity = quantity;
    }

    public ReporteDetailDto getProducto() {
        return producto;
    }

    public void setProducto(ReporteDetailDto producto) {
        this.producto = producto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

