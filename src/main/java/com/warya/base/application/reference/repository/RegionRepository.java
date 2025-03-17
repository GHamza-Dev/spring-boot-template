package com.warya.base.application.reference.repository;

import com.warya.base.application.reference.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findAllByOrderByOrdreAsc();
}
