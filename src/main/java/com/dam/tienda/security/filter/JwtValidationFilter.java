package com.dam.tienda.security.filter;

import com.dam.tienda.security.SimpleGrantedAuthorityJsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.dam.tienda.security.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        // se pasa a la clase padre con super
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        //Obtenemos la cabecera del token
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header== null || !header.startsWith(PREFIX_TOKEN))
        {
            chain.doFilter(request, response);
            return;
        }
        // Hay que poner trim() para eliminar espacios en blanco
        // Le quitamos la cabecera, para quedarnos con el token
        String token=header.replace(PREFIX_TOKEN, "").trim();

        try {
            // Analiza el token si es válido
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build()
                    .parseSignedClaims(token).getPayload();
            // Para obtener el username
            String username=claims.getSubject();
            //String username2=claims.get("username",String.class);
            Object autoritiesClaims=claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities=
                    Arrays.asList( new ObjectMapper()
                            // Hacemos un mixing para que entienda autorities como role
                                    .addMixIn(SimpleGrantedAuthority.class,
                                            SimpleGrantedAuthorityJsonCreator.class)
                    .readValue(autoritiesClaims.toString().getBytes(),
                    SimpleGrantedAuthority[].class));

            UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
   // Importante para continuar a los siguientes filtros
            chain.doFilter(request, response);
        } catch (JwtException e) {
            Map<String,String> body=new HashMap<>();
            body.put("error",e.getMessage());
            body.put("message","El token JWT no es válido");
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);

        }
        }
}
