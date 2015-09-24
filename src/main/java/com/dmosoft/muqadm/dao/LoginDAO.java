/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.dao;

import com.dmosoft.muqadm.model.QUsuario;
import com.dmosoft.muqadm.model.Usuario;
import com.dmosoft.muqadm.util.HibernateUtil;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author DaniloJM
 */
public class LoginDAO implements Serializable {

    private static final long serialVersionUID = -4775287724030433855L;

    private final static Logger logger = Logger.getLogger(LoginDAO.class);
    private Session session;

    private void criarSessao() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
        } catch (HibernateException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public Usuario logar(Usuario u) {
        criarSessao();

        Usuario usuario = null;

        try {
            logger.info("Classe LoginDAO, fazendo login...");
            QUsuario qUsuario = QUsuario.usuario;
            JPQLQuery query = new HibernateQuery(session);
            usuario = query.from(qUsuario).where(
                    qUsuario.login.eq(u.getLogin()),
                    qUsuario.senha.eq(u.getSenha()))
                    .uniqueResult(qUsuario);
            if (usuario != null) {
                logger.info("Classe LoginDAO, logado com o usuario = " + usuario.getLogin());
            } else {
                logger.info("Classe LoginDAO, usuario não encontrado...");
            }
        } catch (HibernateException e) {
            logger.error("Classe LoginDAO, erro ao tentar logar... " + e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usuario;
    }
}
