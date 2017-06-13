/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import py.una.pol.par.modelos.UsuarioManager;
import py.una.pol.par.entidades.Usuario;

/*
 * @author Skynet
 */

public class UsuarioABMServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        String accion = request.getParameter("accion");
        request.setAttribute("accion", accion);
        HttpSession sesion = request.getSession();
        Usuario usuario = new Usuario();
        UsuarioManager um = new UsuarioManager();
        usuario = null;
        int id = 0;
        String login_name = null;
        String nombre = null;
        String apellido = null;
        String contrasenha = null;
        int tipo_usuario = 2;

        if (accion == null) {
            //modo grilla...se muestran todos los registros
            ArrayList<Usuario> usuarios = um.getAll();
            request.setAttribute("usuarios", usuarios);

            RequestDispatcher rd = request.getRequestDispatcher("/Usuarios.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Eliminar".equals(accion)) {
            int idCli = Integer.valueOf(request.getParameter("vid"));
            usuario = new Usuario();
            usuario.setId_usuario(idCli);

            um.eliminar(usuario);

            ArrayList<Usuario> usuarios = um.getAll();
            request.setAttribute("usuarios", usuarios);
            RequestDispatcher rd = request.getRequestDispatcher("/Usuarios.jsp");

            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Grabar".equals(accion)) {
            //id = Integer.valueOf(request.getParameter("id_cli"));
            login_name = request.getParameter("login_name");
            nombre = request.getParameter("nombre");
            apellido = request.getParameter("apellido");
            contrasenha = request.getParameter("contrasenha");
            usuario = new Usuario();

            //debo ver si en la sesion esta el admin o un desconocido y mandarlo al index
            if (request.getSession().getAttribute("usuario") != null) { //Si hay un usuario logueado.

                usuario = (Usuario) sesion.getAttribute("usuario");

                //si esta el admin debo mandarlo al listado porque esta agregando clientes
                if (um.es_usuario(usuario.getlogin_name(), usuario.getContrasenha()) == 1) { //El administrador le registra a un cliente.
                    usuario.setApellido(apellido);
                    usuario.setlogin_name(login_name);
                    usuario.setNombre(nombre);
                    usuario.setContrasenha(contrasenha);

                    um.insertar(usuario);

                    ArrayList<Usuario> clientes = um.getAll();
                    request.setAttribute("clientes", clientes);

                    RequestDispatcher rd = request.getRequestDispatcher("/Clientes.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                    //si no, es un desconocido debo cambiar el usuario cargado en la sesion
                } else if (um.es_usuario(usuario.getlogin_name(), usuario.getContrasenha()) == 2) { //EL usuario desconocido se registra solo.
                    //sesion.invalidate();
                    usuario.setApellido(apellido);
                    usuario.setlogin_name(login_name);
                    usuario.setId_usuario(id);
                    usuario.setNombre(nombre);
                    usuario.setContrasenha(contrasenha);
                    Usuario usuario_nuevo = new Usuario();
                    usuario_nuevo.setlogin_name(login_name);
                    usuario_nuevo.setContrasenha(contrasenha);
                    um.insertar(usuario);

                    sesion.setAttribute("usuario", usuario_nuevo);

                    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                }
            } else if (request.getSession().getAttribute("usuario") == null) { //Si el usuario se quiere registrar por primera vez.

                usuario.setApellido(apellido);
                usuario.setlogin_name(login_name);
                usuario.setId_usuario(id);
                usuario.setNombre(nombre);
                usuario.setContrasenha(contrasenha);

                Usuario usuario_nuevo = new Usuario();
                usuario_nuevo.setlogin_name(login_name);
                usuario_nuevo.setContrasenha(contrasenha);

                if (um.insertar(usuario)) {
                    sesion.setAttribute("usuario", usuario_nuevo);
                    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                } else {
                    sesion.setAttribute("usuario", null);
                    request.setAttribute("mensaje", "Ya existe un cliente con ese alias.");
                    RequestDispatcher rd = request.getRequestDispatcher("/ClienteCrear.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                }
            }
            // ArrayList<Cliente> clientes = clc.getAll();
            //request.setAttribute("clientes", clientes);

        }

        if ("Editar".equals(accion)) {
            int idCli = Integer.valueOf(request.getParameter("vid"));
            ArrayList<Usuario> clientes = um.getAll();
            for (Usuario u : clientes) {
                if (u.getId_usuario() == idCli) {
                    request.setAttribute("cliente", u);
                }
            }

            RequestDispatcher rd = request.getRequestDispatcher("/ClienteEdit.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("GrabarModificado".equals(accion)) {
            id = Integer.valueOf(request.getParameter("id_usuario"));
            login_name = request.getParameter("login_name");
            nombre = request.getParameter("nombre");
            apellido = request.getParameter("apellido");
            contrasenha = request.getParameter("contraseña");
            usuario = new Usuario();

            // ver que pasa si no existe categoria
            usuario.setApellido(apellido);
            usuario.setlogin_name(login_name);
            usuario.setId_usuario(id);
            usuario.setNombre(nombre);
            usuario.setContrasenha(contrasenha);

            um.actualizar(usuario);

            ArrayList<Usuario> clientes = um.getAll();
            request.setAttribute("clientes", clientes);

            RequestDispatcher rd = request.getRequestDispatcher("/Clientes.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioABMServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioABMServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
