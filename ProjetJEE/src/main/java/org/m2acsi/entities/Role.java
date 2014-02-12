package org.m2acsi.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Classe entite du role
 * 
 * 
 * @author Gauthier Robert & Loic Mathias
 */

@Entity
public class Role implements Serializable {
    
    /**
     * identifiant du role auto-incremente
     */
    @Id
//    @GeneratedValue
    private Long id;
    
    /**
     * nom du role
     */
    private String nom;

    public Role() {
    }

    public Role(String nom) {
        this.nom = nom;
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

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", nom=" + nom + '}';
    }
    
    
}
