package com.warya.base.mock.pam.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class RefBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}