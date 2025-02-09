package com.warya.base.mock.pam.dto;

import com.warya.base.mock.pam.entity.Commune;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommuneDto extends RefBaseDto {
    private Long provinceId;

    public static CommuneDto fromEntity(Commune entity) {
        CommuneDto dto = RefBaseDto.fromEntity(entity, CommuneDto.class);
        dto.setProvinceId(entity.getProvince().getId());
        return dto;
    }
}