package com.hsbc.incident.manager;

import com.hsbc.incident.common.BizException;
import com.hsbc.incident.common.Converter;
import com.hsbc.incident.common.StatusEnum;
import com.hsbc.incident.model.IncidentDTO;
import com.hsbc.incident.model.IncidentEntity;
import com.hsbc.incident.model.PageDTO;
import com.hsbc.incident.repository.IncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IncidentManagerTest {
    @Mock
    private IncidentRepository incidentRepository;
    @InjectMocks
    private IncidentManager incidentManager;
    private IncidentEntity incidentEntity;
    private IncidentDTO incidentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        incidentEntity = new IncidentEntity();
        incidentEntity.setId(1L);
        incidentEntity.setTitle("Test Incident");
        incidentEntity.setType(1);
        incidentEntity.setStatus(1);
        incidentDTO = Converter.toDTO(incidentEntity);
    }

    @Test
    void testGetAllIncidents_Success() {
        IncidentEntity incidentEntity1 = new IncidentEntity();
        incidentEntity1.setId(2L);
        incidentEntity1.setType(2);
        incidentEntity1.setStatus(2);
        incidentEntity1.setTitle("Test Incident 1");

        // 模拟分页数据
        List<IncidentEntity> incidentEntities = Arrays.asList(incidentEntity, incidentEntity1);
        Page<IncidentEntity> page = new PageImpl<>(incidentEntities, PageRequest.of(0, 2), incidentEntities.size());

        when(incidentRepository.findAll(any(Pageable.class))).thenReturn(page);

        // 调用 getAllIncidents 方法
        PageDTO<IncidentDTO> result = incidentManager.getAllIncidents(PageRequest.of(0, 2));

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(1, result.getTotalPages());
        assertEquals(2, result.getTotalElements());

        // 验证 repository 被调用一次
        verify(incidentRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetIncidentById_Success() {
        Long now = Instant.now().toEpochMilli();
        incidentEntity.setCreateTime(now);
        incidentEntity.setUpdateTime(now);
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(incidentEntity));

        IncidentDTO result = incidentManager.getIncidentById(1L);
        assertNotNull(result);
        assertEquals("Test Incident", result.getTitle());

        verify(incidentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetIncidentById_NotFound() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.empty());

        BizException exception = assertThrows(BizException.class, () -> {
            incidentManager.getIncidentById(1L);
        });

        assertEquals(404, exception.getCode());
        assertEquals("Incident not found with id: 1", exception.getMessage());
        verify(incidentRepository, times(1)).findById(1L);
    }


    @Test
    void testCreateIncident_Success() {
        when(incidentRepository.existsByTitle("Test Incident")).thenReturn(false);
        when(incidentRepository.save(any(IncidentEntity.class))).thenReturn(incidentEntity);

        IncidentDTO result = incidentManager.createIncident(incidentDTO);
        assertNotNull(result);
        assertEquals("Test Incident", result.getTitle());

        verify(incidentRepository, times(1)).existsByTitle("Test Incident");
        verify(incidentRepository, times(1)).save(any(IncidentEntity.class));
    }

    @Test
    void testCreateIncident_DuplicateTitle() {
        when(incidentRepository.existsByTitle("Test Incident")).thenReturn(true);

        BizException exception = assertThrows(BizException.class, () -> {
            incidentManager.createIncident(incidentDTO);
        });

        assertEquals(409, exception.getCode());
        assertEquals("Incident with title 'Test Incident' already exists", exception.getMessage());
        verify(incidentRepository, times(1)).existsByTitle("Test Incident");
        verify(incidentRepository, times(0)).save(any(IncidentEntity.class));
    }

    @Test
    void testUpdateIncident_Success() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.of(incidentEntity));
        when(incidentRepository.save(any(IncidentEntity.class))).thenReturn(incidentEntity);

        IncidentDTO updatedIncidentDTO = new IncidentDTO();
        updatedIncidentDTO.setStatus(StatusEnum.IN_PROGRESS.getText());
        updatedIncidentDTO.setHandler("New Handler");
        updatedIncidentDTO.setDetail("Updated detail");

        IncidentDTO result = incidentManager.updateIncident(1L, updatedIncidentDTO);
        assertNotNull(result);
        assertEquals("Test Incident", result.getTitle()); // Title should not change
        assertEquals("New Handler", result.getHandler());
        assertEquals(StatusEnum.IN_PROGRESS.getText(), result.getStatus());

        verify(incidentRepository, times(1)).findById(1L);
        verify(incidentRepository, times(1)).save(any(IncidentEntity.class));
    }

    @Test
    void testUpdateIncident_NotFound() {
        when(incidentRepository.findById(1L)).thenReturn(Optional.empty());

        BizException exception = assertThrows(BizException.class, () -> {
            incidentManager.updateIncident(1L, incidentDTO);
        });

        assertEquals(404, exception.getCode());
        assertEquals("Incident not found with id: 1", exception.getMessage());
        verify(incidentRepository, times(1)).findById(1L);
        verify(incidentRepository, times(0)).save(any(IncidentEntity.class));
    }

    @Test
    void testDeleteIncident_Success() {
        when(incidentRepository.existsById(1L)).thenReturn(true);

        incidentManager.deleteIncident(1L);
        verify(incidentRepository, times(1)).existsById(1L);
        verify(incidentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteIncident_NotFound() {
        when(incidentRepository.existsById(1L)).thenReturn(false);

        BizException exception = assertThrows(BizException.class, () -> {
            incidentManager.deleteIncident(1L);
        });

        assertEquals(404, exception.getCode());
        assertEquals("Incident not found with id: 1", exception.getMessage());
        verify(incidentRepository, times(1)).existsById(1L);
        verify(incidentRepository, times(0)).deleteById(1L);
    }










}
