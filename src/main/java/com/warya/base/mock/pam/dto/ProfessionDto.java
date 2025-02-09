package com.warya.base.mock.pam.dto;

import com.warya.base.mock.pam.entity.Profession;

public class ProfessionDto extends RefBaseDto {
    public static ProfessionDto fromEntity(Profession entity) {
        return RefBaseDto.fromEntity(entity, ProfessionDto.class);
    }
}