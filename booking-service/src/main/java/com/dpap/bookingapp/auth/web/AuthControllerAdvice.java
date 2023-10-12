package com.dpap.bookingapp.auth.web;


import com.dpap.bookingapp.auth.exception.InvalidInputDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(InvalidInputDataException.class)
    public String handleInvalidInput(InvalidInputDataException ex) {
        return ex.getMessage();
    }
}
