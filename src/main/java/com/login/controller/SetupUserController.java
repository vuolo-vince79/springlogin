package com.login.controller;

import com.login.dto.ApiResponse;
import com.login.dto.SetupUserDto;
import com.login.model.SetupUser;
import com.login.service.SetupUserService;
import com.login.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/set")
public class SetupUserController {

    private final SetupUserService service;
    private final JwtUtil jwtUtil;

    @Autowired
    public SetupUserController(SetupUserService service, JwtUtil jwtUtil){
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/lang")
    public ResponseEntity<ApiResponse> setLang(@RequestBody SetupUserDto setupUserDto){
        SetupUser setupUser = getSetupUser(setupUserDto);
        if(setupUser != null) {
            setupUser.setLang(setupUserDto.getLang());
            service.updateSetupUser(setupUser);
            return ResponseEntity.ok(new ApiResponse("set lang ok", true));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("failed set lang", false));
    }

    @PostMapping("/theme")
    public ResponseEntity<ApiResponse> setTheme(@RequestBody SetupUserDto setupUserDto){
        SetupUser setupUser = getSetupUser(setupUserDto);
        if(setupUser != null){
            setupUser.setDarkTheme(setupUserDto.isDark());
            service.updateSetupUser(setupUser);
            return ResponseEntity.ok(new ApiResponse("set lang ok", true));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("failed set lang", false));
    }

    private SetupUser getSetupUser(SetupUserDto setupUserDto){
        String token = setupUserDto.getToken();
        if(jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token)) {
            long idUser = jwtUtil.extractIdUser(token);
            return service.getSetupUser(idUser);
        }
        return null;
    }
}
