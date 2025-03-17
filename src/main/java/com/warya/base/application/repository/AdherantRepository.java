package com.warya.base.application.repository;

import com.warya.base.application.entity.Adherant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdherantRepository extends JpaRepository<Adherant, Long> {
    boolean existsByCin(String cin);
}
