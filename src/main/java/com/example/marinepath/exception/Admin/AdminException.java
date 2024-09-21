package com.example.marinepath.exception.Admin;

import com.example.marinepath.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AdminException extends RuntimeException{
    private final ErrorCode errorCode;

    public AdminException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
