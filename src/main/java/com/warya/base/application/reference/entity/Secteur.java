package com.warya.base.application.reference.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "SECTEURTRAVAIL")
public class Secteur extends RefBaseEntity {
    public Secteur() {}
    public Secteur(Long id) {
        super(id);
    }
}