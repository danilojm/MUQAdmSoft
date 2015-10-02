/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.bean;

import com.dmosoft.muqadm.dao.VendasDAO;
import com.dmosoft.muqadm.model.Produto;
import com.dmosoft.muqadm.model.VendasProduto;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author fernando
 */
@ManagedBean(name = "vendasProdutoBean")
@SessionScoped
public class VendasProdutoBean implements Serializable {

    private static final long serialVersionUID = -3473392346556528619L;

    VendasProduto vProduto = new VendasProduto();
    VendasDAO vdao = new VendasDAO();

    public void vender(Produto p) {
        vdao.vendaProduto(p);
    }

    public String limpaTela() {
        vProduto = new VendasProduto();
        return "/telas/telaDeVendaProdutos";
    }

}
