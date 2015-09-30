/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.dao;

import static com.dmosoft.muqadm.dao.TipoProdutoDAO.logger;
import com.dmosoft.muqadm.mensagem.Mensagem;
import com.dmosoft.muqadm.model.Produto;
import com.dmosoft.muqadm.model.QProduto;
import com.dmosoft.muqadm.model.QVendasProduto;
import com.dmosoft.muqadm.util.HibernateUtil;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fernando
 */
public class VendasDAO implements Serializable {

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

//    public VendaProduto findProduto(Produto p)
    public void vendaProduto(Produto p) {

        QVendasProduto qVendasProduto = QVendasProduto.vendasProduto;

        try {
//            new HibernateUpdateClause(session, qVendasProduto)
//                    .where(qVendasProduto.id.eq(p.getId()))
//                    .set(null, null)
        } catch (Exception e) {
        }

    }

    public Produto listarProdutos(Produto p) {

        Produto produto = null;

        try {

            criarSessao();

            QProduto qProduto = QProduto.produto;
            JPQLQuery query = new HibernateQuery(session);
            produto = query.from(qProduto).where(qProduto.codTipoProduto.eq(p.getCodTipoProduto())).uniqueResult(qProduto);

        } catch (HibernateException he) {
            new Mensagem().addMessageError("Erro ao tentar listar!");
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return produto;
    }
}
