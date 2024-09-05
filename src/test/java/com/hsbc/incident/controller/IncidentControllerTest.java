package com.hsbc.incident.controller;

import com.hsbc.incident.common.BizException;
import com.hsbc.incident.manager.IncidentManager;
import com.hsbc.incident.model.IncidentDTO;
import com.hsbc.incident.model.PageDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@WebMvcTest(IncidentController.class)
public class IncidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentManager incidentManager;

    @Test
    void testGetIncidentById_Success() throws Exception {
        IncidentDTO incidentDTO = new IncidentDTO();
        incidentDTO.setId(1L);
        incidentDTO.setTitle("Test Incident");

        when(incidentManager.getIncidentById(1L)).thenReturn(incidentDTO);

        mockMvc.perform(get("/api/incidents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Incident"));
    }

    @Test
    void testGetIncidentById_NotFound() throws Exception {
        Mockito.doThrow(new BizException(404, "Incident not found with id: 1")).when(incidentManager).getIncidentById(1L);

        mockMvc.perform(get("/api/incidents/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Incident not found with id: 1"));
    }

    @Test
    void testCreateIncident_Success() throws Exception {
        IncidentDTO incidentDTO = new IncidentDTO();
        incidentDTO.setId(1L);
        incidentDTO.setTitle("Test Incident");

        when(incidentManager.createIncident(any(IncidentDTO.class))).thenReturn(incidentDTO);

        mockMvc.perform(post("/api/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Incident\",\"type\":\"Server\",\"status\":\"Open\",\"reporter\":\"John\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Incident"));
    }

    @Test
    void testCreateIncident_BadRequest() throws Exception {
        IncidentDTO incidentDTO = new IncidentDTO();
        incidentDTO.setId(1L);
        incidentDTO.setTitle("Test Incident");

        when(incidentManager.createIncident(any(IncidentDTO.class))).thenReturn(incidentDTO);

        mockMvc.perform(post("/api/incidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Incident\",\"type\":\"Server\",\"status\":\"Open\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void testUpdateIncident_Success() throws Exception {
        IncidentDTO updatedIncidentDTO = new IncidentDTO();
        updatedIncidentDTO.setId(1L);
        updatedIncidentDTO.setTitle("Test Incident Updated");
        updatedIncidentDTO.setReporter("Jane Doe");

        when(incidentManager.updateIncident(eq(1L), any(IncidentDTO.class))).thenReturn(updatedIncidentDTO);

        mockMvc.perform(put("/api/incidents/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Incident Updated\",\"type\":\"Server\",\"status\":\"Open\",\"reporter\":\"John Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Incident Updated"))
                .andExpect(jsonPath("$.reporter").value("Jane Doe"));
    }

    @Test
    void testDeleteIncident_Success() throws Exception {
        mockMvc.perform(delete("/api/incidents/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(incidentManager, Mockito.times(1)).deleteIncident(1L);
    }

    @Test
    void testGetAllIncidents_Success() throws Exception {
        IncidentDTO incidentDTO1 = new IncidentDTO();
        incidentDTO1.setId(1L);
        incidentDTO1.setTitle("Incident 1");

        IncidentDTO incidentDTO2 = new IncidentDTO();
        incidentDTO2.setId(2L);
        incidentDTO2.setTitle("Incident 2");

        PageDTO<IncidentDTO> pageResp = new PageDTO<>();
        pageResp.setContent(Arrays.asList(incidentDTO1, incidentDTO2));
        pageResp.setTotalElements(2L);
        pageResp.setTotalPages(1);
        pageResp.setCurrentPage(0);

        when(incidentManager.getAllIncidents(PageRequest.of(0, 2))).thenReturn(pageResp);

        mockMvc.perform(get("/api/incidents")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2));
    }



}
