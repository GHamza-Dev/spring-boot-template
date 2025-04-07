package com.warya.base.application.reference.dto;

import com.warya.base.application.reference.entity.Commune;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommuneDto extends RefBaseDto {
    private Long provinceId;

    public static CommuneDto fromEntity(Commune entity) {
        CommuneDto dto = RefBaseDto.fromEntity(entity, CommuneDto.class);
        dto.setProvinceId(entity.getProvince().getId());
        return dto;
    }
}