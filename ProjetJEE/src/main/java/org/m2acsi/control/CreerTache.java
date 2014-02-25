package org.m2acsi.control;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.m2acsi.boundary.MessageEJB;
import org.m2acsi.boundary.TacheEJB;
import org.m2acsi.boundary.UtilisateurEJB;
import org.m2acsi.entities.Message;
import org.m2acsi.entities.Tache;
import org.m2acsi.entities.Utilisateur;

@Named("nouvelleTaches")
@RequestScoped
public class CreerTache {

    @Inject
    private TacheEJB tacheEJB;

    @Inject
    private UtilisateurEJB utilisateurEJB;

    @Inject
    private MessageEJB messageEJB;

    private Utilisateur utilisateur = (Utilisateur) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("connexionUtilisateur");

    private Tache tache = new Tache();
    private Date echeance;

    private List<Tache> listeTaches;

    private List<Utilisateur> listeUtilisateurs;

    private List<Long> listeParticipants;

    private List<Long> listeParticipantsActif;

    private List<Message> listeMessages;

    private Long pid;

    private Message mess = new Message();

    /**
     * Constructeur
     */
    public CreerTache() {
    }

    /**
     * Getters and setters
     */
    public List<Long> getListeParticipants() {
        return listeParticipants;
    }

    public void setListeParticipants(List<Long> listeParticipants) {
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
        }

        return listeTaches;
    }

    public void setListeTaches(List<Tache> listeTaches) {
        this.listeTaches = listeTaches;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<Message> getListeMessages() {
        return listeMessages;
    }

    public void setListeMessages(List<Message> listeMessages) {
        this.listeMessages = listeMessages;
    }

    public Message getMess() {
        return mess;
    }

    public void setMess(Message mess) {
        this.mess = mess;
    }

    /**
     * Fonction permettant de récupérer les utilisateurs participant à une tâche
     *
     * @return
     */
    public List<Long> getListeParticipantsActif() {
        if (null == listeParticipantsActif) {
            listeParticipantsActif = tacheEJB.listeDeParticipants(pid);
        }
        return listeParticipantsActif;
    }

    public void setListeParticipantsActif(List<Long> listeParticipantsActif) {
        this.listeParticipantsActif = listeParticipantsActif;
    }

    public List<Utilisateur> getListeUtilisateurs() {
        if (null == listeUtilisateurs) {
            listeUtilisateurs = utilisateurEJB.listeUtilisateur();
            for (int i = 0; i < listeUtilisateurs.size(); i++) {
                Utilisateur utili = listeUtilisateurs.get(i);
                if (utili.getId() == this.utilisateur.getId()) {
                    listeUtilisateurs.remove(i);
                }
            }
        }
        return listeUtilisateurs;
    }

    /**
     * Setter
     *
     * @param listeUtilisateurs
     */
    public void setListeUtilisateurs(List<Utilisateur> listeUtilisateurs) {
        this.listeUtilisateurs = listeUtilisateurs;
    }

    public String ajouterTache() {
        tache.setResponsable(utilisateur);
        tache = tacheEJB.creerTache(tache, listeParticipants);
        return "accueil.xhtml?faces-redirect=true";
    }

    /**
     * Fonction permettant de récupérer l'ensemble des tâches
     *
     * @return
     */
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

    /**
     * Fonction permettant de récupérer une tâche à partir de son id
     *
     * @return
     */
    public Tache detailTache() {
        tache = tacheEJB.findTache(pid);
        return tache;
    }

    /**
     * Fonction qui récupère les message en relation avec une tâche
     *
     * @return
     */
    public List<Message> messagesTache() {
        listeMessages = tacheEJB.listeDeMessage(pid);
        return listeMessages;
    }

    /**
     * Fonction qui modifie le contenu de la tâche
     *
     * @return
     */
    public String modifierTache() {

        Tache tacheTMP = tacheEJB.findTache(pid);
        tache.setId(pid);
        tache.setResponsable(tacheTMP.getResponsable());

        tache = tacheEJB.modifierTache(tache, listeParticipantsActif);
        return "accueil.xhtml?faces-redirect=true";
    }

    /**
     * Fonction hashCode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.tacheEJB);
        hash = 47 * hash + Objects.hashCode(this.utilisateurEJB);
        hash = 47 * hash + Objects.hashCode(this.utilisateur);
        hash = 47 * hash + Objects.hashCode(this.tache);
        hash = 47 * hash + Objects.hashCode(this.echeance);
        hash = 47 * hash + Objects.hashCode(this.listeTaches);
        hash = 47 * hash + Objects.hashCode(this.listeUtilisateurs);
        hash = 47 * hash + Objects.hashCode(this.listeParticipants);
        return hash;
    }

    /**
     * Fonction equals
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CreerTache other = (CreerTache) obj;
        if (!Objects.equals(this.tacheEJB, other.tacheEJB)) {
            return false;
        }
        if (!Objects.equals(this.utilisateurEJB, other.utilisateurEJB)) {
            return false;
        }
        if (!Objects.equals(this.utilisateur, other.utilisateur)) {
            return false;
        }
        if (!Objects.equals(this.tache, other.tache)) {
            return false;
        }
        if (!Objects.equals(this.echeance, other.echeance)) {
            return false;
        }
        if (!Objects.equals(this.listeTaches, other.listeTaches)) {
            return false;
        }
        if (!Objects.equals(this.listeUtilisateurs, other.listeUtilisateurs)) {
            return false;
        }
        if (!Objects.equals(this.listeParticipants, other.listeParticipants)) {
            return false;
        }
        return true;
    }

}
