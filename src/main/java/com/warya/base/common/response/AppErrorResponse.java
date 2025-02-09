package com.warya.base.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppErrorResponse {
    private LocalDateTime timestamp;
    private String error;
    private int status;
    private String statusText;
    private List<FieldValidationError> validationErrors;
}