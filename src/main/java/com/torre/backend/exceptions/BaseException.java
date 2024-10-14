package com.torre.backend.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;


@Getter
@Setter
public class BaseException extends RuntimeException{
    private HttpStatusCode status;
    private String message;
    public BaseException(HttpStatusCode statusCode, String statusText) {
        this.status = statusCode;
        this.message = statusText;
    }
}
