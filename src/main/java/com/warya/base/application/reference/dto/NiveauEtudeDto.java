package com.warya.base.application.reference.dto;

import com.warya.base.application.reference.entity.NiveauEtude;

public class NiveauEtudeDto extends RefBaseDto {
    public static NiveauEtudeDto fromEntity(NiveauEtude entity) {
        return RefBaseDto.fromEntity(entity, NiveauEtudeDto.class);
    }
}