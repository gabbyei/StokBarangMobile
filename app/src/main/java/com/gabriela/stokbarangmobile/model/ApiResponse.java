package com.gabriela.stokbarangmobile.model;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private String token;
    private T user;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getToken() {
        return token;
    }

    public T getUser() {
        return user;
    }
}
