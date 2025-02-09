package com.warya.base.mock.pam.repository;

import com.warya.base.mock.pam.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
    List<Commune> findByProvinceId(Long provinceId);
}