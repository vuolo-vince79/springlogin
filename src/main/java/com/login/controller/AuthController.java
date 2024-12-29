package com.login.controller;

import com.login.dto.ApiResponse;
import com.login.dto.UserDto;
import com.login.enum_message.ResponseMessage;
import com.login.enum_message.Token;
import com.login.exception.UserNotFoundException;
import com.login.model.User;
import com.login.repository.UserAdminRepository;
import com.login.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserAdminRepository repository;

    @Autowired
    public AuthController(JwtUtil jwtUtil, UserAdminRepository repository){
        this.jwtUtil = jwtUtil;
        this.repository = repository;
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody String refreshToken){
        if(jwtUtil.validateToken(refreshToken) && !jwtUtil.isTokenExpired(refreshToken)){
            String username = jwtUtil.extractUsername(refreshToken);
            User exiatingUser = repository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException(ResponseMessage.NO_MATCH.name()));
            if(!exiatingUser.getRefreshToken().equals(refreshToken)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse(ResponseMessage.NO_AUTHORIZATION.name(), false));
            }
            long idUser = jwtUtil.extractIdUser(refreshToken);
            String role = jwtUtil.extractRole(refreshToken);
            Map<String, String> tokens = jwtUtil.createTokens(username, idUser, role);
            String newAccessToken = tokens.get(Token.ACCESS_TOKEN.name());
            UserDto userDto = new UserDto(newAccessToken, refreshToken, username, role);
            return ResponseEntity.ok(new ApiResponse(userDto, true));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse(ResponseMessage.NO_AUTHORIZATION.name(), false));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody String username){
        User existingUser = repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(ResponseMessage.NO_MATCH.name()));
        existingUser.setRefreshToken(null);
        repository.save(existingUser);
        return ResponseEntity.ok(new ApiResponse("ok logout", true));
    }

}
