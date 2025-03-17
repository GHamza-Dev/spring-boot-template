package com.warya.base.application.reference.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "NIVEAUETUDE")
public class NiveauEtude extends RefBaseEntity {
    public NiveauEtude() {}
    public NiveauEtude(Long id) {
        super(id);
    }
}