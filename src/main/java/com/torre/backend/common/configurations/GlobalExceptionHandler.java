package com.torre.backend.common.configurations;

import com.torre.backend.common.dtos.ErrorResponse;
import com.torre.backend.common.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
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
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex) {
        // Crear un objeto de respuesta de error personalizado
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("El endpoint solicitado no existe.");
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        log.error(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> missingParameter(MissingServletRequestParameterException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Validation errors.");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
        log.error(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({Exception.class})
    public @ResponseBody ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(500)
                .body(new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

}
