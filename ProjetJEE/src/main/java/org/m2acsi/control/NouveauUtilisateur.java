/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m2acsi.control;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.m2acsi.boundary.UtilisateurEJB;
import org.m2acsi.entities.Utilisateur;

/**
 *
 * @author LoLo
 */
@Named("nouveauUtilisateur")
@RequestScoped
public class NouveauUtilisateur {
    
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
    
    public void ajouterUtilisateur(){
        utilisateur = utilisateurEJB.creerUtilisateur(utilisateur);
    }
}
