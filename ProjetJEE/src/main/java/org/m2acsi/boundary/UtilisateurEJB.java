package org.m2acsi.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.m2acsi.entities.Role;
import org.m2acsi.entities.Utilisateur;
import org.m2acsi.util.Constante;

@Stateless
public class UtilisateurEJB {

    @PersistenceContext(unitName = "com.mycompany_ProjetJEE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Requête permettant d'ajouter un utilisateur
     * @param utilisateur
     * @return 
     */
    public Utilisateur creerUtilisateur(Utilisateur utilisateur) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Role> q = cb.createQuery(Role.class);
        Root<Role> role = q.from(Role.class);
        q.select(role);

        q.where(cb.equal(role.<String>get("nom"), Constante.ROLE_EDITEUR));

        utilisateur.setRole(em.createQuery(q).getResultList().get(0));
        em.persist(utilisateur);
        return utilisateur;
    }

    /**
     * Requête permettant d'afficher l'ensemble des utilisateurs
     * @return 
     */
    public List<Utilisateur> listeUtilisateur() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Utilisateur> q = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> utilisateur = q.from(Utilisateur.class);

        q.select(utilisateur);

        return em.createQuery(q).getResultList();
    }

    /**
     * Requête permettant d'afficher les rôles possibles
     * @return 
     */
    public List<Role> listeRole() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Role> q = cb.createQuery(Role.class);
        Root<Role> role = q.from(Role.class);

        q.select(role);

        return em.createQuery(q).getResultList();
    }

    /**
     * Requête permettant de trouver un utilisateur à partir de son id
     * @param id
     * @return 
     */
    public Utilisateur findUtilisateur(long id) {
        return em.find(Utilisateur.class, id);
    }

    /**
     * Requête permettant de modifier les informations d'un utilisateur
     * @param utilisateur
     * @param role
     * @return 
     */
    public Utilisateur modifierUtilisateur(Utilisateur utilisateur, Long role) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Role> q = cb.createQuery(Role.class);
        Root<Role> ro = q.from(Role.class);

        q.select(ro);

        q.where(cb.equal(ro.<Role>get("id"), role));
        Role r = (Role)em.createQuery(q).getResultList().get(0);
        utilisateur.setRole(r);
        FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("id utili " + utilisateur.getId()));
        em.merge(utilisateur);
        return utilisateur;
    }
}
