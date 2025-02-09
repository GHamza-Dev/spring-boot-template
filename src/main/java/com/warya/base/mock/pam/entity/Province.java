package com.warya.base.mock.pam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "province")
@Getter
@Setter
public class Province extends RefBaseEntity {
    @ManyToOne
    private Region region;
}