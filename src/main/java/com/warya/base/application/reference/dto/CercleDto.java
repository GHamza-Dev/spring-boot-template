package com.warya.base.application.reference.dto;

import com.warya.base.application.reference.entity.Cercle;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CercleDto extends RefBaseDto {
    private Long provinceId;

    public static CercleDto fromEntity(Cercle entity) {
        CercleDto dto = RefBaseDto.fromEntity(entity, CercleDto.class);
        dto.setProvinceId(entity.getProvince().getId());
        return dto;
    }
}