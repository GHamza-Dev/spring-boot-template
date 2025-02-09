package com.warya.base.mock.pam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arrondissement")
@Getter
@Setter
public class Arrondissement extends RefBaseEntity {
    @ManyToOne
    private Province province;
}