package com.aline.aline.exceptionHandler;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message){super(message);}
}
