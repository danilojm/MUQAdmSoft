package com.dmosoft.muqadm.bean;

import com.dmosoft.muqadm.dao.ProdutoDAO;
import com.dmosoft.muqadm.dao.TipoProdutoDAO;
import com.dmosoft.muqadm.model.ListaTipoProduto;
import com.dmosoft.muqadm.model.Produto;
import com.dmosoft.muqadm.model.TipoProduto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "produtoBean")
@SessionScoped
public class ProdutoBean implements Serializable {

    private static final long serialVersionUID = -7657435227852099350L;
    private static final Logger LOG = Logger.getLogger(ProdutoBean.class.getName());

    private Produto produto = new Produto();
    private TipoProduto tipoProduto = new TipoProduto();

    private List<Produto> produtos;
    private List<TipoProduto> tipoProdutos;
    private List<String> listaProdutos;
    private List<String> listaTipoProdutos;
    private List<String> listaCorProdutos;
    private List<String> listaTamanhoProdutos;

    private final ProdutoDAO pdao = new ProdutoDAO();
    private final TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO();

    @PostConstruct
    public void init() {
        montaListaProdutos();
    }

    public String saveProdutos() {        
        pdao.saveProduto(produto);
        produto = new Produto();
        return "/telas/telaDeProdutos";
    }

    public String removeProduto(Produto p) {
        produto = new Produto();
        produto = p;
        pdao.removeProduto(produto);
        produto = new Produto();
        return "/telas/telaDeProdutos";
    }

    public String carregarProdutos(Produto p) {
        produto = new Produto();
        produto = p;
        tipoProduto = tipoProdutoDAO.findTipoProdutoPorCodProduto(produto.getCodTipoProduto());
        aoMudarProduto();
        aoMudarTipoProduto();
        aoMudarTamanhoProduto();
        return "/telas/telaDeProdutos";
    }

    public String copiarProdutos(ListaTipoProduto p) {
        produto = new Produto();
        produto = pdao.copiarItem((Produto) p);
        tipoProduto = tipoProdutoDAO.findTipoProdutoPorCodProduto(produto.getCodTipoProduto());
        aoMudarProduto();
        aoMudarTipoProduto();
        aoMudarTamanhoProduto();
        LOG.info("Copiar produtos...");
        return "/telas/telaDeProdutos";
    }

    public String limpaTela() {
        produto = new Produto();
        tipoProduto = new TipoProduto();
        return "/telas/telaDeProdutos";
    }

    public List listarProdutos() {
        produtos = pdao.listarProdutos();
        List lista = listaTudo();
        return lista;
    }

    public List listaTudo() {
        List lista = new ArrayList();
        for (Produto prod : produtos) {
            for (TipoProduto tProd : tipoProdutos) {
                if (prod.getCodTipoProduto().equals(tProd.getCodTipoProduto())) {
                    ListaTipoProduto ltp = new ListaTipoProduto();
                    ltp.setId(prod.getId());
                    ltp.setCodTipoProduto(prod.getCodTipoProduto());
                    ltp.setNomeProduto(tProd.getNomeProduto());
                    ltp.setTipoDeProduto(tProd.getTipoDeProduto());
                    ltp.setTamanho(tProd.getTamanho());
                    ltp.setCor(tProd.getCor());
                    ltp.setQuantidade(prod.getQuantidade());
                    ltp.setDescricao(tProd.getDescricao());
                    lista.add(ltp);
                }
            }
        }
        return lista;
    }

    public List listarTipoProdutos() {
        tipoProdutos = tipoProdutoDAO.listarTipoProdutos();
        List lista = listarProdutos();
        return lista;
    }

    public void montaListaProdutos() {

        listarTipoProdutos();

        listaProdutos = new ArrayList<>();

        for (TipoProduto prod : tipoProdutos) {
            if (!listaProdutos.contains(prod.getNomeProduto())) {
                listaProdutos.add(prod.getNomeProduto());
            }
        }
    }

    public void completeText(String codProduto) {
        tipoProduto = tipoProdutoDAO.findTipoProdutoPorCodProduto(Integer.parseInt(codProduto));
        aoMudarProduto();
        aoMudarTipoProduto();
        aoMudarTamanhoProduto();
        aoMudarCorProduto();
    }

    public void aoMudarProduto() {
        if (tipoProduto.getNomeProduto() != null && !tipoProduto.getNomeProduto().equals("")) {
            String tipoProd = tipoProduto.getNomeProduto();

            listaTipoProdutos = new ArrayList<>();
            listaCorProdutos = new ArrayList<>();
            listaTamanhoProdutos = new ArrayList<>();

            for (TipoProduto prod : tipoProdutos) {
                if (prod.getNomeProduto().equals(tipoProd)) {
                    if (!listaTipoProdutos.contains(prod.getTipoDeProduto())) {
                        listaTipoProdutos.add(prod.getTipoDeProduto());
                    }
                }
            }
        }
    }

    public void aoMudarTipoProduto() {
        if (tipoProduto.getTipoDeProduto() != null && !tipoProduto.getTipoDeProduto().equals("")) {
            listaTamanhoProdutos = new ArrayList<>();
            for (TipoProduto lista : tipoProdutos) {
                if (lista.getNomeProduto().equals(tipoProduto.getNomeProduto())
                        && lista.getTipoDeProduto().equals(tipoProduto.getTipoDeProduto())) {
                    if (!listaTamanhoProdutos.contains(lista.getTamanho())) {
                        listaTamanhoProdutos.add(lista.getTamanho());
                    }
                }
            }
        }
    }

    public void aoMudarTamanhoProduto() {
        if (tipoProduto.getTamanho() != null && !tipoProduto.getTamanho().equals("")) {

            listaCorProdutos = new ArrayList<>();

            for (TipoProduto lista : tipoProdutos) {
                if (lista.getNomeProduto().equals(tipoProduto.getNomeProduto())
                        && lista.getTipoDeProduto().equals(tipoProduto.getTipoDeProduto())
                        && lista.getTamanho().equals(tipoProduto.getTamanho())) {
                    if (!listaCorProdutos.contains(lista.getCor())) {
                        listaCorProdutos.add(lista.getCor());
                    }
                }
            }
        }
    }

    public void aoMudarCorProduto() {
        TipoProduto tp = new TipoProduto();
        tp.setNomeProduto(tipoProduto.getNomeProduto());
        tp.setTipoDeProduto(tipoProduto.getTipoDeProduto());
        tp.setTamanho(tipoProduto.getTamanho());
        tp.setCor(tipoProduto.getCor());

        TipoProduto retTP = tipoProdutoDAO.findTipoProduto(tp);
        produto.setCodTipoProduto(retTP.getCodTipoProduto());
    }

    public List<String> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<String> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<String> getListaTipoProdutos() {
        return listaTipoProdutos;
    }

    public void setListaTipoProdutos(List<String> listaTipoProdutos) {
        this.listaTipoProdutos = listaTipoProdutos;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<TipoProduto> getTipoProdutos() {
        return tipoProdutos;
    }

    public void setTipoProdutos(List<TipoProduto> tipoProdutos) {
        this.tipoProdutos = tipoProdutos;
    }

    public List<String> getListaCorProdutos() {
        return listaCorProdutos;
    }

    public void setListaCorProdutos(List<String> listaCorProdutos) {
        this.listaCorProdutos = listaCorProdutos;
    }

    public List<String> getListaTamanhoProdutos() {
        return listaTamanhoProdutos;
    }

    public void setListaTamanhoProdutos(List<String> listaTamanhoProdutos) {
        this.listaTamanhoProdutos = listaTamanhoProdutos;
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
