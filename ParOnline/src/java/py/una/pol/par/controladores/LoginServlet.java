/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.controladores;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import py.una.pol.par.entidades.Usuario;
import py.una.pol.par.modelos.UsuarioManager;

/*
 * @author Skynet
 */

public class LoginServlet extends HttpServlet {

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

        UsuarioManager um = new UsuarioManager();
        String login_name = null;
        String contrasenha = null;
        String mensaje = null;
        Usuario usuario = new Usuario();
        String registrar = null;
        String accion = request.getParameter("accion");
        request.setAttribute("accion", accion);
        HttpSession sesion = request.getSession(); //se crea un espacio

        if (request.getParameter("registrar") != null) {
            registrar = request.getParameter("registrar");
        }

        if ("login".equals(accion)) {
            /**
             * una posibiliad es que el usuario sea nuevo e intente loguearse
             * por primera vez segunda posibilidad sea el usuario desconocido
             */
            login_name = request.getParameter("login_name");
            contrasenha = request.getParameter("contrasenha");

            if (request.getSession().getAttribute("usuario") == null) { //Nadie esta logueado.
                if (um.es_usuario(login_name, contrasenha) == 0) { // Si es admin.
                    usuario.setlogin_name(login_name);
                    usuario.setContrasenha(contrasenha);
                    sesion.setAttribute("usuario", usuario); //Se guarda en la sesion.
                    sesion.setMaxInactiveInterval(60 * 30); //Puede estar logueado por 10min.
                    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp"); 
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                } else if (um.es_usuario(login_name, contrasenha) == 1) { //Es un usuario normal.
                    usuario.setlogin_name(login_name);
                    usuario.setContrasenha(contrasenha);
                    sesion.setAttribute("usuario", usuario); //Se guarda en la sesion.
                    sesion.setMaxInactiveInterval(60 * 30); //Puede estar logueado por 10min.
                    response.sendRedirect("/ParOnline/index.jsp");

                } else { //Es un usuario incorrecto que no esta en la base de datos o es un usuario "" vacio.
                    mensaje = "incorrecto";
                    request.setAttribute("mensaje", mensaje);
                    RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp"); 
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                };
            } else {
                //Se supone que la cedula y el pas fueron validados en el jsp y de que el usuario esta logueado.
                usuario = (Usuario) sesion.getAttribute("usuario");
                if (um.es_usuario(login_name, contrasenha) == 2) { //Es un usuario incorrecto.
                    mensaje = "incorrecto";
                    request.setAttribute("mensaje", mensaje);
                    RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                } else if (um.es_usuario(usuario.getlogin_name(), usuario.getContrasenha()) == 2) { //Es un usuario desconocido.
                    usuario.setlogin_name(login_name);
                    usuario.setContrasenha(contrasenha);
                    sesion = request.getSession(true);
                    sesion.setAttribute("usuario", usuario); //Se guarda en la sesion.
                    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                } else {
                    mensaje = "logueado";
                    request.setAttribute("mensaje", mensaje);
                    RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp"); 
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                }
            }
        } else if ("registrar".equals(registrar)) {
            RequestDispatcher rd = request.getRequestDispatcher("/UsuarioCrear.jsp"); 
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
        String vaccion = null;

        if (request.getParameter("logout") != null) {
            vaccion = request.getParameter("logout");
        }

        if ("logout".equals(vaccion)) {
            HttpSession sesion = request.getSession();
            sesion.invalidate(); //Elimina la sesion, te deslogueda.
            sesion = null;
            response.sendRedirect("/Par_online/index.jsp");
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
