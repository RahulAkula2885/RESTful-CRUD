package com.example.demo.crud.exception;

import com.example.demo.crud.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.Instant;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalException implements ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> invalidRefreshTokenException(Exception exception) {
        return createBaseResponse(INTERNAL_SERVER_ERROR,  exception.getMessage());
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<BaseResponse> iOException(IOException exception) {
        log.error(exception.getMessage());
        return createBaseResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<BaseResponse> internalServerException(InternalServerException exception) {
        log.error(exception.getMessage());
        return createBaseResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
    }


    private ResponseEntity<BaseResponse> createBaseResponse(HttpStatus httpStatus, String message) {
        BaseResponse baseResponse = BaseResponse.builder()
                .status(false)
                .statusMessage(message)
                .response(null)
                .systemTime(Instant.now())
                .build();
        return new ResponseEntity<>(baseResponse, httpStatus);
    }

}
