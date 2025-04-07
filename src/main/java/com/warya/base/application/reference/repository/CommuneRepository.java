package com.warya.base.application.reference.repository;

import com.warya.base.application.reference.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
    List<Commune> findByProvinceId(Long provinceId);
}