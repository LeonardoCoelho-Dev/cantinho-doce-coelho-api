package com.CantinhoDoceCoelho.Project.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.core.JacksonException;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException ex){
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(DataErrorValidation::new).toList());

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleErrorInvalidJson(HttpMessageNotReadableException ex){
        if (ex.getCause() instanceof JacksonException jacksonEx && !jacksonEx.getPath().isEmpty()) {
            var field = jacksonEx.getPath().getLast().getPropertyName();
            return ResponseEntity.badRequest().body(List.of(new DataErrorValidation(field, "valor inválido")));
        }
        return ResponseEntity.badRequest().body(List.of(new DataErrorValidation(null, "JSON inválido")));
    }

    private record DataErrorValidation(String field, String message){
        public DataErrorValidation(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }

    }
}
