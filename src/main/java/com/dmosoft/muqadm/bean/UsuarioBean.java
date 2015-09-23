package com.dmosoft.muqadm.bean;

import com.dmosoft.muqadm.dao.LoginDAO;
import com.dmosoft.muqadm.dao.UsuarioDAO;
import com.dmosoft.muqadm.mensagem.Mensagem;
import com.dmosoft.muqadm.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.HibernateException;

@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    private Usuario usuario = new Usuario();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    public boolean isLogged = false;
    public String nomeLogado = "";
    private LoginDAO ldao = new LoginDAO();
    private List listaUsuarios = new ArrayList();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomeLogado() {
        return nomeLogado;
    }

    public void setNomeLogado(String nomeLogado) {
        this.nomeLogado = nomeLogado;
    }

    public String limpaTela() {
        usuario = new Usuario();
        return "/cadastro/telaDeUsuarios";
    }

    public String control() {
        try {
            usuario = this.ldao.logar(usuario);
            if (usuario != null && usuario.getId() != null && usuario.getId() != 0) {
                isLogged = true;
                nomeLogado = usuario.getNome();
                usuario = new Usuario();
                return "/login/loginsuccess.xhtml?faces-redirect=true";
            } else {
                isLogged = false;
                usuario = new Usuario();
                new Mensagem().addMessageError("Usuário e/ou Senha Inválidos!");
                return "";
            }
        } catch (HibernateException e) {

        }
        return "";
    }

    public String addUsuario() {
        usuarioDAO.saveUsuario(usuario);
        return "/cadastro/telaDeUsuarios.xhtml?faces-redirect=true";
    }

    public List listarUsuarios() {
        listaUsuarios = usuarioDAO.listarUsuarios();
        return listaUsuarios;
    }

    public String removeClientes(Usuario u) {
        usuario = u;
        usuarioDAO.removeUsuario(usuario);
        usuario = new Usuario();
        return "/cadastro/telaDeUsuarios";
    }

    public String carregarClientes(Usuario u) {
        usuario = u;
        return "/cadastro/telaDeUsuarios";
    }
}
