package com.detect.mutant.controller.handler.message;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public class GlobalMessage {

    private GlobalMessage(){
        super();
    }

    public static final String ERROR_BAD_REQUEST ="Formato del ADN incorrecto";
    public static final String ERROR_NOT_CORRECT_CHARS = "La secuencia de ADN no es válida";
    public static final String ERROR_FORBIDDEN = "Humano!!! Fuera de acá";
    public static final String OK_MUTANT = "Mutante!!! Bienvenido a Magneto Club";

}
