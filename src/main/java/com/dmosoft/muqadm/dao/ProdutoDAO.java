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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author DaniloJM
 */
public class ProdutoDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Session session;
    private Transaction transaction;
    //private List<Produto> lista;
    private Produto produto;

    public void saveProduto(Produto produto) {
        try {

            criarSessao();

            this.produto = produto;
            
            String[] prod = produto.getNomeProduto().replace("[", "").replace("]", "").split(",");

            QProduto qProduto = QProduto.produto;

            if (this.produto.getId() != null && this.produto.getId() != 0) {
                try {
                    new HibernateUpdateClause(session, qProduto)
                            .where(qProduto.id.eq(this.produto.getId()))
                            .set(qProduto.quantidade, this.produto.getQuantidade())
                            .set(qProduto.nomeProduto, prod[0])
                            .set(qProduto.corProduto, prod[1])
                            .set(qProduto.tipoProduto, this.produto.getTipoProduto())
                            .execute();
                    transaction.commit();
                    new Mensagem().addMessageInfo("Atualizado com Sucesso!");
                } catch (HibernateException ex) {
                    new Mensagem().addMessageInfo("Erro ao Atualizar");
                }
            } else {
                try {
                    session.save(this.produto);
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

    private Integer getQuantidadeEstoque(Produto produto) {
        Integer quant = null;
        try {
            String hql = "select quantidadeEmEstoque from " + Produto.class.getName() + " where id = '" + produto.getId() + "'";
            Query query = session.createQuery(hql);
//            Usuario user = (Usuario) session.get(Usuario.class, usuario);
            if (!query.list().isEmpty()) {
                quant = (Integer) query.list().get(0);
            }
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return quant;
    }

    public void saidaProduto(Produto produto) {
        try {
            criarSessao();
            this.produto = new Produto();
            this.produto = produto;

            int quantEstoque = getQuantidadeEstoque(produto);
            int quantPersonalizacao = produto.getQuantidadePersonalizacao();
            this.produto.setQuantidade(quantEstoque - quantPersonalizacao);
            session.update(this.produto);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
