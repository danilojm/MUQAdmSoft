/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.dao;

import com.dmosoft.muqadm.mensagem.Mensagem;
import com.dmosoft.muqadm.model.Cliente;
import com.dmosoft.muqadm.model.QCliente;
import com.dmosoft.muqadm.util.HibernateUtil;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.jpa.hibernate.HibernateUpdateClause;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author DaniloJM
 */
public class ClientesDAO implements Serializable {

    private static final long serialVersionUID = -1450107230248863806L;

    private Session session;
    private Transaction transaction;
    private List<Cliente> lista;
    private Cliente cliente;

    public void saveCliente(Cliente cliente) {
        try {
            criarSessao();
            this.cliente = new Cliente();
            QCliente qCliente = QCliente.cliente;

            if (cliente.getId() != null && cliente.getId() != 0) {
                try {
                    new HibernateUpdateClause(session, qCliente)
                            .where(qCliente.id.eq(cliente.getId()))
                            .set(qCliente.nome, cliente.getNome())
                            .set(qCliente.endereco, cliente.getEndereco())
                            .set(qCliente.email, cliente.getEmail())
                            .set(qCliente.telefoneRes, cliente.getTelefoneRes())
                            .set(qCliente.telefoneCel, cliente.getTelefoneCel())
                            .set(qCliente.telefoneCel2, cliente.getTelefoneCel2())
                            .execute();

                    new Mensagem().addMessageInfo("Atualizado com Sucesso!");
                } catch (HibernateException ex) {
                    new Mensagem().addMessageError("Erro ao tentar Atualizar\n" + ex.getLocalizedMessage());
                }
            } else {
                try {
                    this.cliente = cliente;
                    session.save(this.cliente);
                    new Mensagem().addMessageInfo("Salvo com Sucesso!");
                } catch (HibernateException he) {
                    new Mensagem().addMessageError("Erro ao Salvar");
                }
            }
            transaction.commit();
        } catch (HibernateException e) {
            new Mensagem().addMessageError("Erro\n" + e.getLocalizedMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void removeCliente(Cliente clientes) {
        try {
            criarSessao();

            QCliente qCliente = QCliente.cliente;

            new HibernateDeleteClause(session, qCliente).where(qCliente.id.eq(clientes.getId())).execute();

            new Mensagem().addMessageInfo("Deletado com Sucesso!");

            transaction.commit();
        } catch (HibernateException e) {
            new Mensagem().addMessageError("Erro ao tentar Deletar\n" + e.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Cliente> listarClientes() {
        try {
            criarSessao();

            QCliente qCliente = QCliente.cliente;
            JPQLQuery query = new HibernateQuery(session);
            lista = query.from(qCliente).orderBy(qCliente.nome.asc()).list(new QCliente(qCliente));

//            Criteria criteria = session.createCriteria(Cliente.class);
//            lista = criteria.list();
        } catch (HibernateException he) {
            new Mensagem().addMessageError("Erro ao tentar Listar!");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lista;
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
