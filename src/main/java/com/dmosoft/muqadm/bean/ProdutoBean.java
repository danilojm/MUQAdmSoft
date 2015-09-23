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

    private Produto produto = new Produto();
    private final ProdutoDAO pdao = new ProdutoDAO();
    private final TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();

    public String saveProdutos() {
        pdao.saveProduto(produto);
        produto = new Produto();
        return "/cadastro/telaDeProdutos";
    }

    public String logout() {
        return "/login/logout";
    }

    public String removeProduto(Produto p) {
        produto = p;
        pdao.removeProduto(produto);
        produto = new Produto();
        return "/cadastro/telaDeProdutos";
    }

    public String saidaProduto(Produto p) {
        produto = p;
        pdao.saidaProduto(produto);
        produto = new Produto();
        return "/cadastro/telaDeProdutos";
    }

    public String carregarProdutos(Produto p) {
        produto = p;
        return "/cadastro/telaDeProdutos";
    }

    public String limpaTela() {
        produto = new Produto();
        return "/cadastro/telaDeProdutos";
    }

    public List listarProdutos() {
        return pdao.listarProdutos();
    }

    public List listarTipoProdutos() {
        return tipoProdutoDAO.listarTipoProdutos();
    }

    public List listarNomeTipoProdutos() {
        return tipoProdutoDAO.getNomeTipoProduto();
    }

    public Produto getProdutos() {
        return produto;
    }

    public void setProdutos(Produto produtos) {
        this.produto = produtos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.produto != null ? this.produto.hashCode() : 0);
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
        return !(this.produto != other.produto && (this.produto == null || !this.produto.equals(other.produto)));
    }
}
