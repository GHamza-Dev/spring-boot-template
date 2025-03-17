package com.warya.base.application.reference.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cercle")
@Getter
@Setter
public class Cercle extends RefBaseEntity {
    @ManyToOne
    private Province province;
}