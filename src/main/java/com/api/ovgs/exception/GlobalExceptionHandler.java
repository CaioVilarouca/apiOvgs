package com.api.ovgs.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // Cliente
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

    // Cliente
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleUserNotFoundException(Exception e, HttpServletRequest request) {
        return ApiErrorResponse.simple(
                ErrorTypes.NOT_FOUND,
                "Erro objeto não encontrado",
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                request.getRequestURI()
        );
    }

    // Bad-Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleValidationException(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        String message = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .distinct()
                .reduce((first, next) -> first + "; " + next)
                .orElse("Dados inválidos.");

        return ApiErrorResponse.simple(
                ErrorTypes.BAD_REQUEST,
                "Erro de validação",
                HttpStatus.BAD_REQUEST.value(),
                message,
                request.getRequestURI()
        );
    }

    // Bad-Request
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidJson(
            HttpMessageNotReadableException e,
            HttpServletRequest request) {

        return ApiErrorResponse.simple(
                ErrorTypes.BAD_REQUEST,
                "JSON inválido",
                HttpStatus.BAD_REQUEST.value(),
                "Confira o formato do JSON e os tipos dos campos.",
                request.getRequestURI()
        );
    }

    // Generico
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