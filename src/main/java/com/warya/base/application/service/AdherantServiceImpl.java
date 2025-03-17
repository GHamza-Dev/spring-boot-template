package com.warya.base.application.service;

import com.warya.base.application.dto.AdherantRequest;
import com.warya.base.application.entity.Adherant;
import com.warya.base.application.entity.PhotoAdherent;
import com.warya.base.application.reference.repository.*;
import com.warya.base.application.repository.AdherantRepository;
import com.warya.base.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Log4j2
@RequiredArgsConstructor
public class AdherantServiceImpl implements AdherantService {
    private final AdherantRepository adherantRepository;
    private final NiveauEtudeRepository niveauEtudeRepository;
    private final ProfessionRepository professionRepository;
    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;
    private final CommuneRepository communeRepository;
    private final ArrondissementRepository arrondissementRepository;
    private final CercleRepository cercleRepository;
    private final SecteurRepository secteurRepository;

    @Value("${adherant.photo.max-size}")
    private long maxPhotoSize;

    @Override
    @Transactional
    public void createNewAdherant(AdherantRequest request, MultipartFile photo) throws BusinessException, IOException {
        if (request == null) {
            throw new BusinessException("adherant_data_required", HttpStatus.BAD_REQUEST);
        }

        if (existsByCin(request.getCin())) {
            log.info("Adherant with CIN: {} already exists", request.getCin());
            throw new BusinessException("cin_exist", HttpStatus.CONFLICT);
        }

        Adherant adherant = AdherantRequest.toEntity(request);

        fetchAndSetRelatedEntities(adherant, request);

        if (photo != null && !photo.isEmpty()) {
            processPhoto(adherant, photo);
        }

        adherantRepository.save(adherant);
    }

    @Override
    public boolean existsByCin(String cin) {
        if (cin == null || cin.trim().isEmpty()) {
            return false;
        }
        return adherantRepository.existsByCin(cin);
    }

    private void fetchAndSetRelatedEntities(Adherant adherant, AdherantRequest request) {
        if (request.getNiveauEtude() != null) {
            niveauEtudeRepository.findById(request.getNiveauEtude())
                    .ifPresent(adherant::setNiveauEtude);
        }

        if (request.getProfession() != null) {
            professionRepository.findById(request.getProfession())
                    .ifPresent(adherant::setProfession);
        }

        if (request.getRegion() != null) {
            regionRepository.findById(request.getRegion())
                    .ifPresent(adherant::setRegion);
        }

        if (request.getProvince() != null) {
            provinceRepository.findById(request.getProvince())
                    .ifPresent(adherant::setProvince);
        }

        if (request.getCommune() != null) {
            communeRepository.findById(request.getCommune())
                    .ifPresent(adherant::setCommune);
        }

        if (request.getArrondissement() != null) {
            arrondissementRepository.findById(request.getArrondissement())
                    .ifPresent(adherant::setArrondissement);
        }

        if (request.getCercleElectoral() != null) {
            cercleRepository.findById(request.getCercleElectoral())
                    .ifPresent(adherant::setCercleElectoral);
        }

        if (request.getSecteurTravailBean() != null) {
            secteurRepository.findById(request.getSecteurTravailBean())
                    .ifPresent(adherant::setSecteurTravail);
        }
    }

    private void processPhoto(Adherant adherant, MultipartFile photo) throws BusinessException, IOException {
        if (photo.getSize() > maxPhotoSize) {
            throw new BusinessException("photo_too_large",
                    new Object[]{maxPhotoSize / (1024 * 1024)},
                    HttpStatus.BAD_REQUEST);
        }

        PhotoAdherent photoAdherent = new PhotoAdherent();
        photoAdherent.setNomFichier(photo.getOriginalFilename());
        photoAdherent.setContenu(photo.getBytes());
        photoAdherent.setMimeType(photo.getContentType());

        String originalFilename = photo.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            photoAdherent.setExtension(extension);
        }

        adherant.setPhotoAdherent(photoAdherent);
    }
}