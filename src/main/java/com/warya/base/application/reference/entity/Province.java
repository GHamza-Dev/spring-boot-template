package com.warya.base.application.reference.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PROVINCE")
@Getter
@Setter
public class Province extends RefBaseEntity {
    @ManyToOne
    private Region region;

    public Province() {}
    public Province(Long id) {
        super(id);
    }
}