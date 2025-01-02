package com.login.controller;

import com.login.dto.ApiResponse;
import com.login.enum_message.ResponseMessage;
import com.login.model.User;
import com.login.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@ValidateOnExecution
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUser(){
            List<User> users = adminService.getAllUser();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(users, true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable long id){
            User user = adminService.getUserById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(user, true));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody User user){
            adminService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(ResponseMessage.CREATED_OK.name(), true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody User user, @PathVariable long id){
            adminService.updateUser(user, id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(ResponseMessage.UPDATE_OK.name(), true));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> setAdminRole(@PathVariable long id){
            adminService.setAdminRole(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(ResponseMessage.UPDATE_OK.name(), true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable long id){
            adminService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse(ResponseMessage.DELETE_OK.name(), true));
    }
}
