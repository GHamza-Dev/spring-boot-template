package com.warya.base.application.reference.dto;

import com.warya.base.application.reference.entity.RefBaseEntity;
import lombok.Data;

@Data
public abstract class RefBaseDto {
    private Long id;
    private String libelle;
    private String code;

    public static <T extends RefBaseDto> T fromEntity(RefBaseEntity entity, Class<T> dtoClass) {
        try {
            T dto = dtoClass.getDeclaredConstructor().newInstance();
            dto.setId(entity.getId());
            dto.setLibelle(entity.getLibelle());
            dto.setCode(entity.getCode());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping entity to DTO", e);
        }
    }
}