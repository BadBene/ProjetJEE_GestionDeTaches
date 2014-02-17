/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m2acsi.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.m2acsi.entities.Message;

/**
 *
 * @author LoLo
 */
@Stateless
public class MessageEJB {
    
    @PersistenceContext(unitName = "com.mycompany_ProjetJEE_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public Message creerMessage(Message message){
        em.persist(message);
        return message;
    }
}
