package pe.edu.cibertec.spring_proyecto.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pe.edu.cibertec.spring_proyecto.entity.ShoppingCart;

import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ShoppingCart shoppingCart) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/maintenance/login").permitAll()
                        .requestMatchers("/maintenance/start").hasAnyRole("ADMIN", "OPERATOR")
                        .requestMatchers("/maintenance/cart/remove/**").authenticated()
                        .anyRequest().authenticated()
                )

                //redireecionar a una pagina en caso no haya permiso
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((req, resp, excep) -> {
                            resp.sendRedirect("/maintenance/restricted");
                        })
                )

                //configurar formulario de inicio de sesion
                .formLogin(form -> form
                        .loginPage("/maintenance/login")
                        .successHandler(new CustomAuthenticationSuccessHandler())

                        .permitAll()
                )

                //configurar logout
                .logout(logout -> logout
                        .logoutUrl("/maintenance/logout")
                        .logoutSuccessUrl("/maintenance/login?logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler(shoppingCart))  // Usar el handler personalizado
                        .permitAll()
                );
        return http.build();
    }


    /*Clase succesauthhandler para manejar redireccion de inicio de sesion segun tipo de usuario en la bd*/
    private static class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {
            boolean esAdmin = false;
            boolean esOperator = false;
            //boolean esRol = false;

            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    esAdmin = true;
                    break;
                } else if (authority.getAuthority().equals("ROLE_OPERATOR")) {
                    esOperator = true;
                    break;
                }
            }

            if (esAdmin) {
                response.sendRedirect("/maintenance/start"); // Redireccion si es admin
            } else if (esOperator) {
                response.sendRedirect("/maintenance/shop"); // redireccion si es operator
            }  else {
                response.sendRedirect("/maintenance/start"); // Redireccion por default
            }
        }
    }


    /*No quitar*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*Activar si por algun motivo no funciona la conexion a bd y quieran probar algo*/
    /*        @Bean
    public UserDetailsService userDetailsService() {
        return username -> User.builder()
                .username("stephany")
                .password(passwordEncoder().encode("123456"))
                 .roles("ADMIN")
                .build();
    }*/
}
