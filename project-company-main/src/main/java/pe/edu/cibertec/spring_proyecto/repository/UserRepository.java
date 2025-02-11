package pe.edu.cibertec.spring_proyecto.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.spring_proyecto.entity.Usuario;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Usuario,Integer> {
    Optional<Usuario> findByUsername(String username);
}
