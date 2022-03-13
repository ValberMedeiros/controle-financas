package br.com.dev.valber.medeiros.controleficancas.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class JWTValidationFilter extends BasicAuthenticationFilter {
    public static final String PREFIX_ATRIBUTE = "Bearer ";

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/users/token/refresh")){
            chain.doFilter(request, response);
        } else {
            var attribute = request.getHeader(AUTHORIZATION);
            if (attribute != null) {
                try {
                    var jwt = attribute;
                    if (attribute.startsWith(PREFIX_ATRIBUTE)) {
                        jwt = attribute.replace(PREFIX_ATRIBUTE, "");
                    }
                    var authenticationToken = getAuthenticationToken(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request, response);
                } catch (Exception exception) {
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    Map<String, String> error = new HashMap<>();
                    error.put("timestamp", LocalDateTime.now().toString());
                    error.put("path", request.getServletPath());
                    error.put("unique-id", UUID.randomUUID().toString());
                    error.put("message", exception.getMessage());
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String jwt) {
        var usuario = JWT.require(Algorithm.HMAC512(JWTAuthenticatorFilter.TOKEN_KEY))
                .build()
                .verify(jwt)
                .getSubject();

        if (usuario == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
    }
}
