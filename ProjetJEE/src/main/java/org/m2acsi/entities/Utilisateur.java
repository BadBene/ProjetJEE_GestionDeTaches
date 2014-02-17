package org.m2acsi.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Classe entite de l'utilisateur
 * 
 * 
 * @author Gauthier Robert & Loic Mathias
 */

@Entity
public class Utilisateur implements Serializable{

    /**
     * identifiant de l'utilisateur auto-incremente
     */
    @Id @GeneratedValue
    private Long id;

    /**
     * login de l'utilisateur
     */
//    @Column(nullable = false)
//    @Size(min = 5, max = 20, message = "Login non conforme !")
    private String login;

    /**
     * mot de passe de l'utilisateur
     */
//    @Column(nullable = false)
//    @Size(min = 5, max = 20, message = "Mot de passe non conforme !")
    private String motDePasse;

    /**
     * role de l'utilisateur
     * ! ! voir si garder cascade ! !
     */
    @ManyToOne
    private Role role;
    
    /**
     * nom de l'utilisateur
     */
//    @Size(min = 1, max = 20, message = "Nom vide !")
    private String nom;

    /**
     * prenom de l'utilisateur
     */
//    @Size(min = 1, max = 20, message = "Prenom vide !")
    private String prenom;

    /**
     * email de l'utilisateur
     * ! ! expression reguliere du format du mail
     */
//    @Size(min = 6, max = 50, message = "Email non conforme !")
    //^[a-Z]+@
    private String email;
    
    @ManyToMany
    private List<Tache> listeDeParticipation;
    
    /**
     * date de creation du compte de l'utilisateur
     */
//    @Temporal(javax.persistence.TemporalType.DATE)
//    private Date dateCreation;

    public Utilisateur() {
    }

    public Utilisateur(String login, String motDePasse, String nom, String prenom, String email, Role role) {
        this.login = login;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
//        this.dateCreation = new Date(System.currentTimeMillis());         
    }    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Tache> getListeDeParticipation() {
        return listeDeParticipation;
    }

    public void setListeDeParticipation(List<Tache> listeDeParticipation) {
        this.listeDeParticipation = listeDeParticipation;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//    public Date getDateCreation() {
//        return dateCreation;
//    }
//
//    public void setDateCreation(Date dateCreation) {
//        this.dateCreation = dateCreation;
//    }
//    @Override
//    public String toString() {
//        return "Utilisateur{" + "id=" + id + ", login=" + login + ", motDePasse=" + motDePasse + ", role=" + role + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + '}';
//    }
    
    public void addTache(Tache tache){
        listeDeParticipation.add(tache);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.login);
        hash = 23 * hash + Objects.hashCode(this.motDePasse);
        hash = 23 * hash + Objects.hashCode(this.role);
        hash = 23 * hash + Objects.hashCode(this.nom);
        hash = 23 * hash + Objects.hashCode(this.prenom);
        hash = 23 * hash + Objects.hashCode(this.email);
        return hash;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utilisateur other = (Utilisateur) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.motDePasse, other.motDePasse)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
       
        return true;
    }
    

    
}
