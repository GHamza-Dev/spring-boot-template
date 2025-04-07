package com.warya.base.application.reference.repository;

import com.warya.base.application.reference.entity.Arrondissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArrondissementRepository extends JpaRepository<Arrondissement, Long> {
    List<Arrondissement> findByProvinceId(Long provinceId);
}