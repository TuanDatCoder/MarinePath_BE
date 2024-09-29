package com.example.marinepath.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import javax.security.auth.login.AccountException;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(ChangeSetPersister.NotFoundException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistedException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ErrorResponse handlerAlreadyExistedException(AlreadyExistedException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.ALREADY_REPORTED, ex.getMessage());
    }

    @ExceptionHandler(AccountException.class)
    public ErrorResponse handlerAccountException(AlreadyExistedException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.ALREADY_REPORTED, ex.getMessage());
    }
    @ExceptionHandler(ApiException.class)
    public ErrorResponse handlerApiException(AlreadyExistedException ex, WebRequest req) {
        return new ErrorResponse(HttpStatus.ALREADY_REPORTED, ex.getMessage());
    }


}