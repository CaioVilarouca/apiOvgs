package com.api.ovgs.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private String type;

    private String title;

    private Integer status;

    private String detail;

    private String instance;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant timestamp;

    private List<ValidationError> violations;

    public static ApiErrorResponse simple(String type, String title, Integer status, String detail, String instance) {
        return ApiErrorResponse.builder()
                .type(type)
                .title(title)
                .status(status)
                .detail(detail)
                .instance(instance)
                .timestamp(Instant.now())
                .build();
    }
}