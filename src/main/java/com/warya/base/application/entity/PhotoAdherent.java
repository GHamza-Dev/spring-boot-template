package com.warya.base.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "PHOTOADHERENT")
@Getter
@Setter
@NoArgsConstructor
public class PhotoAdherent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String nomFichier;
    
    @Lob
    private byte[] contenu;
    
    private String extension;
    
    @Column(name = "mine_type")
    private String mimeType;

    public PhotoAdherent(Long id) {
        this.id = id;
    }
}