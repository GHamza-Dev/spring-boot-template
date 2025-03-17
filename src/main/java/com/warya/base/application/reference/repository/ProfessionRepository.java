package com.warya.base.application.reference.repository;

import com.warya.base.application.reference.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {
    List<Profession> findAllByOrderByOrdreAsc();
}