package com.hsbc.incident.controller;

import com.hsbc.incident.common.ErrorResponse;
import com.hsbc.incident.manager.IncidentManager;
import com.hsbc.incident.model.IncidentDTO;
import com.hsbc.incident.model.PageDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    @Autowired
    private IncidentManager incidentManager;

    @Operation(summary = "Get all incidents with pagination", description = "Retrieve a list of all incidents with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incidents retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<PageDTO<IncidentDTO>> getAllIncidents(
            @Parameter(description = "Page number for pagination, default to 0", example = "2", required = true)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of records per page, default to 10", example = "20", required = true)
            @RequestParam(defaultValue = "10") int size) {
        log.info("Entering getAllIncidents() with page: {} and size: {}", page, size);
        PageDTO<IncidentDTO> resPage = incidentManager.getAllIncidents(PageRequest.of(page, size));
        log.info("Exiting getAllIncidents() with result: {}", resPage);
        return ResponseEntity.ok(resPage);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incident found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncidentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Incident not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<IncidentDTO> getIncidentById(
            @Parameter(description = "ID of the incident to be retrieved", required = true)
            @PathVariable Long id) {
        log.info("Entering getIncidentById() with ID: {}", id);
        IncidentDTO incident = incidentManager.getIncidentById(id);
        log.info("Exiting getIncidentById() with result: {}", incident);
        return ResponseEntity.ok(incident);
    }

    @Operation(summary = "Create a new incident", description = "Create a new incident by providing the necessary information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Incident created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncidentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<IncidentDTO> createIncident(
            @Parameter(description = "Incident object that needs to be created", required = true)
            @Valid @RequestBody IncidentDTO incidentDTO) {
        log.info("Entering createIncident() with request body: {}", incidentDTO);
        IncidentDTO created = incidentManager.createIncident(incidentDTO);
        log.info("Exiting createIncident() with result: {}", created);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @Operation(summary = "Update an existing incident", description = "Update incident information by providing new data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incident updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IncidentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Incident not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<IncidentDTO> updateIncident(
            @Parameter(description = "ID of the incident to be updated", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated incident object", required = true)
            @Valid @RequestBody IncidentDTO incidentDTO) {
        log.info("Entering updateIncident() with ID: {} and request body: {}", id, incidentDTO);
        IncidentDTO updated = incidentManager.updateIncident(id, incidentDTO);
        log.info("Exiting updateIncident() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }


    @Operation(summary = "Delete an incident", description = "Delete an incident by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Incident deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Incident not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(
            @Parameter(description = "ID of the incident to be deleted", required = true)
            @PathVariable Long id) {
        log.info("Entering deleteIncident() with ID: {}", id);
        incidentManager.deleteIncident(id);
        log.info("Exiting deleteIncident() with no content");
        return ResponseEntity.noContent().build();
    }

}
