package pe.edu.cibertec.spring_proyecto.dto;

public record UserDetDto( Integer usuarioId,
         String username,
         String password,
         String email,
         String role) {
}
