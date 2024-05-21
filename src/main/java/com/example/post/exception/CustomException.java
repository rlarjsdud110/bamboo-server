package com.example.post.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
public class CustomException extends RuntimeException{
    private HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        log.info("[CustomException] CustomException Error");
        log.info("Error Code[{}]", httpStatus);
        this.httpStatus = httpStatus;
    }
}
