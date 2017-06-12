/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.controladores;

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
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

        String vaccion = request.getParameter("vaccion");
        request.setAttribute("vaccion", vaccion);
        HttpSession sesion = request.getSession();
        Usuario usuario = new Usuario();
        UsuarioManager um = new UsuarioManager();
        usuario = null;
        int id = 0;
        int cedula = 0;
        String nombre = null;
        String apellido = null;
        String pass = null;

        if (vaccion == null) {
            //modo grilla...se muestran todos los registros
            ArrayList<Usuario> usuario = um.getAll();
            request.setAttribute("usuarios", usuario);

            RequestDispatcher rd = request.getRequestDispatcher("/Usuarios.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Eliminar".equals(vaccion)) {
            int idCli = Integer.valueOf(request.getParameter("vid"));
            usuario = new Usuario();
            usuario.setId_usuario(idCli);

            um.eliminar(usuario);

            ArrayList<Usuario> usuarios = um.getAll();
            request.setAttribute("usuarios", usuario);
            RequestDispatcher rd = request.getRequestDispatcher("/Usuarios.jsp");

            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Grabar".equals(vaccion)) {
            //id = Integer.valueOf(request.getParameter("id_cli"));
            cedula = Integer.valueOf(request.getParameter("cedula"));
            nombre = request.getParameter("nombre");
            apellido = request.getParameter("apellido");
            pass = request.getParameter("pass");
            usuario = new Usuario();

            //debo ver si en la sesion esta el admin o un desconocido y mandarlo al index
            if (request.getSession().getAttribute("usuario") != null) { //Si hay un usuario logueado.

                usuario = (Usuario) sesion.getAttribute("usuario");

                //si esta el admin debo mandarlo al listado porque esta agregando clientes
                if (um.login_usuario(usuario.getlogin_name(), usuario.getContrasenha()) == 1) { //El administrador le registra a un cliente.
                    cliente.setApellido(apellido);
                    cliente.setCedula(cedula);
                    cliente.setNombre(nombre);
                    cliente.setPass(pass);

                    clc.insertar(cliente);

                    ArrayList<Cliente> clientes = clc.getAll();
                    request.setAttribute("clientes", clientes);

                    RequestDispatcher rd = request.getRequestDispatcher("/Clientes.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                    //si no, es un desconocido debo cambiar el usuario cargado en la sesion
                } else if (clc.is_usuario(usuario.getCedula(), usuario.getPass()) == 2) { //EL usuario desconocido se registra solo.
                    //sesion.invalidate();
                    cliente.setApellido(apellido);
                    cliente.setCedula(cedula);
                    cliente.setId_cliente(id);
                    cliente.setNombre(nombre);
                    cliente.setPass(pass);
                    Usuario usuario_nuevo = new Usuario();
                    usuario_nuevo.setCedula(cedula);
                    usuario_nuevo.setPass(pass);
                    clc.insertar(cliente);

                    sesion.setAttribute("usuario", usuario_nuevo);

                    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                }
            } else if (request.getSession().getAttribute("usuario") == null) { //Si el usuario se quiere registrar por primera vez.

                cliente.setApellido(apellido);
                cliente.setCedula(cedula);
                cliente.setId_cliente(id);
                cliente.setNombre(nombre);
                cliente.setPass(pass);

                Usuario usuario_nuevo = new Usuario();
                usuario_nuevo.setCedula(cedula);
                usuario_nuevo.setPass(pass);

                if (clc.insertar(cliente)) {
                    sesion.setAttribute("usuario", usuario_nuevo);
                    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                } else {
                    sesion.setAttribute("usuario", null);
                    request.setAttribute("mensaje", "Ya existe un cliente con esa C.I.");
                    RequestDispatcher rd = request.getRequestDispatcher("/ClienteCrear.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                }
            }
            // ArrayList<Cliente> clientes = clc.getAll();
            //request.setAttribute("clientes", clientes);

        }

        if ("Editar".equals(vaccion)) {
            int idCli = Integer.valueOf(request.getParameter("vid"));
            ArrayList<Cliente> clientes = clc.getAll();
            for (Cliente c : clientes) {
                if (c.getId_cliente() == idCli) {
                    request.setAttribute("cliente", c);
                }
            }

            RequestDispatcher rd = request.getRequestDispatcher("/ClienteEdit.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("GrabarModificado".equals(vaccion)) {
            id = Integer.valueOf(request.getParameter("id_usuario"));
            login_name = request.getParameter("login_name");
            nombre = request.getParameter("nombre");
            apellido = request.getParameter("apellido");
            pass = request.getParameter("contraseña");
            usuario = new Usuario();

            // ver que pasa si no existe categoria
            usuario.setApellido(apellido);
            usuario.setlogin_name(login_name);
            usuario.setId_usuario(id_usuario);
            usuario.setNombre(nombre);
            usuario.setContrasenha(pass);

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
