package com.warya.base.application.reference.dto;

import com.warya.base.application.reference.entity.Region;

public class RegionDto extends RefBaseDto {
    public static RegionDto fromEntity(Region entity) {
        return RefBaseDto.fromEntity(entity, RegionDto.class);
    }
}