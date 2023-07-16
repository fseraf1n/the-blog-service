package com.apper.theblogservice.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
public class CustomErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    public CustomErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }
    public CustomErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
