package com.warya.base.application.reference;

import com.warya.base.application.reference.dto.*;
import com.warya.base.application.reference.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReferenceService {
    private final NiveauEtudeRepository niveauEtudeRepository;
    private final ProfessionRepository professionRepository;
    private final SecteurRepository secteurRepository;
    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;
    private final CommuneRepository communeRepository;
    private final CercleRepository cercleRepository;
    private final ArrondissementRepository arrondissementRepository;

    public ReferenceService(
            NiveauEtudeRepository niveauEtudeRepository,
            ProfessionRepository professionRepository,
            SecteurRepository secteurRepository,
            RegionRepository regionRepository,
            ProvinceRepository provinceRepository,
            CommuneRepository communeRepository,
            CercleRepository cercleRepository,
            ArrondissementRepository arrondissementRepository) {
        this.niveauEtudeRepository = niveauEtudeRepository;
        this.professionRepository = professionRepository;
        this.secteurRepository = secteurRepository;
        this.regionRepository = regionRepository;
        this.provinceRepository = provinceRepository;
        this.communeRepository = communeRepository;
        this.cercleRepository = cercleRepository;
        this.arrondissementRepository = arrondissementRepository;
    }

    public List<NiveauEtudeDto> getAllNiveauEtude() {
        return niveauEtudeRepository.findAllByOrderByOrdreAsc()
                .stream()
                .map(NiveauEtudeDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProfessionDto> getAllProfession() {
        return professionRepository.findAllByOrderByOrdreAsc()
                .stream()
                .map(ProfessionDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<SecteurDto> getAllSecteur() {
        return secteurRepository.findAllByOrderByOrdreAsc()
                .stream()
                .map(SecteurDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<RegionDto> getAllRegion() {
        return regionRepository.findAllByOrderByOrdreAsc()
                .stream()
                .map(RegionDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ProvinceDto> getAllProvince(Long regionId) {
        return provinceRepository.findByRegionId(regionId)
                .stream()
                .map(ProvinceDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CommuneDto> getAllCommune(Long provinceId) {
        return communeRepository.findByProvinceId(provinceId)
                .stream()
                .map(CommuneDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CercleDto> getAllCercle(Long provinceId) {
        return cercleRepository.findByProvinceId(provinceId)
                .stream()
                .map(CercleDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ArrondissementDto> getAllArrondissement(Long provinceId) {
        return arrondissementRepository.findByProvinceId(provinceId)
                .stream()
                .map(ArrondissementDto::fromEntity)
                .collect(Collectors.toList());
    }
}