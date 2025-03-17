package com.warya.base.application.reference.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "COMMUNE")
@Getter
@Setter
public class Commune extends RefBaseEntity {
    @ManyToOne
    private Province province;

    public Commune() {}

    public Commune(Long id) {
        super(id);
    }
}