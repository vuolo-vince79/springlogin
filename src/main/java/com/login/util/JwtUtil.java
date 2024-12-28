package com.login.util;


import com.login.enum_message.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // Chiave segreta generata per firmare i token usando l'algoritmo HS256
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public Map<String, String> createTokens(String username, long idUser, String role){
        long accessExpiration = 1000 * 1 * 60;
        long refreshExpiration = 1000 * 60 * 60 * 24 * 7;
        String accessToken = generateToken(username, idUser, role, accessExpiration);
        String refreshToken = generateToken(username, idUser, role, refreshExpiration);
        return Map.of(Token.ACCESS_TOKEN.name(), accessToken, Token.REFRESH_TOKEN.name(), refreshToken);
    }

    private String generateToken(String username, long idUser, String role, long expirationTime){
        return Jwts.builder()
                .setSubject(username).claim("idUser", idUser).claim("role", role)
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key).compact();
    }


    /**
     * Estrae tutte le informazioni (claims) da un token JWT.
     * Questo metodo verifica la firma del token prima di estrarre i dati.
     *
     * @param token il token JWT da analizzare
     * @return un oggetto Claims contenente tutte le informazioni presenti nel token
     */
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)  // Imposta la chiave usata per verificare la firma
                .build()
                .parseClaimsJws(token)  // Analizza il token firmato
                .getBody();  // Ottiene il corpo del token (claims)
    }

    /**
     * Estrae il nome utente (soggetto) dal token JWT.
     *
     * @param token il token JWT da analizzare
     * @return il nome utente presente nel token
     */
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public Long extractIdUser(String token){
        return extractAllClaims(token).get("idUser", Long.class);
    }

    public String extractRole(String token){
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Verifica se un token è scaduto.
     *
     * @param token il token JWT da controllare
     * @return true se il token è scaduto, false altrimenti
     */
    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }


    /**
     * Valida il token JWT verificando la firma e la struttura.
     *
     * @param token il token JWT da validare
     * @return true se il token è valido, false altrimenti
     */
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)  // Imposta la chiave per la verifica
                    .build()
                    .parseClaimsJws(token);  // Analizza il token firmato
            // Se non ci sono eccezioni, il token è valido
            return true;
        }
        catch(JwtException e){
            // Se il parsing fallisce, il token non è valido
            return false;
        }
    }



}
