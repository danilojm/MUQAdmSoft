/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmosoft.muqadm.login;

import com.dmosoft.muqadm.bean.UsuarioBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DaniloJM
 */
@WebFilter("*.xhtml")
public class LoginFilter implements Filter {

    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            req = (HttpServletRequest) request;
            resp = (HttpServletResponse) response;
            UsuarioBean session = (UsuarioBean) req.getSession().getAttribute("usuarioBean");
            String url = req.getRequestURI();

            if (session == null || !session.isLogged) {
                if (url.contains("principal/principal.xhtml")
                        || url.contains("login/logout.xhtml")
                        || url.contains("login/loginsuccess.xhtml")) {
                    req.getSession().removeAttribute("usuarioBean");
                    resp.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml");
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                if (url.contains("login/logout.xhtml")) {
                    req.getSession().removeAttribute("usuarioBean");
                    resp.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml");
                } else if (url.contains("login/loginsuccess.xhtml")) {
                    resp.sendRedirect(req.getServletContext().getContextPath() + "/principal/principal.xhtml");
                } else {
                    chain.doFilter(request, response);
                }
            }
        } catch (IOException | ServletException iOException) {
            resp.sendRedirect(req.getServletContext().getContextPath() + "/index.xhtml");
        }
    }

    @Override
    public void destroy() {
    }

}
