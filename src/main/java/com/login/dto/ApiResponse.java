package com.login.dto;

public class ApiResponse {

    private final Object message;
    private final boolean success;

    public ApiResponse(Object message, boolean success) {
        this.message = message;
        this.success = success;
    }


    public Object getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

}
