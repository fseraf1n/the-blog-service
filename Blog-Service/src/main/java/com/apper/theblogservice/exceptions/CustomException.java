package com.apper.theblogservice.exceptions;

import lombok.Data;

//deprecated code
@Data
public class CustomException extends RuntimeException {
    private String message;
    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        System.out.println(this.message);
        return this.message;
    }
}
