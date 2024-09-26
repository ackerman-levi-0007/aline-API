package com.aline.aline.exceptionHandler;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceName, String field, String value){
        super(String.format("%s not found with %s : %s", resourceName, field, value));
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

}
