package com.aline.aline.exceptionHandler;

import com.aline.aline.payload.APIResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityExistsException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, IllegalArgumentException.class,
            EntityExistsException.class, BadRequestException.class})
    public ResponseEntity<APIResponse> resourceNotFoundException(Exception ex){
        String message = ex.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse> badCredentialsException(BadCredentialsException ex){
        String message = ex.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<APIResponse> unauthorizedException(UnauthorizedException ex){
        String message = ex.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<APIResponse> forbiddenException(ForbiddenException ex){
        String message = ex.getMessage();
        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse> httpMessageNotReadableException(HttpMessageNotReadableException ex){

        Throwable mostSpecificCause = ex.getMostSpecificCause();
        String message = ex.getMessage();
        if (mostSpecificCause instanceof InvalidFormatException invalidFormatException) {
            String targetType = invalidFormatException.getTargetType().getSimpleName();
            String value = invalidFormatException.getValue().toString();
            String enumValues = Arrays.toString(invalidFormatException.getTargetType().getEnumConstants());

            message = String.format("Invalid value '%s' for %s. Accepted values are: %s", value, targetType, enumValues);
        }

        APIResponse apiResponse = new APIResponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
