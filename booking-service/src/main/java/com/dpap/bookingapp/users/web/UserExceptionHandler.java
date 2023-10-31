//package com.dpap.bookingapp.users.web;
//
//import com.dpap.bookingapp.users.exception.UserNotFoundException;
//import com.dpap.bookingapp.users.exception.UserWithEmailExistsException;
//import com.dpap.bookingapp.users.exception.UserWithUsernameExistsException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//class UserExceptionHandler {
//
//    @ResponseBody
//    @ExceptionHandler(UserNotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    String handleUserNotFoundException(UserNotFoundException exception){
//        return exception.getMessage();
//    }
//
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(UserWithUsernameExistsException.class)
//    String handleUserWithUsernameExistsException(UserWithUsernameExistsException exception){
//        return exception.getMessage();
//    }
//
//    @ResponseBody
//    @ExceptionHandler(UserWithEmailExistsException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    String handleUserWithEmailExistsException(UserWithEmailExistsException exception){
//        return exception.getMessage();
//    }
//}
