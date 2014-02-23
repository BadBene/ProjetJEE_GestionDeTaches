    package org.m2acsi.entities;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 * Classe entite de la timeline pour une tache
 *
 *
 * @author Gauthier Robert & Loic Mathias
 */
@Entity
public class Message implements Serializable{

    /**
     * identifiant de la tache auto-incremente
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * message d'un utilisateur pour une tache
     */
//    @Column(nullable = false)
    private String message;

    /**
     * date d'emmission d'un message
     */
//    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateMessage;

    /**
     * utilisateur ayant creer le message
     */
//    @Column(nullable = false)
    @ManyToOne
    private Utilisateur utilisateur;
    
    @ManyToOne
    private Tache tache;

    public Message() {
    }

    public Message(String message, Date dateMessage, Utilisateur utilisateur, Tache tache) {
        this.message = message;
        this.dateMessage = dateMessage;
        this.utilisateur = utilisateur;
        this.tache = tache;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }
    
    
}
