package pe.edu.cibertec.spring_proyecto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoriaId;
    private String categoriaNombre;

    @OneToMany(mappedBy = "categoria")
    @ToString.Exclude
    private List<Reporte> reportes;  // Relación con los productos (opcional, si deseas cargar los productos de la categoría)

}
