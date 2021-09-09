package com.chencorp.fileapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    @Data
    @AllArgsConstructor
    public class ApiError {

        private HttpStatus status;
        private String message;
    }

    @ExceptionHandler({AlreadyExistException.class })
    protected ResponseEntity<Object> handleAlreadyExistException(
            AlreadyExistException exception) {
        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
    @ExceptionHandler({NotFoundException.class })
    protected ResponseEntity<Object> handleNotFoundException(
            NotFoundException exception) {
        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

}
