/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m2acsi.control;

import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.m2acsi.boundary.TacheEJB;
import org.m2acsi.boundary.UtilisateurEJB;
import org.m2acsi.entities.Tache;
import org.m2acsi.entities.Utilisateur;

/**
 *
 * @author LoLo
 */
@Named("nouvelleTaches")
@RequestScoped
public class CreerTache {

    @Inject
    private TacheEJB tacheEJB;

    @Inject
    private UtilisateurEJB utilisateurEJB;

    private Utilisateur utilisateur = (Utilisateur) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("connexionUtilisateur");

    private Tache tache = new Tache();
    private Date echeance;

    private List<Tache> listeTaches;

    private List<Utilisateur> listeUtilisateurs;

    private List<Utilisateur> listeParticipants;

    public CreerTache() {
    }

    public List<Utilisateur> getListeParticipants() {
        return listeParticipants;
    }

    public void setListeParticipants(List<Utilisateur> listeParticipants) {
        this.listeParticipants = listeParticipants;
    }

    public TacheEJB getTacheEJB() {
        return tacheEJB;
    }

    public UtilisateurEJB getUtilisateurEJB() {
        return utilisateurEJB;
    }

    public void setUtilisateurEJB(UtilisateurEJB utilisateurEJB) {
        this.utilisateurEJB = utilisateurEJB;
    }

    public void setTacheEJB(TacheEJB tacheEJB) {
        this.tacheEJB = tacheEJB;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Date getEcheance() {
        return echeance;
    }

    public void setEcheance(Date echeance) {
        this.echeance = echeance;
    }

    public List<Tache> getListeTaches() {
        if (null == listeTaches) {
            listeTaches = tacheEJB.listeTache(utilisateur);
//        FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("taille ? "+listeTaches.size()+" element "+listeTaches.get(0)));
        }

        return listeTaches;
    }

    public void setListeTaches(List<Tache> listeTaches) {
        this.listeTaches = listeTaches;
    }

    public List<Utilisateur> getListeUtilisateurs() {
        if (null == listeUtilisateurs) {
            listeUtilisateurs = utilisateurEJB.listeUtilisateur();
            for (int i = 0; i < listeUtilisateurs.size(); i++) {
                Utilisateur utili = listeUtilisateurs.get(i);
                FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("Bloucle"));
                if (utili.getId() == this.utilisateur.getId()) {

                    FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("suppression"));
                    listeUtilisateurs.remove(i);
                }
            }
        }
        return listeUtilisateurs;
    }

    public void setListeUtilisateurs(List<Utilisateur> listeUtilisateurs) {
        this.listeUtilisateurs = listeUtilisateurs;
    }

    public void ajouterTache() {
//        tache.setParticipants(listeParticipants);
        tache.setResponsable(utilisateur);
//        tache.setEcheance(echeance);
        FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("Taille : " + listeParticipants.get(0)));
        
        tache = tacheEJB.creerTache(tache);
        FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("Tache creee"));
    }

    public List<Tache> afficheTache() {
//        FacesContext fc = FacesContext.getCurrentInstance();
//        ConnexionUtilisateur connexionUtilisateur = (ConnexionUtilisateur) fc.getExternalContext().getSessionMap().get("connexionUtilisateur");
//        if(null == connexionUtilisateur){
//            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("connexion null"));
//        }
//        System.out.println("!!!!!!!!!!!!!!!!!!!! "+connexionUtilisateur.getLogin());

//            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage(""+connexionUtilisateur.getLogin()+" objet "+connexionUtilisateur.getUtilisateur()));
//            listeTaches = tacheEJB.listeTache(connexionUtilisateur.getUtilisateur());
        listeTaches = tacheEJB.listeTache(null);
        return listeTaches;
    }

}
