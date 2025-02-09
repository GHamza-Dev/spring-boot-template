package com.warya.base.mock.pam.dto;

import com.warya.base.mock.pam.entity.Region;

public class RegionDto extends RefBaseDto {
    public static RegionDto fromEntity(Region entity) {
        return RefBaseDto.fromEntity(entity, RegionDto.class);
    }
}