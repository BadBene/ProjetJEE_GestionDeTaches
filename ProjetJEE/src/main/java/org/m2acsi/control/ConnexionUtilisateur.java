/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m2acsi.control;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.m2acsi.entities.Utilisateur;
import org.m2acsi.util.Encryptage;

/**
 *
 * @author LoLo
 */
@Named("connexionUtilisateur")
@SessionScoped
//sessionscoped jsf 2
public class ConnexionUtilisateur implements Serializable{
    @PersistenceContext(unitName="com.mycompany_ProjetJEE_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    private String login = "";
    private String motDePasse = "";
    
    private boolean isLoggedIn;
    private boolean isRedirect;

    public ConnexionUtilisateur() {
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

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean isIsRedirect() {
        return isRedirect;
    }

    public void setIsRedirect(boolean isRedirect) {
        this.isRedirect = isRedirect;
    }
    
    public List<Utilisateur> requeteConnexion(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Utilisateur> q = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> utilisateur = q.from(Utilisateur.class);
        q.select(utilisateur);
        motDePasse = Encryptage.MD5(motDePasse);
        Predicate andClause = cb.and(cb.equal(utilisateur.<String>get("login"), login), cb.equal(utilisateur.<String>get("motDePasse"), motDePasse));
        
        
        q.where(andClause);
        
        
        return em.createQuery(q).getResultList();
    }
    
    public String verificationConnexion(){
        String redirection;
        if(1 == requeteConnexion().size()){
            isLoggedIn = true;
            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("Connecte"));
            redirection =  "index.html?faces-redirect=true";
        }else{
            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("Erreur connexion"));
            redirection =  "connexion.xhtml?faces-redirect=true";
        }
        return redirection;
    }
    
}
