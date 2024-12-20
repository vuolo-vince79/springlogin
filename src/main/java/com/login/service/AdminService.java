package com.login.service;

import com.login.enum_message.ResponseMessage;
import com.login.enum_message.Roles;
import com.login.exception.*;
import com.login.model.User;
import com.login.repository.UserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserAdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(UserAdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder){
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUser(){
        return adminRepository.findAll();
    }

    public User getUserById(long id){
        return adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ResponseMessage.NO_MATCH.name()));
    }

    public void createUser(User user){
        String username = user.getUsername().trim();
        String email = user.getEmail().trim();
        String psw = user.getPsw().trim();
        validateUser(username, email);
        user.setUsername(username);
        user.setEmail(email);
        user.setPsw(passwordEncoder.encode(psw));
        user.setRuolo(Roles.USER.name());
        adminRepository.save(user);
    }

    public void updateUser(User user, long id){
        User existingUser = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ResponseMessage.NO_MATCH.name()));
        String username = user.getUsername().trim();
        String email = user.getEmail().trim();
        String psw = user.getPsw().trim();
        if(!username.equals(existingUser.getUsername())){
            if(adminRepository.existsByUsername(username)){
                throw new UsernameExistsException(ResponseMessage.EXISTS_USERNAME.name());
            }
            existingUser.setUsername(username);
        }
        if(!email.equalsIgnoreCase(existingUser.getEmail())){
            if(adminRepository.existsByEmail(email)){
                throw new EmailExistsException(ResponseMessage.EXISTS_EMAIL.name());
            }
            existingUser.setEmail(email);
        }
        if(!passwordEncoder.matches(psw, existingUser.getPsw())){
            existingUser.setPsw(passwordEncoder.encode(psw));
        }
        adminRepository.save(existingUser);
    }

    public void setAdminRole(long id){
        User existingUser = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ResponseMessage.NO_MATCH.name()));
        existingUser.setRuolo(Roles.ADMIN.name());
        adminRepository.save(existingUser);
    }

    public void deleteUser(long id){
        User existingUser = adminRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ResponseMessage.NO_MATCH.name()));
        adminRepository.delete(existingUser);
    }

    public void validateUser(String username, String email){
        if(adminRepository.existsByEmail(email)){
            throw new EmailExistsException(ResponseMessage.EXISTS_EMAIL.name());
        }
        else if(adminRepository.existsByUsername(username)){
            throw new UsernameExistsException(ResponseMessage.EXISTS_USERNAME.name());
        }
    }

//    private boolean isValidEmail(String email) {
//        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
//        return email != null && email.matches(emailRegex);
//    }
}
