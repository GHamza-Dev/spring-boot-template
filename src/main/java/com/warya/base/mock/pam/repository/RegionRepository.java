package com.warya.base.mock.pam.repository;

import com.warya.base.mock.pam.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findAllByOrderByOrdreAsc();
}
