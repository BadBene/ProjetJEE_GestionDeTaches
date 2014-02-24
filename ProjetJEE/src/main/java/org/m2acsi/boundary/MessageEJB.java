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
import org.m2acsi.entities.Message;
import org.m2acsi.entities.Tache;

@Stateless
public class MessageEJB {

    @PersistenceContext(unitName = "com.mycompany_ProjetJEE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     * Requête permettant d'ajouter un message
     * @param message
     * @param t
     * @return 
     */
    public Message creerMessage(Message message, Tache t) {
        FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("tache " + t.getNom()));
        t.addMessage(message);
        em.persist(message);
        em.merge(t);
        return message;
    }

    /**
     * Requête permettant de trouver les messages à partir de l'id d'une tâche
     */
    public List<Message> findMessage(Long pid) {
        Tache t = em.find(Tache.class, pid);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> q = cb.createQuery(Message.class);
        Root<Message> message = q.from(Message.class);

        q.select(message);

        q.where(cb.equal(message.<Message>get("tache"), t));
        return em.createQuery(q).getResultList();
    }
}
