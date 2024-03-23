package com.aline.aline.exceptionHandler;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){super(message);}
}
