package com.torre.backend.configurations;

import com.torre.backend.dto.ErrorResponse;
import com.torre.backend.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.Timestamp;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({BaseException.class})
    public @ResponseBody ResponseEntity<Object> handleBaseException(BaseException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getStatus())
                .body(new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        e.getMessage(),
                        e.getStatus().value()));
    }
    @ExceptionHandler({NoResourceFoundException.class})
    public @ResponseBody ResponseEntity<Object> handleNoResourceFound(NoResourceFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        e.getMessage(),
                        HttpStatus.NOT_FOUND.value()));
    }
    @ExceptionHandler({Exception.class})
    public @ResponseBody ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(500)
                .body(new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

}
