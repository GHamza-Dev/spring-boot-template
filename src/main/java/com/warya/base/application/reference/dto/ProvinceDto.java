package com.warya.base.application.reference.dto;

import com.warya.base.application.reference.entity.Province;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProvinceDto extends RefBaseDto {
    private Long regionId;

    public static ProvinceDto fromEntity(Province entity) {
        ProvinceDto dto = RefBaseDto.fromEntity(entity, ProvinceDto.class);
        dto.setRegionId(entity.getRegion().getId());
        return dto;
    }
}