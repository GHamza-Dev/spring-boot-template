package com.warya.base.mock.pam.repository;

import com.warya.base.mock.pam.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {
    List<Profession> findAllByOrderByOrdreAsc();
}