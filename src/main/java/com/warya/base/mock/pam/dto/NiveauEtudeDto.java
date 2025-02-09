package com.warya.base.mock.pam.dto;

import com.warya.base.mock.pam.entity.NiveauEtude;

public class NiveauEtudeDto extends RefBaseDto {
    public static NiveauEtudeDto fromEntity(NiveauEtude entity) {
        return RefBaseDto.fromEntity(entity, NiveauEtudeDto.class);
    }
}