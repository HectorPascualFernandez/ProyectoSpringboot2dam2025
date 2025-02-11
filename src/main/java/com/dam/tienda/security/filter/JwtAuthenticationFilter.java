    package com.dam.tienda.security.filter;


    import com.dam.tienda.entities.User;
    import com.fasterxml.jackson.core.exc.StreamReadException;
    import com.fasterxml.jackson.databind.DatabindException;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.AuthenticationException;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    import java.io.IOException;
    import java.util.Collection;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;

    import static com.dam.tienda.security.TokenJwtConfig.*;


    public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;




    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;
    }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request,
                                                    HttpServletResponse response)
                throws AuthenticationException {
            User user = null;
            String username = null;
            String password = null;
            try {
                // La clase ObjectMapper para trabajar con JSON, convierte un Stream
                // a un tipo de objeto, en nuestro caso User
                user = new ObjectMapper().readValue(request.getInputStream(), User.class);
                username = user.getUsername();
                System.out.println("authentication attempt: " + username);
                password = user.getPassword();
            } catch (StreamReadException e) {
                e.printStackTrace();
            } catch (DatabindException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
         UsernamePasswordAuthenticationToken token =
                 new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(token);
        }

        @Override
        protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Se ejecuta este método cuando la autenticación es exitosa
           // Se hace el casting de Spring Security, posible error si se
            // importa de nuestro proyecto
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                    authResult.getPrincipal();

            String username= user.getUsername();
            System.out.println("username:"+username);

            Collection <? extends GrantedAuthority> roles= authResult.getAuthorities();

            // Recogemos los roles
            // Claims hereda de Map de Java
            Claims claims = Jwts.claims()
                    // Pasamos los roles como un JSON
                    .add("authorities", new ObjectMapper().writeValueAsString(roles))
                    .add("username", username)
                    .build();


            String token = Jwts.builder()
                    .subject(username)
                    .claims(claims)
                    .expiration(new Date(System.currentTimeMillis()+3600000))
                    .issuedAt(new Date())
                    .signWith(SECRET_KEY)
                    .compact();
            response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);
            Map<String,String> body = new HashMap<>();
            body.put("token", token);
            body.put("username", username);
            body.put("mensaje", String.format("Hola has iniciado sesión con exito: %s", username));
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setContentType(CONTENT_TYPE);
            response.setStatus(200);

        }

        @Override
        protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
            // Se ejecuta este método cuando la autenticación no es exitosa
            Map<String,String> body = new HashMap<>();
            body.put("message", "Error en la autenticación usuario o password incorrecto!");
            body.put("error",failed.getMessage() );
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401);
            response.setContentType(CONTENT_TYPE);

        }
    }
