/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m2acsi.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.m2acsi.entities.Role;
import org.m2acsi.entities.Utilisateur;

/**
 *
 * @author LoLo
 */
@Stateless
public class UtilisateurEJB {
    
    @PersistenceContext(unitName="com.mycompany_ProjetJEE_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public Utilisateur creerUtilisateur(Utilisateur utilisateur){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Role> q = cb.createQuery(Role.class);
        Root<Role> role = q.from(Role.class);
        q.select(role);        
        
        q.where(cb.equal(role.<String>get("nom"), "Simple"));
        
        utilisateur.setRole(em.createQuery(q).getResultList().get(0));
        em.persist(utilisateur);
        return utilisateur;
    }
    
    public Utilisateur modifierUtilisateur(Utilisateur utilisateur){
        em.merge(utilisateur);
        return utilisateur;
    }
}
