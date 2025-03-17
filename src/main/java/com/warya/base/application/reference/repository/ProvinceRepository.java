package com.warya.base.application.reference.repository;

import com.warya.base.application.reference.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    List<Province> findByRegionId(Long regionId);
}