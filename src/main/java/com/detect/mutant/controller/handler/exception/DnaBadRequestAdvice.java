package com.detect.mutant.controller.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class DnaBadRequestAdvice {
    @ResponseBody
    @ExceptionHandler(DnaBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String entityBadRequestAdvice(DnaBadRequestException ex){
        return ex.getMessage();
    }
}
