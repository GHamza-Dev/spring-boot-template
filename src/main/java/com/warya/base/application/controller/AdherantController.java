package com.warya.base.application.controller;

import com.warya.base.application.dto.AdherantRequest;
import com.warya.base.application.entity.Adherant;
import com.warya.base.application.service.AdherantService;
import com.warya.base.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/adherants")
@RequiredArgsConstructor
@Slf4j
public class AdherantController {
    private final AdherantService adherantService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Adherant> createAdherant(
            @RequestPart("adherant") AdherantRequest adherantData,
            @RequestPart(value = "photo", required = false) MultipartFile photo) 
            throws BusinessException, IOException {
        log.info("Creating new adherant with CIN: {}", adherantData.getCin());
        adherantService.createNewAdherant(adherantData, photo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/check-cin/{cin}")
    public ResponseEntity<Boolean> checkCinExists(@PathVariable String cin) {
        boolean exists = adherantService.existsByCin(cin);
        return ResponseEntity.ok(exists);
    }
}