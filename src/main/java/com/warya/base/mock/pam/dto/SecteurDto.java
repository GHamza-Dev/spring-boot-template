package com.warya.base.mock.pam.dto;

import com.warya.base.mock.pam.entity.Secteur;

public class SecteurDto extends RefBaseDto {
    public static SecteurDto fromEntity(Secteur entity) {
        return RefBaseDto.fromEntity(entity, SecteurDto.class);
    }
}