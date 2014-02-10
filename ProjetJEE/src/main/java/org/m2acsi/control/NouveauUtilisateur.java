/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m2acsi.control;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.m2acsi.boundary.UtilisateurEJB;
import org.m2acsi.entities.Utilisateur;
import org.m2acsi.util.Encryptage;

/**
 *
 * @author LoLo
 */
@Named("nouveauUtilisateur")
@RequestScoped
public class NouveauUtilisateur {
    
    @EJB
    @Inject
    private UtilisateurEJB utilisateurEJB;
    
    private Utilisateur utilisateur = new Utilisateur();

    public NouveauUtilisateur() {
    }

    public UtilisateurEJB getUtilisateurEJB() {
        return utilisateurEJB;
    }

    public void setUtilisateurEJB(UtilisateurEJB utilisateurEJB) {
        this.utilisateurEJB = utilisateurEJB;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    
    public String ajouterUtilisateur(){
        utilisateur.setMotDePasse(Encryptage.MD5(utilisateur.getMotDePasse()));
        utilisateur.setRole(null);
        utilisateur = utilisateurEJB.creerUtilisateur(utilisateur);
        return "nouveauUtilisateur.xhtml?faces-redirect=true";
    }
    
//    public String modifierUtilisateur(){
//        utilisateur = utilisateurEJB.modifierUtilisateur(utilisateur);
//        return "nouveauUtilisateur.xhtml?faces-redirect=true";
//    }
}
