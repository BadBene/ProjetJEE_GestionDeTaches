package org.m2acsi.boundary;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.m2acsi.entities.Message;
import org.m2acsi.entities.Tache;
import org.m2acsi.entities.Utilisateur;
import org.m2acsi.util.Constante;

@Stateless
public class TacheEJB {

    @PersistenceContext(unitName = "com.mycompany_ProjetJEE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    private Long id;

    /**
     * Getters and setters
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Requête permettant de créer une tâche et d'y associer les participants
     *
     * @param tache
     * @param listeParticipants
     * @return
     */
    public Tache creerTache(Tache tache, List<Long> listeParticipants) {
        //select * from Utilisateur where id="$id"
        List<Utilisateur> listeUtili = new ArrayList<Utilisateur>();
        for (Long utili : listeParticipants) {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Utilisateur> q = cb.createQuery(Utilisateur.class);
            Root<Utilisateur> utilisateur = q.from(Utilisateur.class);

            q.select(utilisateur);

            q.where(cb.equal(utilisateur.<Utilisateur>get("id"), utili));
            listeUtili.add(em.createQuery(q).getResultList().get(0));
        }
        tache.setParticipants(listeUtili);
        for (Utilisateur utilisateur : listeUtili) {
            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("!!! " + utilisateur));
            utilisateur.addTache(tache);
        }
        em.persist(tache);
//        em.flush();
        return tache;
    }

    /**
     * Requête permettant lde récupérer a liste de tâche en fonction de
     * l'utilisateur (et de son rôle)
     *
     * @param utilisateur
     * @return
     */
    public List<Tache> listeTache(Utilisateur utilisateur) {
        if (Constante.ROLE_RESPONSABLE.equals(utilisateur.getRole().getNom())) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tache> q = cb.createQuery(Tache.class);
            Root<Tache> tache = q.from(Tache.class);

            q.select(tache);
            return em.createQuery(q).getResultList();
        } else {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tache> q = cb.createQuery(Tache.class);
            Root<Tache> tache = q.from(Tache.class);

            q.select(tache);
            List<Tache> lTache = em.createQuery(q).getResultList();

            List<Tache> lTacheTmp = new ArrayList<Tache>();

            if (null != lTache) {
                for (Tache tache1 : lTache) {
                    for (Utilisateur utili : tache1.getParticipants()) {
                        if (utili.getId() == utilisateur.getId()) {
                            lTacheTmp.add(tache1);
                        }
                    }
                }
                return lTacheTmp;

            }
            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("pas de taches"));
            //mettre erreur !!
            return null;
        }

    }

    /**
     * Requête permettant de trouver une tâche avec son id
     *
     * @param id
     * @return
     */
    public Tache findTache(long id) {
        return em.find(Tache.class, id);
    }

    /**
     * Requête permettant d'update les informations d'une tâche
     *
     * @param tache
     * @param listeeParticipant
     * @return
     */
    public Tache modifierTache(Tache tache, List<Long> listeeParticipant) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Utilisateur> q = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> utilisateur = q.from(Utilisateur.class);

        q.select(utilisateur);
        List<Utilisateur> allUtilisateur = em.createQuery(q).getResultList();

        List<Utilisateur> listeUtili = new ArrayList<Utilisateur>();
        for (Long utili : listeeParticipant) {
            listeUtili.add(em.find(Utilisateur.class, utili));
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Utilisateur> q = cb.createQuery(Utilisateur.class);
//            Root<Utilisateur> utilisateur = q.from(Utilisateur.class);
//
//            q.select(utilisateur);
//
//            q.where(cb.equal(utilisateur.<Utilisateur>get("id"), utili));
//            listeUtili.add(em.createQuery(q).getResultList().get(0));
        }
        tache.setParticipants(listeUtili);
        for (Utilisateur utilisateur1 : allUtilisateur) {
            if (!listeUtili.contains(utilisateur1) && utilisateur1.getListeDeParticipation().contains(tache)) {
                utilisateur1.removeTache(tache);
            } else if (listeUtili.contains(utilisateur1) && !utilisateur1.getListeDeParticipation().contains(tache)) {
                utilisateur1.addTache(tache);
            }

            em.merge(utilisateur1);
        }

//        for (Utilisateur utilisateurB : listeUtili) {
//            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("!!! " + utilisateur));
//            if (!utilisateurB.getListeDeParticipation().contains(tache)) {
//                utilisateurB.addTache(tache);
//            }
//        }
        em.merge(tache);
        return tache;
    }

    /**
     * Requête permettant de récupérer les participants d'une tâche (id de la
     * tâche en entrée)
     *
     * @param pid
     * @return
     */
    public List<Long> listeDeParticipants(Long pid) {
        if(null != pid){
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Tache> q = cb.createQuery(Tache.class);
//        Root<Tache> tache = q.from(Tache.class);
//
//        q.select(tache);
//        q.where(cb.equal(tache.<Tache>get("id"), pid));

//       Tache t = em.createQuery(q).getResultList().get(0);
        FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("pas de taches " + pid));
        Tache ta = findTache(pid);

        FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("pas de taches " + ta.getParticipants().size()));

        List<Long> liParticipant = new ArrayList<Long>();
        List<Utilisateur> liUtili = ta.getParticipants();
        for (Utilisateur utili : liUtili) {
            liParticipant.add(utili.getId());
        }

        return liParticipant;
        }
        return null;
    }

    /**
     * Requête permettant de trouver l'ensemble des messages d'une tâche
     */
    public List<Message> listeDeMessage(Long pid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tache> q = cb.createQuery(Tache.class);
        Root<Tache> tache = q.from(Tache.class);

        q.select(tache);
        q.where(cb.equal(tache.<Tache>get("id"), pid));

        Tache t = em.createQuery(q).getResultList().get(0);
        return t.getTimeline();
    }
}
