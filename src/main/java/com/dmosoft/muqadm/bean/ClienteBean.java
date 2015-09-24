package com.dmosoft.muqadm.bean;

import com.dmosoft.muqadm.dao.ClientesDAO;
import com.dmosoft.muqadm.model.Cliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "clienteBean")
@SessionScoped
public class ClienteBean implements Serializable {
    
    private static final long serialVersionUID = 8558237494534882801L;

    private Cliente clientes = new Cliente();
    private ClientesDAO cdao = new ClientesDAO();
    private List listaClientes = new ArrayList();

    public String saveClientes() {
        cdao.saveCliente(clientes);
        clientes = new Cliente();
        return "/cadastro/telaDeClientes";
    }

    public String logout() {
        return "/login/logout";
    }

    public String removeClientes(Cliente c) {
        clientes = c;
        cdao.removeCliente(clientes);
        clientes = new Cliente();
        return "/cadastro/telaDeClientes";
    }

    public String carregarClientes(Cliente c) {
        clientes = new Cliente();
        clientes = c;
        return "/cadastro/telaDeClientes";
    }

    public String limpaTela() {
        clientes = new Cliente();
        return "/cadastro/telaDeClientes";
    }

    public List listarClientes() {
        listaClientes = cdao.listarClientes();
        return listaClientes;
    }

    public Cliente getClientes() {
        return clientes;
    }

    public void setClientes(Cliente clientes) {
        this.clientes = clientes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.clientes != null ? this.clientes.hashCode() : 0);
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
        final ClienteBean other = (ClienteBean) obj;
        if (this.clientes != other.clientes && (this.clientes == null || !this.clientes.equals(other.clientes))) {
            return false;
        }
        return true;
    }
}
