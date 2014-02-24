/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m2acsi.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Gauthier Robert & Loic Mathias
 */
@Entity
public class Fichier implements Serializable{

    /**
    Identifiant du fichier en auto-incrémenté
    */
    @Id
    @GeneratedValue
    private Long id;
    
    /**
     * Nom du fichier
     */
    private String nomFichier;
    
    /**
     * Lien physique du fichier
     */
    private String lienFichier;
    
    /**
     * Utilisateur ayant uploadé le fichier
     */
    @ManyToOne
    private Utilisateur proprietaireFichier;
    
    @ManyToOne
    private Tache tache;
    
    /**
     * Constructeurs
     */
    public Fichier() {
    }

    public Fichier(String nomFichier, String lienFichier, Utilisateur proprietaireFichier, Tache tache) {
        this.nomFichier = nomFichier;
        this.lienFichier = lienFichier;
        this.proprietaireFichier = proprietaireFichier;
        this.tache = tache;
    }

    

    /**
     * Getters and Setters
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getLienFichier() {
        return lienFichier;
    }

    public void setLienFichier(String lienFichier) {
        this.lienFichier = lienFichier;
    }

    public Utilisateur getProprietaireFichier() {
        return proprietaireFichier;
    }

    public void setProprietaireFichier(Utilisateur proprietaireFichier) {
        this.proprietaireFichier = proprietaireFichier;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    @Override
    public String toString() {
        return "Fichier{" + "id=" + id + ", nomFichier=" + nomFichier + ", lienFichier=" + lienFichier + ", proprietaireFichier=" + proprietaireFichier + '}';
    }

    
}
