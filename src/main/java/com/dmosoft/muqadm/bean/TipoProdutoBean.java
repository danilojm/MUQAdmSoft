package com.dmosoft.muqadm.bean;

import com.dmosoft.muqadm.dao.TipoProdutoDAO;
import com.dmosoft.muqadm.model.TipoProduto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "tipoProdutoBean")
@SessionScoped
public class TipoProdutoBean implements Serializable {

    private TipoProduto tipoProduto = new TipoProduto();
    private final TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();
    private List listaTipoProduto = new ArrayList();

    public String saveTipoProdutos() {
        tipoProdutoDAO.saveTipoProduto(tipoProduto);
        tipoProduto = new TipoProduto();
        return "/cadastro/telaTipoDeProduto";
    }

    public String removeTipoProduto(TipoProduto tProduto) {
        this.tipoProduto = tProduto;
        tipoProdutoDAO.removeTipoProduto(tipoProduto);
        tipoProduto = new TipoProduto();
        return "/cadastro/telaTipoDeProduto";
    }

    public String carregarTipoProdutos(TipoProduto tProduto) {
        this.tipoProduto = tProduto;
        return "/cadastro/telaTipoDeProduto";
    }

    public String limpaTela() {
        tipoProduto = new TipoProduto();
        return "/cadastro/telaTipoDeProduto";
    }

    public List listarTipoProdutos() {
        listaTipoProduto = tipoProdutoDAO.listarTipoProdutos();
        return listaTipoProduto;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
}
