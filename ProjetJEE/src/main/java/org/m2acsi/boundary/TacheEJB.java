/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
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

    public List<Tache> listeTache(Utilisateur utilisateur) {
        if (Constante.ROLE_RESPONSABLE.equals(utilisateur.getRole().getNom())) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tache> q = cb.createQuery(Tache.class);
            Root<Tache> tache = q.from(Tache.class);

            q.select(tache);
            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("responsable"));
            return em.createQuery(q).getResultList();
        } else {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tache> q = cb.createQuery(Tache.class);
            Root<Tache> tache = q.from(Tache.class);

            q.select(tache);
            List<Tache> lTache = em.createQuery(q).getResultList();
            
            List<Tache> lTacheTmp = new ArrayList<Tache>();
            
            if(null != lTache){
                for (Tache tache1 : lTache) {
                    for (Utilisateur utili : tache1.getParticipants()) {
                        if(utili.getId() == utilisateur.getId()){
                            lTacheTmp.add(tache1);
                        }
                    }
                }
                FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("editeur"));
                return lTacheTmp;
                
            }
            FacesContext.getCurrentInstance().addMessage("connexionForm:msLogin", new FacesMessage("pas de taches"));
            //mettre erreur !!
            return null;
        }

    }

    public Tache findTache(long id) {
        return em.find(Tache.class, id);
    }
}
