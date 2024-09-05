package com.hsbc.incident.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Pagination response DTO for paginated results")
public class PageDTO<T> {
    @Schema(description = "List of elements on the current page", required = true)
    private List<T> content;   // Content of current page

    @Schema(description = "Total number of elements", example = "100", required = true)
    private long totalElements;  // Total elements of this query

    @Schema(description = "Total number of pages", example = "10", required = true)
    private int totalPages;     // Total pages of this query

    @Schema(description = "Current page number", example = "0", required = true)
    private int currentPage;    // Current page NO start from 0
}
