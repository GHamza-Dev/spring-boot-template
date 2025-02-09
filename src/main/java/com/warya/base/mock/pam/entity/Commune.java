package com.warya.base.mock.pam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "commune")
@Getter
@Setter
public class Commune extends RefBaseEntity {
    @ManyToOne
    private Province province;
}