package com.warya.base.mock.pam;

import com.warya.base.mock.pam.dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adhesion")
public class ReferenceController {
    private final ReferenceService referenceService;

    public ReferenceController(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @GetMapping("/getAllNiveauEtude.do")
    public List<NiveauEtudeDto> getAllNiveauEtude() {
        return referenceService.getAllNiveauEtude();
    }

    @GetMapping("/getAllProfession.do")
    public List<ProfessionDto> getAllProfession() {
        return referenceService.getAllProfession();
    }

    @GetMapping("/getAllSecteur.do")
    public List<SecteurDto> getAllSecteur() {
        return referenceService.getAllSecteur();
    }

    @GetMapping("/getAllRegion.do")
    public List<RegionDto> getAllRegion() {
        return referenceService.getAllRegion();
    }

    @GetMapping("/getAllProvince/{region}.do")
    public List<ProvinceDto> getAllProvince(@PathVariable Long region) {
        return referenceService.getAllProvince(region);
    }

    @GetMapping("/getAllCommune/{province}.do")
    public List<CommuneDto> getAllCommune(@PathVariable Long province) {
        return referenceService.getAllCommune(province);
    }

    @GetMapping("/getAllCercle/{province}.do")
    public List<CercleDto> getAllCercle(@PathVariable Long province) {
        return referenceService.getAllCercle(province);
    }

    @GetMapping("/getAllArrondissement/{province}.do")
    public List<ArrondissementDto> getAllArrondissement(@PathVariable Long province) {
        return referenceService.getAllArrondissement(province);
    }
}