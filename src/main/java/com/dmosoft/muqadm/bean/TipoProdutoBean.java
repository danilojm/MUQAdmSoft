package com.dmosoft.muqadm.bean;

import com.dmosoft.muqadm.dao.TipoProdutoDAO;
import com.dmosoft.muqadm.model.Produto;
import com.dmosoft.muqadm.model.TipoProduto;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "tipoProdutoBean")
@SessionScoped
public class TipoProdutoBean implements Serializable {

    private static final long serialVersionUID = -4681766834562382335L;
    private static final Logger LOG = Logger.getLogger(TipoProdutoBean.class.getName());

    private TipoProduto tipoProduto = new TipoProduto();
    private final TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();

    public String saveTipoProdutos() {
        tipoProdutoDAO.saveTipoProduto(tipoProduto);
        tipoProduto = new TipoProduto();
        return "/telas/telaTipoDeProduto";
    }

    public String removeTipoProduto(TipoProduto tProduto) {
        tipoProdutoDAO.removeTipoProduto(tProduto);
        tipoProduto = new TipoProduto();
        return "/telas/telaTipoDeProduto";
    }

    public String carregarTipoProdutos(TipoProduto tProduto) {
        tipoProduto = tProduto;
        return "/telas/telaTipoDeProduto";
    }

    public String copiarProdutos(TipoProduto tp) {
        tipoProduto = new TipoProduto();
        tipoProduto = tipoProdutoDAO.copiarItem(tp);
        return "/telas/telaTipoDeProduto";
    }

    public String limpaTela() {
        tipoProduto = new TipoProduto();
        return "/telas/telaTipoDeProduto";
    }

    public List listarTipoProdutos() {
        return tipoProdutoDAO.listarTipoProdutos();
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
}
