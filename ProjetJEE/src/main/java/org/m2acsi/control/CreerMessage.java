/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m2acsi.control;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.m2acsi.boundary.MessageEJB;
import org.m2acsi.boundary.TacheEJB;
import org.m2acsi.entities.Message;
import org.m2acsi.entities.Tache;
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

    @Inject
    private TacheEJB tacheEJB;

    private Message message = new Message();

    private Utilisateur utilisateur = (Utilisateur) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("connexionUtilisateur");

    private Long pid;

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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public TacheEJB getTacheEJB() {
        return tacheEJB;
    }

    public void setTacheEJB(TacheEJB tacheEJB) {
        this.tacheEJB = tacheEJB;
    }

//    public Message detailMessage(){
//        message = messageEJB.findMessage(pid);
//        return message;
//    }
    public Message rechercheMessage() {

//        FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("ID " + pid));
//        List<Message> lmess = messageEJB.findMessage(tacheEJB.findTache(pid));
//        if(lmess == null){
//            return Arrays.asList(new Message("CAC merde", new Date(), utilisateur, null));
//        }
//        return lmess;
        return new Message("MERDEEEEEEtgsfhfgdhdgh", null, utilisateur, null);
    }

    public void ajouterMessage() {
        message.setUtilisateur(utilisateur);
        message.setDateMessage(new Date());
        FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("pid : " + pid));
        Tache tache = tacheEJB.findTache(pid);
        message.setTache(tache);
        message = messageEJB.creerMessage(message, tache);
    }

}
