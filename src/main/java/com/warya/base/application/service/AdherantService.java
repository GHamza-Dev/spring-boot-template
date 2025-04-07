package com.warya.base.application.service;

import com.warya.base.application.dto.AdherantRequest;
import com.warya.base.common.exception.BusinessException;
import com.warya.base.payment.dto.PaymentLinkResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service interface for handling Adherant operations.
 */
public interface AdherantService {
    /**
     * Creates a new adherant with optional photo upload.
     *
     * @param request The adherant request data
     * @param photo Optional photo file
     * @return The created adherant entity
     * @throws BusinessException If there's a business rule violation (e.g., CIN already exists)
     * @throws IOException If there's an issue processing the photo
     */
    PaymentLinkResponse createNewAdherant(AdherantRequest request, MultipartFile photo) throws BusinessException, IOException;
    
    /**
     * Checks if an adherant with the given CIN already exists.
     * 
     * @param cin The CIN to check
     * @return true if an adherant with the given CIN exists, false otherwise
     */
    boolean existsByCin(String cin);
}