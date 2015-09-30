/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.dao;

import com.dmosoft.muqadm.mensagem.Mensagem;
import com.dmosoft.muqadm.model.Produto;
import com.dmosoft.muqadm.model.QProduto;
import com.dmosoft.muqadm.util.HibernateUtil;
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
public class ProdutoDAO implements Serializable {

    private static final long serialVersionUID = -6823382201249032916L;

    private Session session;
    private Transaction transaction;

    public void saveProduto(Produto produto) {
        try {

            criarSessao();

            QProduto qProduto = QProduto.produto;

            if (produto.getId() != null && produto.getId() != 0) {
                try {
                    new HibernateUpdateClause(session, qProduto)
                            .where(qProduto.id.eq(produto.getId()))
                            .set(qProduto.quantidade, produto.getQuantidade())
                            .set(qProduto.codTipoProduto, produto.getCodTipoProduto())
                            .execute();
                    transaction.commit();
                    new Mensagem().addMessageInfo("Atualizado com Sucesso!");
                } catch (HibernateException ex) {
                    new Mensagem().addMessageInfo("Erro ao Atualizar");
                }
            } else {
                try {
                    session.save(produto);
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

    public void removeProduto(Produto produto) {
        try {

            criarSessao();

            QProduto qProduto = QProduto.produto;
            new HibernateDeleteClause(session, qProduto).where(qProduto.id.eq(produto.getId())).execute();
            transaction.commit();
            new Mensagem().addMessageInfo("Deletado com Sucesso!");
        } catch (HibernateException e) {
            new Mensagem().addMessageInfo("Erro ao Deletar!");
        } finally {
            session.close();
        }
    }

    public List<Produto> listarProdutos() {
        List<Produto> result = new ArrayList<>();
        try {

            criarSessao();

            QProduto qProduto = QProduto.produto;
            JPQLQuery query = new HibernateQuery(session);
            result = query.from(qProduto).list(new QProduto(qProduto));

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

    public Produto copiarItem(Produto p) {
        Produto prod = new Produto();
        if (p.getCodTipoProduto()!= null) {
            prod.setCodTipoProduto(p.getCodTipoProduto());
        }

        if (p.getQuantidade() != null) {
            prod.setQuantidade(p.getQuantidade());
        }
        return prod;
    }
}
