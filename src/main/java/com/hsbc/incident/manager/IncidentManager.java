package com.hsbc.incident.manager;

import com.hsbc.incident.common.BizException;
import com.hsbc.incident.common.Converter;
import com.hsbc.incident.common.StatusEnum;
import com.hsbc.incident.common.TypeEnum;
import com.hsbc.incident.model.IncidentDTO;
import com.hsbc.incident.model.IncidentEntity;
import com.hsbc.incident.model.PageDTO;
import com.hsbc.incident.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IncidentManager {
    @Autowired
    private IncidentRepository incidentRepository;

    @Cacheable(value = "incident_pages", key = "'page_' + #req.pageNumber + '_' + #req.pageSize", unless = "#result.content.isEmpty()")
    public PageDTO<IncidentDTO> getAllIncidents(PageRequest req) {
        Page<IncidentEntity> page = incidentRepository.findAll(req);
        List<IncidentDTO> incidentDTOs = page.getContent().stream()
                .map(Converter::toDTO)
                .collect(Collectors.toList());

        return PageDTO.<IncidentDTO>builder()
                .content(incidentDTOs)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(req.getPageNumber())
                .build();
    }

    @Cacheable(value = "incidents", key = "#id")
    public IncidentDTO getIncidentById(Long id) {
        IncidentEntity entity = incidentRepository.findById(id)
                .orElseThrow(() -> new BizException(HttpStatus.NOT_FOUND.value(), "Incident not found with id: " + id));
        return Converter.toDTO(entity);
    }


    @CacheEvict(value = "incident_pages", allEntries = true) // Clear page cache
    public IncidentDTO createIncident(IncidentDTO incidentDTO) {
        if (incidentRepository.existsByTitle(incidentDTO.getTitle())) {
            throw new BizException(HttpStatus.CONFLICT.value(), "Incident with title '" + incidentDTO.getTitle() + "' already exists");
        }

        IncidentEntity entity = Converter.toEntity(incidentDTO);
        // Set createTime and updateTime to current timestamp
        Long now = Instant.now().toEpochMilli();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);

        IncidentEntity savedEntity = incidentRepository.save(entity);
        return Converter.toDTO(savedEntity);  // Return created incident as DTO
    }

    @Caching(evict = {
            @CacheEvict(value = "incidents", key = "#id"), // Only evict specific key item for incidents cache
            @CacheEvict(value = "incident_pages", allEntries = true) // Evict all page cache
    })
    public IncidentDTO updateIncident(Long id, IncidentDTO incidentDTO) {
        IncidentEntity entity = incidentRepository.findById(id)
                .orElseThrow(() -> new BizException(HttpStatus.NOT_FOUND.value(), "Incident not found with id: " + id));

        // entity.setTitle(entity.getTitle()); Change title is not allowed
        entity.setType(TypeEnum.getCodeByText(incidentDTO.getType()));
        entity.setStatus(StatusEnum.getCodeByText(incidentDTO.getStatus()));
        entity.setReporter(incidentDTO.getReporter());
        entity.setHandler(incidentDTO.getHandler());
        entity.setDetail(incidentDTO.getDetail());

        // Update updateTime
        entity.setUpdateTime(Instant.now().toEpochMilli());

        IncidentEntity updatedEntity = incidentRepository.save(entity);
        return Converter.toDTO(updatedEntity);
    }

    @Caching(evict = {
            @CacheEvict(value = "incidents", key = "#id"), // Only evict specific key item for incidents cache
            @CacheEvict(value = "incident_pages", allEntries = true) // Evict all page cache
    })
    public void deleteIncident(Long id) {
        if (!incidentRepository.existsById(id)) {
            throw new BizException(HttpStatus.NOT_FOUND.value(), "Incident not found with id: " + id);
        }
        incidentRepository.deleteById(id);
    }
}
