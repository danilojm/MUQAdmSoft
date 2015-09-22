/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.dao;

import com.dmosoft.muqadm.model.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danilo
 */
public class UsuarioDAOIT {

    @Test
    public void insertAndFindUsuario() {
        String nome = "Administrador";
        String usuario = "admin";
        String senha = "admin";

        Usuario u = new Usuario();
        u.setNome(nome);
        u.setLogin(usuario);
        u.setSenha(senha);

        UsuarioDAO dao = new UsuarioDAO();
        //Insere Usuario
        dao.saveUsuario(u);

        //Procura Usuario
        Usuario user = dao.findUsuario(u);

        assertEquals(user.getLogin(), u.getLogin());
    }
}
