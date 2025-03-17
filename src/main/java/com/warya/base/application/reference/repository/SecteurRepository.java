package com.warya.base.application.reference.repository;

import com.warya.base.application.reference.entity.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {
    List<Secteur> findAllByOrderByOrdreAsc();
}