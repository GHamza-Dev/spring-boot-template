package com.warya.base.mock.pam.dto;

import com.warya.base.mock.pam.entity.Cercle;
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