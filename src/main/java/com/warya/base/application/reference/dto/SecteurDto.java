package com.warya.base.application.reference.dto;

import com.warya.base.application.reference.entity.Secteur;

public class SecteurDto extends RefBaseDto {
    public static SecteurDto fromEntity(Secteur entity) {
        return RefBaseDto.fromEntity(entity, SecteurDto.class);
    }
}