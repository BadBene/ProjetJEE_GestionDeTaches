/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m2acsi.control;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.m2acsi.boundary.MessageEJB;
import org.m2acsi.entities.Message;
import org.m2acsi.entities.Utilisateur;

/**
 *
 * @author LoLo
 */
@Named("nouveauMessage")
@RequestScoped
public class CreerMessage {
    
    @Inject
    private MessageEJB messageEJB;
    
    private Message message = new Message();
    
    private Utilisateur utilisateur = (Utilisateur) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("connexionUtilisateur");

    public CreerMessage() {
    }

    public MessageEJB getMessageEJB() {
        return messageEJB;
    }

    public void setMessageEJB(MessageEJB messageEJB) {
        this.messageEJB = messageEJB;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
    
    
    
    public void ajouterMessage(){
        message.setUtilisateur(utilisateur);
        message.setDateMessage(new Date());
        message = messageEJB.creerMessage(message);
    }
    
}
