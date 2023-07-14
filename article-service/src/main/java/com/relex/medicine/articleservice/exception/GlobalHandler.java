package com.relex.medicine.articleservice.exception;

import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.Objects;

@ControllerAdvice
@Log
public class GlobalHandler{
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleBindException(BindException e){
        List<String> errors = e.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        log.log(Level.INFO,
                String.format("Bind exception in: %s, error count: %d.",
                        Objects.requireNonNull(e.getBindingResult().getTarget()),
                        e.getAllErrors().size()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NotUniqueEntry.class)
    protected ResponseEntity<String> handleNotUniqueEntryException(NotUniqueEntry e){
        log.log(Level.INFO, "Raised Not Unique Exception with message: " + e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NoEntityFoundException.class)
    protected ResponseEntity<String> handleNoEntityFoundException(NoEntityFoundException e){
        log.log(Level.INFO, "Raised No Entity Found Exception with message: " + e.getMessage());
        return ResponseEntity.notFound().build();
    }
}
