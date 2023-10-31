//package com.dpap.bookingapp.booking.place;
//
//import com.dpap.bookingapp.booking.place.exception.NotFoundPlaceException;
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//@RestControllerAdvice
//public class PlaceControllerAdvice{//} extends ResponseEntityExceptionHandler {
////
////    @ResponseStatus(HttpStatus.BAD_REQUEST)
////    @ExceptionHandler(MethodArgumentNotValidException.class)
////    public Map<String, String> handleValidationExceptions(
////            MethodArgumentNotValidException ex) {
////        Map<String, String> errors = new HashMap<>();
////        ex.getBindingResult().getAllErrors().forEach((error) -> {
////            String fieldName = ((FieldError) error).getField();
////            String errorMessage = error.getDefaultMessage();
////            errors.put(fieldName, errorMessage);
////        });
////        return errors;
////    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public String onConstraintValidationException(
//            ConstraintViolationException e) {
////        ValidationErrorResponse error = new ValidationErrorResponse();
////        for (ConstraintViolation violation : e.getConstraintViolations()) {
////            error.getViolations().add(
////                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
////        }
////        return error;
////        return e.getMessage();
//        return "XDDX@EQWEQWE";
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public String onMethodArgumentNotValidException(
//            MethodArgumentNotValidException e) {
////        ValidationErrorResponse error = new ValidationErrorResponse();
////        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
////            error.getViolations().add(
////                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
////        }
////        return e.getMessage();
//        return "XLDDLD";
//    }
//
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ResponseBody
//    @ExceptionHandler(NotFoundPlaceException.class)
//    public String handleInvalidInput(NotFoundPlaceException ex) {
////        return ex.getMessage();
//        return "XDDDD";
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(RuntimeException.class)
//    public String handleInvalidInput(RuntimeException ex) {
////        return ex.getMessage();
//        return "XDD";
//
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    public String handleInvalidInput(Exception ex) {
////        return ex.getMessage();
//        return "XD";
//    }
//
//
//}
//
