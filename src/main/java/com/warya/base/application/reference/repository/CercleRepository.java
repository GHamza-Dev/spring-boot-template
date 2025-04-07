package com.warya.base.application.reference.repository;

import com.warya.base.application.reference.entity.Cercle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CercleRepository extends JpaRepository<Cercle, Long> {
    List<Cercle> findByProvinceId(Long provinceId);
}