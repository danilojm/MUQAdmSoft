package com.dmosoft.muqadm.bean;

import com.dmosoft.muqadm.dao.ProdutoDAO;
import com.dmosoft.muqadm.dao.TipoProdutoDAO;
import com.dmosoft.muqadm.model.Produto;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "produtoBean")
@SessionScoped
public class ProdutoBean implements Serializable {

    private Produto produtos = new Produto();
    private final ProdutoDAO pdao = new ProdutoDAO();
    private final TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();

    public String saveProdutos() {
        pdao.saveProduto(produtos);
        produtos = new Produto();
        return "/cadastro/telaDeProdutos";
    }

    public String logout() {
        return "/login/logout";
    }

    public String removeProduto(Produto p) {
        produtos = p;
        pdao.removeProduto(produtos);
        produtos = new Produto();
        return "/cadastro/telaDeProdutos";
    }

    public String saidaProduto(Produto p) {
        produtos = p;
        pdao.saidaProduto(produtos);
        produtos = new Produto();
        return "/cadastro/telaDeProdutos";
    }

    public String carregarProdutos(Produto p) {
        produtos = p;
        return "/cadastro/telaDeProdutos";
    }

    public String limpaTela() {
        produtos = new Produto();
        return "/cadastro/telaDeProdutos";
    }

    public List listarProdutos() {
        return pdao.listarProdutos();
    }

    public List listarTipoProdutos() {
        return tipoProdutoDAO.listarTipoProdutos();
    }

    public List listarNomeTipoProdutos() {
        return tipoProdutoDAO.listarNomeTipoProdutos();
    }

    public Produto getProdutos() {
        return produtos;
    }

    public void setProdutos(Produto produtos) {
        this.produtos = produtos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.produtos != null ? this.produtos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProdutoBean other = (ProdutoBean) obj;
        return !(this.produtos != other.produtos && (this.produtos == null || !this.produtos.equals(other.produtos)));
    }
}
