package com.warya.base.application.reference.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "REGION")
public class Region extends RefBaseEntity {
    public Region() {}
    public Region(Long id) {
        super(id);
    }
}