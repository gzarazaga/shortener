package com.upwork.shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralHandler {

  // Exception handler for CustomException
  @ExceptionHandler(URLNotFoundException.class)
  public ResponseEntity<String> handleURLNotFoundException(URLNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  // Exception handler for other exceptions (if needed)
  @ExceptionHandler(ExpiredUrlException.class)
  public ResponseEntity<String> handleExpiredUrlExceptionExceptions(ExpiredUrlException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}