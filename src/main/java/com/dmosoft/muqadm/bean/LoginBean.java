package com.dmosoft.muqadm.bean;

import com.dmosoft.muqadm.dao.LoginDAO;
import com.dmosoft.muqadm.mensagem.Mensagem;
import com.dmosoft.muqadm.model.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import org.hibernate.HibernateException;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    
    private static final long serialVersionUID = 4379852798555225549L;
    
    public Usuario usuario = new Usuario();
    private final LoginDAO ldao = new LoginDAO();
    public boolean isLogged = false;
    public String nomeLogado = "";

    public String control(Usuario u) {
        try {
            usuario = this.ldao.logar(u);
            if (usuario != null && usuario.getId() != null && usuario.getId() != 0) {
                isLogged = true;
                nomeLogado = usuario.getNome();
                usuario = new Usuario();
                return "/principal/principal.xhtml?faces-redirect=true";
            } else {
                isLogged = false;
                new Mensagem().addMessageInfo("Usuário e/ou Senha Inválidos!");
                return "";
            }
        } catch (HibernateException e) {

        }
        return "";
    }

}
