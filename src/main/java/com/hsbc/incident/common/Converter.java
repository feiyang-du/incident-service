package com.hsbc.incident.common;

import com.hsbc.incident.model.IncidentDTO;
import com.hsbc.incident.model.IncidentEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Converter {
    public static IncidentDTO toDTO(IncidentEntity entity) {
        return IncidentDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .type(TypeEnum.getTextByCode(entity.getType()))  // Convert code to text for front-end
                .status(StatusEnum.getTextByCode(entity.getStatus()))  // Convert code to text for front-end
                .reporter(entity.getReporter())
                .handler(entity.getHandler())
                .detail(entity.getDetail())
                .createTime(entity.getCreateTime() != null ?
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(entity.getCreateTime()), ZoneId.systemDefault()) : null)
                .updateTime(entity.getUpdateTime() != null ?
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(entity.getUpdateTime()), ZoneId.systemDefault()) : null)
                .build();
    }

    public static IncidentEntity toEntity(IncidentDTO dto) {
        return IncidentEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .type(TypeEnum.getCodeByText(dto.getType()))  // Convert text to int for database
                .status(StatusEnum.getCodeByText(dto.getStatus()))  // Convert text to int for database
                .reporter(dto.getReporter())
                .handler(dto.getHandler())
                .detail(dto.getDetail())
                .createTime(dto.getCreateTime() != null ?
                        dto.getCreateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : null)
                .updateTime(dto.getUpdateTime() != null ?
                        dto.getUpdateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() : null)
                .build();
    }
}
