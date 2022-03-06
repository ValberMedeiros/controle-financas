package br.com.dev.valber.medeiros.controleficancas.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTValidationFilter extends BasicAuthenticationFilter {

    public static final String HEADER_ATRIBUTE = "Authorization";
    public static final String PREFIX_ATRIBUTE = "Bearer ";

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var attribute = request.getHeader(HEADER_ATRIBUTE);
        if (attribute == null || !attribute.startsWith(PREFIX_ATRIBUTE)) {
            chain.doFilter(request, response);
            return;
        }

        var jwt = attribute.replace(PREFIX_ATRIBUTE, "");
        var authenticationToken = getAuthenticationToken(jwt);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
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
