package com.example.post.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> exception(CustomException e){
        log.info("[GlobalExceptionHandler] CustomException Error");

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", e.getMessage());
            responseBody.put("code", e.getHttpStatus());
            return ResponseEntity.status(e.getHttpStatus()).body(responseBody);
    }
}
