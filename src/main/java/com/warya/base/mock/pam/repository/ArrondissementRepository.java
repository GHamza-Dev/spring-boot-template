package com.warya.base.mock.pam.repository;

import com.warya.base.mock.pam.entity.Arrondissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArrondissementRepository extends JpaRepository<Arrondissement, Long> {
    List<Arrondissement> findByProvinceId(Long provinceId);
}