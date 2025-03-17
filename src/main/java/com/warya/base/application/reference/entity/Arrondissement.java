package com.warya.base.application.reference.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ARRONDISSEMENT")
@Getter
@Setter
public class Arrondissement extends RefBaseEntity {
    @ManyToOne
    private Province province;

    public Arrondissement(Long id) {
        super(id);
    }

    public Arrondissement() {

    }
}