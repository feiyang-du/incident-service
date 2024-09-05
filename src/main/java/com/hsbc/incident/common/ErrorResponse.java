package com.hsbc.incident.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Standard error response structure")
public class ErrorResponse {
    @Schema(description = "HTTP status code of the error", example = "404")
    private Integer status;

    @Schema(description = "Error message describing what went wrong", example = "Incident not found")
    private String error;

    @Schema(description = "Timestamp when the error occurred", example = "2023-04-01T06:21:40")
    private LocalDateTime timestamp;

    @Schema(description = "Validation error Map, key is error field name, value is error message", example = "{\"type\": \"Type cannot be null\"}")
    private Map<String, String> validationErrors;
}
