/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.dao;

import com.dmosoft.muqadm.mensagem.Mensagem;
import com.dmosoft.muqadm.model.QTipoProduto;
import com.dmosoft.muqadm.model.TipoProduto;
import com.dmosoft.muqadm.util.HibernateUtil;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.jpa.hibernate.HibernateUpdateClause;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author DaniloJM
 */
public class TipoProdutoDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Session session;
    private Transaction transaction;
    private TipoProduto tipoProduto;

    public void saveTipoProduto(TipoProduto tProduto) {
        try {

            criarSessao();

            this.tipoProduto = tProduto;

            QTipoProduto qtp = QTipoProduto.tipoProduto;

            if (this.tipoProduto.getId() != null && this.tipoProduto.getId() != 0) {
                try {

                    new HibernateUpdateClause(session, qtp)
                            .where(qtp.id.eq(this.tipoProduto.getId()))
                            .set(qtp, this.tipoProduto)
                            .execute();
                    transaction.commit();
                    new Mensagem().addMessageInfo("Atualizado com Sucesso!");
                } catch (HibernateException ex) {
                    new Mensagem().addMessageInfo("Erro ao Atualizar");
                }
            } else {
                try {
                    session.save(this.tipoProduto);
                    transaction.commit();
                    new Mensagem().addMessageInfo("Salvo com Sucesso!");
                } catch (HibernateException ex) {
                    new Mensagem().addMessageInfo("Erro ao Salvar");
                }
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void removeTipoProduto(TipoProduto tipoProduto) {
        try {

            criarSessao();

            QTipoProduto qtp = QTipoProduto.tipoProduto;

            new HibernateDeleteClause(session, qtp).where(qtp.id.eq(tipoProduto.getId())).execute();
            transaction.commit();
            new Mensagem().addMessageInfo("Deletado com Sucesso!");
        } catch (HibernateException e) {
            new Mensagem().addMessageInfo("Erro ao Deletar!");
        } finally {
            session.close();
        }
    }

    public List<Tuple> listarNomeTipoProdutos() {
        List<Tuple> result = new ArrayList<>();
        try {

            criarSessao();

            QTipoProduto qtp = QTipoProduto.tipoProduto;

            JPQLQuery query = new HibernateQuery(session);
            result = query.from(qtp).list(qtp.tipo, qtp.cor);            

        } catch (HibernateException he) {
            new Mensagem().addMessageError("Erro ao tentar listar!");
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

    public List<TipoProduto> listarTipoProdutos() {
        List<TipoProduto> result = new ArrayList<>();
        try {

            criarSessao();

            QTipoProduto qtp = QTipoProduto.tipoProduto;

            JPQLQuery query = new HibernateQuery(session);
            result = query.from(qtp).list(new QTipoProduto(qtp));

        } catch (HibernateException he) {
            new Mensagem().addMessageError("Erro ao tentar listar!");
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

    private void criarSessao() {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

}
