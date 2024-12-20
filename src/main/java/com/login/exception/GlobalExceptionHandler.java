package com.login.exception;

import com.login.dto.ApiResponse;
import com.login.enum_message.ResponseMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> validationException(MethodArgumentNotValidException e){
        List<String> errors = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return ResponseEntity.badRequest().body(new ApiResponse(errors, false));
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ApiResponse> emailExistException(EmailExistsException e){
        return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false));
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<ApiResponse> usernameExistException(UsernameExistsException e){
        return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false));
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<ApiResponse> invalidUsernameException(InvalidUsernameException e){
        return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse> invalidPasswordException(InvalidPasswordException e){
        return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> userNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(e.getMessage(), false));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> generalException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(ResponseMessage.SERVER_ERROR.name(), false));
    }

}
