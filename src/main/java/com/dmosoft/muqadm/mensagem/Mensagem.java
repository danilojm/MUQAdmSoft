/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.mensagem;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author DaniloJM
 */
public class Mensagem implements Serializable {

    public void addMessageInfo(String msgn) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msgn, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void addMessageError(String msgn) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgn, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
