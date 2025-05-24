package com.example.movietwebapplication.APIRespons;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}