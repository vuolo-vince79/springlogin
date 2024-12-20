package com.login.service;

import com.login.dto.UserDto;
import com.login.enum_message.ResponseMessage;
import com.login.enum_message.Roles;
import com.login.enum_message.Token;
import com.login.exception.InvalidPasswordException;
import com.login.exception.InvalidUsernameException;
import com.login.model.User;
import com.login.repository.UserAdminRepository;
import com.login.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final UserAdminRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AdminService adminService;

    @Autowired
    public UserService(UserAdminRepository repository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil, AdminService adminService){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.adminService = adminService;
    }

    public UserDto login(User user){
        String username = user.getUsername().trim();
        String psw = user.getPsw().trim();
        User existingUser = repository.findByUsername(username)
                .orElseThrow(() -> new InvalidUsernameException(ResponseMessage.INVALID_USERNAME.name()));
        if(!passwordEncoder.matches(psw, existingUser.getPsw())){
            throw new InvalidPasswordException(ResponseMessage.INVALID_PSW.name());
        }
        Map<String, String> tokens = jwtUtil.createTokens(existingUser.getUsername(), existingUser.getIdUser(), existingUser.getRuolo());
        String accessToken = tokens.get(Token.ACCESS_TOKEN.name());
        String refreshToken = tokens.get(Token.REFRESH_TOKEN.name());
        existingUser.setRefreshToken(refreshToken);
        return new UserDto(accessToken, refreshToken, existingUser.getUsername(), existingUser.getRuolo());
    }

    public void register(User user){
        String username = user.getUsername().trim();
        String email = user.getEmail().trim();
        String psw = user.getPsw().trim();
        adminService.validateUser(username, email);
        user.setUsername(username);
        user.setEmail(email);
        user.setPsw(passwordEncoder.encode(psw));
        user.setRuolo(Roles.USER.name());
        repository.save(user);
    }
}
