/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m2acsi.boundary;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.m2acsi.entities.Tache;
import org.m2acsi.entities.Utilisateur;
import org.m2acsi.util.Constante;

/**
 *
 * @author LoLo
 */
@Stateless
public class TacheEJB {

    @PersistenceContext(unitName = "com.mycompany_ProjetJEE_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public Tache creerTache(Tache tache) {

        em.persist(tache);
        return tache;
    }

    public List<Tache> listeTache(Utilisateur utilisateur) {
        //si responsable retourne toute les taches
        //select * from Tache

        if (utilisateur.getRole().getNom().equals(Constante.ROLE_RESPONSABLE)) {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tache> q = cb.createQuery(Tache.class);
            Root<Tache> tache = q.from(Tache.class);
            q.select(tache);

            return em.createQuery(q).getResultList();
        } else {
            //sinon, select * from Tache_utilisateur where participants-id=utilisateur.id
            //select * from tache t join utilisateur u where u.id = utilisateur.id

            CriteriaBuilder cb = em.getCriteriaBuilder();

            CriteriaQuery<Tache> q = cb.createQuery(Tache.class);
            Root<Tache> tache = q.from(Tache.class);
            Join<Tache, Utilisateur> u = tache.join("Utilisateur");

            q.select(tache);

            q.where(cb.equal(tache.<Long>get("id"), utilisateur.getId()));
            return em.createQuery(q).getResultList();

        }
    }
}
