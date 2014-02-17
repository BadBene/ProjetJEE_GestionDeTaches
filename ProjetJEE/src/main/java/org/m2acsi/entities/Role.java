package org.m2acsi.entities;

import java.io.Serializable;
import java.util.Objects;
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

//    @Override
//    public String toString() {
//        return "Role{" + "id=" + id + ", nom=" + nom + '}';
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.nom);
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
        final Role other = (Role) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }
    
    
}
