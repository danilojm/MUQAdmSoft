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
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.jpa.hibernate.HibernateUpdateClause;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author DaniloJM
 */
public class TipoProdutoDAO implements Serializable {

    private static final long serialVersionUID = -68691896807734587L;

    final static Logger logger = Logger.getLogger(TipoProdutoDAO.class);

    private Session session;
    private Transaction transaction;

    public List<TipoProduto> produtoList = new ArrayList<>();
    public List<String> tipoProdutoList = new ArrayList<>();
    public List<String> corProdutoList = new ArrayList<>();

    public Map<String, Map<String, String>> prod;

    //private TipoProduto tipoProduto;
    public void saveTipoProduto(TipoProduto tProduto) {
        try {
            criarSessao();

            QTipoProduto qtp = QTipoProduto.tipoProduto;

            if (tProduto.getId() != null && tProduto.getId() != 0) {
                try {
                    logger.info("Classe TipoProdutoDAO, atualizando tipo produto...");
                    new HibernateUpdateClause(session, qtp)
                            .where(qtp.id.eq(tProduto.getId()))
                            .set(qtp.produto, tProduto.getProduto())
                            .set(qtp.tipoDeProduto, tProduto.getTipoDeProduto())
                            .set(qtp.tamanho, tProduto.getTamanho())
                            .set(qtp.cor, tProduto.getCor())
                            .set(qtp.descricao, tProduto.getDescricao())
                            .execute();
                    transaction.commit();
                    new Mensagem().addMessageInfo("Atualizado com Sucesso!");
                } catch (HibernateException ex) {
                    new Mensagem().addMessageInfo("Erro ao Atualizar");
                }
            } else {
                try {
                    logger.info("Classe TipoProdutoDAO, salvando tipo produto...");
                    session.save(tProduto);
                    transaction.commit();
                    new Mensagem().addMessageInfo("Salvo com Sucesso!");
                } catch (HibernateException ex) {
                    logger.error("Classe TipoProdutoDAO, erro ao salvar/atualizar tipo produto...\n" + ex.getLocalizedMessage());
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

            if (tipoProduto != null && tipoProduto.getId() != null) {

                QTipoProduto qtp = QTipoProduto.tipoProduto;

                logger.info("Classe TipoProdutoDAO, removendo Tipo Produto...");

                new HibernateDeleteClause(session, qtp).where(qtp.id.eq(tipoProduto.getId())).execute();

                transaction.commit();

                new Mensagem().addMessageInfo("Deletado com Sucesso!");
            }
        } catch (HibernateException e) {
            logger.error("Classe TipoProdutoDAO, erro ao remover Tipo Produto...\n" + e.getLocalizedMessage());
            new Mensagem().addMessageInfo("Erro ao Deletar!");
        } finally {
            session.close();
        }
    }

    public List<TipoProduto> listarTipoProdutos() {
        List<TipoProduto> result = new ArrayList<>();
        try {

            criarSessao();
            QTipoProduto qtp = QTipoProduto.tipoProduto;
            logger.info("Classe TipoProdutoDAO, iniciando listagem de tipo de produtos...");
            JPQLQuery query = new HibernateQuery(session);

            prod = new HashMap<>();

            result = query.from(qtp).orderBy(qtp.produto.asc()).list(new QTipoProduto(qtp));

            for (TipoProduto next : result) {
                produtoList.add(next);
            }
        } catch (HibernateException he) {
            logger.info("Classe TipoProdutoDAO, erro ao listar Tipo de Produtos...\n" + he.getLocalizedMessage());
            new Mensagem().addMessageError("Erro ao tentar listar!");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public List<TipoProduto> getListaProduto() {
        if (produtoList == null || produtoList.isEmpty()) {
            listarTipoProdutos();
        }
        return produtoList;
    }

    private void criarSessao() {
        try {
            logger.info("Classe TipoProdutoDAO, criando sessão...");
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
        } catch (HibernateException e) {
            logger.error("Classe TipoProdutoDAO, erro ao criar sessão...");
            e.printStackTrace();
        }
    }

    public TipoProduto copiarItem(TipoProduto tp) {
        TipoProduto tipoProduto = new TipoProduto();
        if (tp.getProduto() != null) {
            tipoProduto.setProduto(tp.getProduto());
        }
        if (tp.getTipoDeProduto() != null) {
            tipoProduto.setTipoDeProduto(tp.getTipoDeProduto());
        }

        if (tp.getTamanho() != null) {
            tipoProduto.setTamanho(tp.getTamanho());
        }

        if (tp.getCor() != null) {
            tipoProduto.setCor(tp.getCor());
        }

        if (tp.getDescricao() != null) {
            tipoProduto.setDescricao(tp.getDescricao());
        }
        return tipoProduto;
    }

}
