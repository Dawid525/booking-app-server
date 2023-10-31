package com.dpap.bookingapp;


import com.dpap.bookingapp.auth.exception.InvalidInputDataException;
import com.dpap.bookingapp.users.exception.UserNotFoundException;
import com.dpap.bookingapp.users.exception.UserWithEmailExistsException;
import com.dpap.bookingapp.users.exception.UserWithUsernameExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = "Validation error: ";
        errorMessage += ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((first, second) -> first + ", " + second)
                .orElse("");

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(InvalidInputDataException.class)
    public String handleInvalidInput(InvalidInputDataException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleUserNotFoundException(UserNotFoundException exception){
        return exception.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserWithUsernameExistsException.class)
    String handleUserWithUsernameExistsException(UserWithUsernameExistsException exception){
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserWithEmailExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleUserWithEmailExistsException(UserWithEmailExistsException exception){
        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleInvalidInput(Exception ex) {
        return ex.getMessage();
    }
}
