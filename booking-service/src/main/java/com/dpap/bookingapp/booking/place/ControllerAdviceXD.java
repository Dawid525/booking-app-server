//package com.dpap.bookingapp.booking.place;
//
//import com.dpap.bookingapp.users.exception.UserNotFoundException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class ControllerAdviceXD extends ResponseEntityExceptionHandler {
//    //This method get triggered whenever there is MethodArgumentNotValidException exception.
//    //It shows only the user friendly error message.
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        Map<String, String> errorMap = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(
//                error -> {
//                    errorMap.put(error.getField(), error.getDefaultMessage());
//                });
//        return ResponseEntity.status(200).body(errorMap);
//    }
//
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
////    @ExceptionHandler(MethodArgumentNotValidException.class)
////    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
////
////    }
//
//    //This method get triggered whenever there is UserNotFoundException exception.
//    //It shows only the user friendly error message.
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(UserNotFoundException.class)
//    public Map<String, String> handleUserNotFoundException(UserNotFoundException exception) {
//        Map<String, String> errorMap = new HashMap<>();
//        errorMap.put("message", exception.getMessage());
//        return errorMap;
//    }
//}
