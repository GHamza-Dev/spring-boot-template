package com.warya.base.application.entity;

import com.warya.base.application.reference.entity.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ADHERANT", uniqueConstraints = { @UniqueConstraint(columnNames = {"cin"}) })
@Getter
@Setter
@NoArgsConstructor
public class Adherant implements Serializable {

	private static final long serialVersionUID = -6989898044088131647L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String nom;
	private String prenom;
	private String nomFr;
	private String prenomFr;
	private Date dateNaissance;
	private String cin;
	private String sexe;
	
	@ManyToOne
	private NiveauEtude niveauEtude;
	
	@ManyToOne
	private Profession profession;
	
	@ManyToOne
	private Region region;
	
	@ManyToOne
	private Province province;
	
	@ManyToOne
	private Commune Commune;
	
	@ManyToOne
	private Arrondissement arrondissement;
	
	@ManyToOne
	private Cercle cercleElectoral;
	
	private String adressePostale;
	private String adresseEmail;
	private String portable;
	private String fixe;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "adherant", orphanRemoval = true, targetEntity = AppartenanceStructureForAdherent.class)
//	private List<AppartenanceStructureForAdherent> appartenancesStructure;
	
//	@OneToOne
//	private ExAppartenanceStructure ExAppartenanceStructure;
	
	private String experienceElectorale;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "adherant", orphanRemoval = true, targetEntity = PosteElectoralForAdherent.class)
//	private List<PosteElectoralForAdherent> postesElectoral;
	
	private String numeroInscriptionElecto;
	
//	@OneToOne
//	private AppartenanceSyndical appartenanceSyndical;
	
	private String appartenanceAssociative;
	private String numeroAdherant;
	private Date dateDemadne;
	private String etatAdherant;
	private String raisonSocial;
	private String faceBook;
	private String twitter;
	private String linkedin;
	private String instagram;
	private String autre;
	private Date dateCreation;
	private Date dateEnvoie;
	private String remarqueRegionale;
	private String remarqueAdministration;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "adherant", orphanRemoval = true, targetEntity = Cotisation.class)
//	private List<Cotisation> cotisations;
	
	private String etatCivil;
	private String nomFemme;
	private String prenomFemme;
	private String cinFemme;
	
	@OneToOne
	private Profession professionFemme;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "adherant", orphanRemoval = true, targetEntity = FilsForAdherant.class)
//	private List<FilsForAdherant> filsForAdherant;
	
	private String comment;
	
	@OneToOne
	private Adherant parrain;
	
	private String qstInscrisElection;
	private String qstLieuResidence;
	
//	@OneToOne
//	private PaysResidence lieuResidence;
	
	private String statut;
	private String motifQuitter;
	private String qstTypeParticipation;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	private PvQuitterPartie pvQuitterPartie;
	
	private Date dateDemissionRevocation;
	private String remarqueDemissionRevocation;
	
	@OneToOne(cascade = CascadeType.ALL)
	private PhotoAdherent photoAdherent;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	private DocumentAdherent documentAdherent;
	
//	@Transient
//	private AppartenanceStructureForAdherent appartenanceStructure;
	
//	@Transient
//	private PosteElectoralForAdherent posteElectoral;
	
//	@OneToOne
//	private Utilisateur userTraiter;
	
	@ManyToOne
	private Secteur secteurTravail;
	
	@Transient
	private Date dateDebut;
	
	@Transient
	private Date dateFin;
	
	@Transient
	private String anneeCotisation;
	
	@Transient
	private String modePaiement;
	
	@Transient
	private Double fraisAdhesion;
	
	private String situationGeographique;
	
	@Transient
	private Integer nbrAdhesion;
	
	// Constructors preserved from original class
	public Adherant(Long id) {
		this.id = id;
	}
	
	public Adherant(Long id, String nom) {
		this.id = id;
		this.nom = nom;
	}
	
	public Adherant(Long id, String nom, String prenom) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}
}