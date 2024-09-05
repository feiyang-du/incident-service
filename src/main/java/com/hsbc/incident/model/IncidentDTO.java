package com.hsbc.incident.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data Transfer Object for Incident")
public class IncidentDTO {
    @Schema(description = "Auto generated unique identifier of the incident", example = "1")
    private Long id;

    @NotNull(message = "Title cannot be null")
    @Size(min = 5, max = 255, message = "Title must be between 5 and 255 characters")
    @Schema(description = "Title of the incident", example = "Network Outage", required = true)
    private String title;

    @Schema(description = "Type of the incident", example = "Server", required = true)
    @NotNull(message = "Type cannot be null")
    private String type;

    @Schema(description = "Status of the incident", example = "Open", required = true)
    @NotNull(message = "Status cannot be null")
    private String status;

    @Schema(description = "Reporter of the incident", example = "John Doe", required = true)
    @Size(min = 3, max = 50, message = "Reporter name must be between 3 and 50 characters")
    @NotNull(message = "Reporter cannot be null")
    private String reporter;

    @Schema(description = "Person handling the incident", example = "Jane Smith")
    private String handler;

    @Schema(description = "Details about the incident", example = "Internet connectivity lost in region")
    @Size(max = 255, message = "Detail cannot exceed 255 characters")
    private String detail;

    @Schema(description = "Timestamp when the incident was created", example = "2023-04-01T06:21:40")
    private LocalDateTime createTime;

    @Schema(description = "Timestamp when the incident was last updated", example = "2023-04-01T07:21:40")
    private LocalDateTime updateTime;
}
