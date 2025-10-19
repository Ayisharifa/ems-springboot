package com.example.ems.exceptions;

import org.springframework.http.*;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    record ErrorBody(String message) {}

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorBody> notFound(ResourceNotFoundException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorBody(ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorBody> badRequest(BadRequestException ex, WebRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorBody(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBody> other(Exception ex, WebRequest req) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorBody("Internal server error"));
    }
}
