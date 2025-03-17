package com.warya.base.application.reference.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "PROFESSION")
public class Profession extends RefBaseEntity {
    public Profession() {}
    public Profession(Long id) {
        super(id);
    }
}
