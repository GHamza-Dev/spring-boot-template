package com.warya.base.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.warya.base.application.entity.Adherant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdherantRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    
    private String nomFr;
    private String prenomFr;
    
    @NotNull(message = "La date de naissance est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    
    @NotBlank(message = "Le CIN est obligatoire")
    @Pattern(regexp = "^[A-Z]{1,2}\\d{1,6}$", message = "Format de CIN invalide")
    private String cin;
    
    @NotBlank(message = "Le sexe est obligatoire")
    private String sexe;

    private Long niveauEtude;
    private Long profession;
    private Long region;
    private Long province;
    private Long commune;
    private Long arrondissement;
    private Long cercleElectoral;

    @Email(message = "Format d'email invalide")
    private String adresseEmail;
    
    @NotBlank(message = "Le numéro de portable est obligatoire")
    @Pattern(regexp = "^\\d{10}$", message = "Format de numéro de téléphone invalide")
    private String portable;
    
    private String fixe;
    @NotBlank(message = "L'état civil est obligatoire")
    private String etatCivil;
    
    private Long secteurTravailBean;

    private String address;
    private Boolean livingInMorocco;
    private String country;

    public static Adherant toEntity(AdherantRequest request) {
        if (request == null) {
            return null;
        }
        Adherant adherant = new Adherant();
        adherant.setNom(request.getNom());
        adherant.setPrenom(request.getPrenom());
        adherant.setNomFr(request.getNomFr());
        adherant.setPrenomFr(request.getPrenomFr());
        adherant.setDateNaissance(request.getDateNaissance());
        adherant.setCin(request.getCin());
        adherant.setSexe(request.getSexe());
        adherant.setAdresseEmail(request.getAdresseEmail());
        adherant.setPortable(request.getPortable());
        adherant.setFixe(request.getFixe());
        adherant.setEtatCivil(request.getEtatCivil());
        if (request.getLivingInMorocco() != null) {
            adherant.setSituationGeographique(request.getLivingInMorocco() ? "maroc" : "étranger");
        }
        if (request.getAddress() != null) {
            adherant.setAdressePostale(request.getAddress());
        }
        adherant.setDateCreation(new Date());
        return adherant;
    }
}