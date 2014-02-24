package org.m2acsi.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.m2acsi.entities.Fichier;
import org.m2acsi.entities.Tache;

@Stateless
public class FichierEJB {
    
    @PersistenceContext(unitName = "com.mycompany_ProjetJEE_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public Fichier ajouterFichier(Fichier fichier, Tache tache){
        tache.addDocument(fichier);
        em.persist(fichier);
        em.merge(tache);
        return fichier;
    }
    
    public Fichier findFichier(Long pid){
        return em.find(Fichier.class, pid);
    }
}
