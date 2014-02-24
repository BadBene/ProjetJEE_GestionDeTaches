package org.m2acsi.control;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.m2acsi.boundary.UtilisateurEJB;
import org.m2acsi.entities.Role;
import org.m2acsi.entities.Utilisateur;
import org.m2acsi.util.Encryptage;


@Named("nouveauUtilisateur")
@RequestScoped
public class NouveauUtilisateur {
    
    @Inject
    private UtilisateurEJB utilisateurEJB;
    
    private Utilisateur utilisateur = new Utilisateur();
    
    private Utilisateur utilisateurConnecte = (Utilisateur) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("connexionUtilisateur");
    
    private Long pid;
    
    private List<Role> listeRole;
    
    private Long roleModifie;

    /**
     * Constructeurs
     */
    public NouveauUtilisateur() {
    }

    public UtilisateurEJB getUtilisateurEJB() {
        return utilisateurEJB;
    }

    /**
     * Getters and setters
     *
     */
    public void setUtilisateurEJB(UtilisateurEJB utilisateurEJB) {
        this.utilisateurEJB = utilisateurEJB;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }

    public List<Role> getListeRole() {
        if(null == listeRole){
            listeRole = utilisateurEJB.listeRole();
            listeRole.remove(utilisateur.getRole());
        }
        return listeRole;
    }

    public void setListeRole(List<Role> listeRole) {
        this.listeRole = listeRole;
    }

    public Long getRoleModifie() {
        return roleModifie;
    }

    public void setRoleModifie(Long roleModifie) {
        this.roleModifie = roleModifie;
    }  
    
    /**
     * Fonction permettant d'ajouter un utilisateur
     * @return 
     */
    public String ajouterUtilisateur(){
        utilisateur.setMotDePasse(Encryptage.MD5(utilisateur.getMotDePasse()));
        utilisateur.setRole(null);
        utilisateur = utilisateurEJB.creerUtilisateur(utilisateur);
        return "connexion.xhtml?faces-redirect=true";
    }
    
    /**
     * Fonction permettant de modifier un utilisateur 
     * @return 
     */
    public String modifierUtilisateur(){
        Utilisateur utilisateurTMP = utilisateurEJB.findUtilisateur(pid);
        utilisateur.setId(pid);
        if(utilisateur.getMotDePasse().equals("") || utilisateur.getMotDePasse() == null){
            
        utilisateur.setMotDePasse(utilisateurTMP.getMotDePasse());
        }else{
            
        utilisateur.setMotDePasse(Encryptage.MD5(utilisateur.getMotDePasse()));
        }
//        utilisateur.setRole(utilisateur.g);
        utilisateur = utilisateurEJB.modifierUtilisateur(utilisateur, roleModifie);
        return "connexion.xhtml?faces-redirect=true";
    }
    
    /**
     * Fonction permettant de modifier le rôle d'un utilisateur
     * @return 
     */
    public boolean modifierRole(){
        return 1 == utilisateur.getRole().getId();
    }
    
    
    /**
     * Fonction permettant de trouver un utilisateur à partir de son id
     * @return 
     */
    public Utilisateur detailUtilisateur(){
        utilisateur = utilisateurEJB.findUtilisateur(pid);
        return utilisateur;
    }
}
