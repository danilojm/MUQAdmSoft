/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.dao;

import com.dmosoft.muqadm.mensagem.Mensagem;
import com.dmosoft.muqadm.model.QUsuario;
import com.dmosoft.muqadm.model.Usuario;
import com.dmosoft.muqadm.util.HibernateUtil;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.jpa.hibernate.HibernateUpdateClause;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author danilo
 */
public class UsuarioDAO implements Serializable {

    private static final long serialVersionUID = -9120739963561597292L;

    final static Logger logger = Logger.getLogger(UsuarioDAO.class);

    private Session session;
    private Transaction transaction;

    private void criarSessao() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            logger.info("Classe UsuarioDAO, Sessão iniciada...");
        } catch (HibernateException e) {
            logger.error("Classe UsuarioDAO, Erro ao criar a sessão ");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void saveUsuario(Usuario u) {
        criarSessao();

        QUsuario qUsuario = QUsuario.usuario;

        try {
            if (u.getId() != null && u.getId() != 0) {
                new HibernateUpdateClause(session, qUsuario)
                        .where(qUsuario.id.eq(u.getId()))
                        .set(qUsuario, u)
                        .execute();
                transaction.commit();
                logger.info("Classe UsuarioDAO, Update Usuario " + u.getNome());
            } else {
                session.save(u);
                transaction.commit();
                logger.info("Classe UsuarioDAO, Save Usuario " + u.getNome());
            }
        } catch (HibernateException he) {
            logger.error("Classe UsuarioDAO, Erro ao salvar Usuario...\n" + he.getLocalizedMessage());
            new Mensagem().addMessageError(he.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Usuario findUsuario(Usuario u) {
        criarSessao();
        Usuario usuario = null;
        try {
            if (u != null) {
                logger.info("Classe UsuarioDAO, findUsuario...");
                QUsuario qUsuario = QUsuario.usuario;
                JPQLQuery query = new HibernateQuery(session);
                usuario = query.from(qUsuario).where(qUsuario.id.eq(u.getId())).uniqueResult(qUsuario);

                if (usuario != null) {
                    logger.info("Classe UsuarioDAO, Usuario encontrado = " + usuario.getNome());
                }
            }
        } catch (HibernateException e) {
            logger.error("Classe UsuarioDAO, Erro ao tentar encontrar Usuario...");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuario;
    }

    public void removeUsuario(Usuario u) {
        criarSessao();
        QUsuario qUsuario = QUsuario.usuario;
        try {
            logger.info("Classe UsuarioDAO, removendo usuario " + u.getNome());
            new HibernateDeleteClause(session, qUsuario)
                    .where(qUsuario.id.eq(u.getId()))
                    .execute();
            transaction.commit();
            new Mensagem().addMessageInfo("Classe UsuarioDAO, Usuario " + u.getNome() + " removido");
            logger.info("Classe UsuarioDAO, Usuario " + u.getNome() + " removido");
        } catch (HibernateException ex) {
            new Mensagem().addMessageError("Classe UsuarioDAO, erro ao remover usuário!");
            logger.error("Classe UsuarioDAO, Erro ao tentar remover\n" + ex.getLocalizedMessage());
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = null;
        try {
            criarSessao();

            QUsuario qUsuario = QUsuario.usuario;
            JPQLQuery query = new HibernateQuery(session);
            lista = query.from(qUsuario).list(new QUsuario(qUsuario));

        } catch (HibernateException he) {
            he.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lista;
    }

}
