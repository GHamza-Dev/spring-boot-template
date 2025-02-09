package com.warya.base.mock.pam.dto;

import com.warya.base.mock.pam.entity.Arrondissement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArrondissementDto extends RefBaseDto {
    private Long provinceId;

    public static ArrondissementDto fromEntity(Arrondissement entity) {
        ArrondissementDto dto = RefBaseDto.fromEntity(entity, ArrondissementDto.class);
        dto.setProvinceId(entity.getProvince().getId());
        return dto;
    }
}