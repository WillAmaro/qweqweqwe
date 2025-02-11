package pe.edu.cibertec.spring_proyecto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.spring_proyecto.entity.Categoria;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {



    Optional<Categoria> findById(Integer id);  // Método para buscar por ID




    Optional<Categoria> findByCategoriaNombre(String categoriaNombre);
}
