package com.example.user_auth.exceptions;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resorceNotFoundExe(ResourceNotFoundException rx,WebRequest webRequest){
        ErrorDetails errorDetails =new ErrorDetails(rx.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleNotSameProprty(HttpMessageNotReadableException ex,WebRequest webRequest){
        String message = "Malformed JSON request";

        if (ex.getCause() instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException unEx = (UnrecognizedPropertyException) ex.getCause();
            message = "Invalid field in request body: " + unEx.getPropertyName();
        }

        ErrorDetails errorDetails = new ErrorDetails(message, webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<?> globalEx(Exception ex,WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(ex.getMessage(),webRequest.getDescription(false));
        return  new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);

    }


}