package com.warya.base.application.reference.dto;

import com.warya.base.application.reference.entity.RefBaseEntity;
import lombok.Data;

@Data
public abstract class RefBaseDto {
    private Long id;
    private String libelle;
    private Double soldeResponsable;
    private Double soldeInterne;
    private Double soldeExterne;
    private String code;
    private Double cotisation;
    private String responsable;
    private Integer nbrBureau;
    private Integer nbrBureauVote;
    private String statut;
    private Integer ordre;

    public static <T extends RefBaseDto> T fromEntity(RefBaseEntity entity, Class<T> dtoClass) {
        try {
            T dto = dtoClass.getDeclaredConstructor().newInstance();
            dto.setId(entity.getId());
            dto.setLibelle(entity.getLibelle());
            dto.setSoldeResponsable(entity.getSoldeResponsable());
            dto.setSoldeInterne(entity.getSoldeInterne());
            dto.setSoldeExterne(entity.getSoldeExterne());
            dto.setCode(entity.getCode());
            dto.setCotisation(entity.getCotisation());
            dto.setResponsable(entity.getResponsable());
            dto.setNbrBureau(entity.getNbrBureau());
            dto.setNbrBureauVote(entity.getNbrBureauVote());
            dto.setStatut(entity.getStatut());
            dto.setOrdre(entity.getOrdre());
            return dto;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping entity to DTO", e);
        }
    }
}