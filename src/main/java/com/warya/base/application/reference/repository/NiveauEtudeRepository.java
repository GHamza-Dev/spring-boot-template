package com.warya.base.application.reference.repository;

import com.warya.base.application.reference.entity.NiveauEtude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NiveauEtudeRepository extends JpaRepository<NiveauEtude, Long> {
    List<NiveauEtude> findAllByOrderByOrdreAsc();
}