package com.api.ovgs.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegistrationFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleRegistrationFailedException(
            RegistrationFailedException e,
            HttpServletRequest request) {

        return ApiErrorResponse.simple(
                ErrorTypes.CONFLICT,
                "Erro ao realizar cadastro",
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleException(Exception e, HttpServletRequest request) {
        return ApiErrorResponse.simple(
                ErrorTypes.INTERNAL_ERROR,
                "Erro interno do servidor",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.",
                request.getRequestURI()
        );
    }
}