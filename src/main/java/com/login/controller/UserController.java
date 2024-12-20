package com.login.controller;

import com.login.dto.ApiResponse;
import com.login.dto.UserDto;
import com.login.enum_message.ResponseMessage;
import com.login.model.User;
import com.login.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ValidateOnExecution
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user){
        UserDto userDto = userService.login(user);
        return ResponseEntity.ok(new ApiResponse(userDto, true));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody User user){
        userService.register(user);
        return ResponseEntity.ok(new ApiResponse(ResponseMessage.CREATED_OK.name(), true));
    }
}
