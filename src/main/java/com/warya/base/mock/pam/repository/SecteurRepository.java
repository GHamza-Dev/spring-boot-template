package com.warya.base.mock.pam.repository;

import com.warya.base.mock.pam.entity.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {
    List<Secteur> findAllByOrderByOrdreAsc();
}