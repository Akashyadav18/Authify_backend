package com.Security.Authify.globalException;


import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        Map<String, String> errMap = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach( err -> {
            String fieldName = ((FieldError) err).getField();
            String msg = err.getDefaultMessage();
            errMap.put(fieldName, msg);
        });
        return errMap;
    }
}
