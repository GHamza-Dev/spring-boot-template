package com.warya.base.application.reference.dto;

import com.warya.base.application.reference.entity.Profession;

public class ProfessionDto extends RefBaseDto {
    public static ProfessionDto fromEntity(Profession entity) {
        return RefBaseDto.fromEntity(entity, ProfessionDto.class);
    }
}