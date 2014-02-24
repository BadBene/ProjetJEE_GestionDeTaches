package org.m2acsi.control;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.Converter;

@ManagedBean
@ViewScoped
public class GenericSecuredConverter implements Converter, Serializable {
 
    private Map <UUID, Object> temporaryStore = new HashMap <UUID, Object> ();
 
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return temporaryStore.get(UUID.fromString(value));
    }
 
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        UUID id = UUID.randomUUID();
        temporaryStore.put(id, value);
        return id.toString();
    }

    @Override
    public boolean autoApply() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
