package org.m2acsi.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 * Classe entite de la tache
 * 
 * 
 * @author Gauthier Robert & Loic Mathias
 */

@Entity
public class Tache implements Serializable {
    
    /**
     * identifiant de la tache auto-incremente
     */
    @Id
    @GeneratedValue
    private Long id;
    
    /**
     * nom de la tache
     */
//    @Column(nullable = false)
//    @Size(min = 5, max = 20, message = "Nom non conforme !")
    private String nom;
    
    /**
     * description de la tache
     */
//    @Column(nullable = false)
    private String description;
    
    /**
     * date de l'echeance de la tache
     */
//    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date echeance;
    
    /**
     * responsable de la tache
     */
    @ManyToOne
    private Utilisateur responsable;

    /**
     * utilisateurs participants a la tache
     */
    @ManyToMany(mappedBy = "listeDeParticipation")
    private List<Utilisateur> participants;
    
    /**
     * liste de documents present dans la tache
     */
//    @ManyToMany
//    private List<Document> documents;
    
    /**
     * timeline de la tache
     */
//    @ManyToMany
//    private List<Message> timeline;
    
    public Tache() {
    }

    public Tache(String nom, String description, Utilisateur responsable, List<Utilisateur> participants) {
        this.nom = nom;
        this.description = description;
        this.responsable = responsable;
        this.participants = participants;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEcheance() {
        return echeance;
    }

    public void setEcheance(Date echeance) {
        this.echeance = echeance;
    }

    public Utilisateur getResponsable() {
        return responsable;
    }

    public void setResponsable(Utilisateur responsable) {
        this.responsable = responsable;
    }

    public List<Utilisateur> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Utilisateur> participants) {
        this.participants = participants;
    }
    
    public void addParticipant(Long utilisateurID){
        
        
        
        
        
//        if(!participants.contains(utilisateur)){
//            participants.add(utilisateur);
//            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("utili "+utilisateur ));
//        }
//        if(!utilisateur.getListeDeParticipation().contains(this)){
//            utilisateur.getListeDeParticipation().add(this);
//        }
    }

//    @Override
//    public String toString() {
//        return "Tache{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", echeance=" + echeance + ", responsable=" + responsable + ", participants=" + participants + '}';
//    }
    
    
}
