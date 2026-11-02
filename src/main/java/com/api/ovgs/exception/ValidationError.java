package com.api.ovgs.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um erro de validação individual seguindo RFC 7807
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationError {

    /**
     * Nome do campo que falhou na validação
     */
    private String field;

    /**
     * Mensagem de erro específica
     */
    private String message;

    /**
     * Valor que foi rejeitado (opcional)
     */
    private Object rejectedValue;

    /**
     * Código de erro específico (opcional)
     */
    private String code;

}