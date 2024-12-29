package com.login.auth;

import com.login.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    /**
     * Metodo principale che intercetta ogni richiesta e verifica se il token JWT è valido.
     * Se il token è valido, l'utente viene autenticato.
     *
     * @param request la richiesta HTTP in arrivo
     * @param response la risposta HTTP in uscita
     * @param filterChain la catena di filtri da eseguire
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String url = request.getRequestURI();
        if(url.equals("/login") || url.equals("/register")){
            filterChain.doFilter(request, response);
	    return;
        }
        // Estrae il token JWT dall'header della richiesta
        String token = getTokenFromRequest(request);
        // Verifica se il token è valido
        if(token != null && jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token)){
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
            // Crea un oggetto di autenticazione senza credenziali (null)
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            // Imposta l'utente autenticato nel contesto di sicurezza
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        // Continua con il prossimo filtro nella catena
        filterChain.doFilter(request, response);
    }

    /**
     * Estrae il token JWT dall'header "Authentication" della richiesta.
     *
     * @param request la richiesta HTTP
     * @return il token JWT (senza il prefisso "Bearer"), oppure null se non presente
     */
    private String getTokenFromRequest(HttpServletRequest request){
        // Legge l'header
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            // Rimuove il prefisso "Bearer "
            return bearerToken.substring(7);
        }
        // Ritorna null se l'header non contiene un token valido
        return null;
    }
}
