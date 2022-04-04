package com.detect.mutant.controller.handler.exception;

public class DnaBadRequestException extends RuntimeException{
    public DnaBadRequestException(String message){
        super(message);
    }
}