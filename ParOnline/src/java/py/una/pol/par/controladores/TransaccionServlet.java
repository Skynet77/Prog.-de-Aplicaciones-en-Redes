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
import py.una.pol.par.entidades.Producto;
import py.una.pol.par.modelos.ProductoManager;
import py.una.pol.par.entidades.Usuario;
import py.una.pol.par.entidades.TransaccionCabecera;
import py.una.pol.par.entidades.TransaccionDetalles;

/*
 * @author Skynet
 */

public class TransaccionServlet extends HttpServlet {

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
        Producto p = new Producto();
        ProductoManager pm = new ProductoManager();
        ArrayList<Producto> carritos;
        HttpSession hs = request.getSession(true); //Se obtiene un sesion.
        Usuario usuario = (Usuario) hs.getAttribute("usuario");
        carritos = (ArrayList<Producto>) hs.getAttribute("carritos");
        int id_pro = 0;

        String accion = request.getParameter("vaccion");
        if (request.getParameter("vid") != null) {
            id_pro = Integer.valueOf(request.getParameter("vid"));
        }

        if ("agregar_carrito".equals(accion)) {
            if (usuario == null && carritos == null) { //No se logueo y cargo un producto por primera vez.
                carritos = new ArrayList<>();
                //usuario = new Usuario(1001, "desconoc");
                p = pm.getProductoById(id_pro);
                carritos.add(p);

                hs.setAttribute("usuario", usuario);
                hs.setAttribute("carritos", carritos);

                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                if (rd != null) {
                    rd.forward(request, response);
                }

            } else if (usuario != null && carritos == null) { //Ya se logueo y cargo un producto por primera vez.
                p = pm.getProductoById(id_pro);
                carritos = new ArrayList<>();
                carritos.add(p);
                hs.setAttribute("carritos", carritos);
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                if (rd != null) {
                    rd.forward(request, response);
                }
            } else if (usuario != null && carritos != null) { //Ya se logueo o es un desconocido y presiono por segunda vez para cargar un producto.
                p = pm.getProductoById(id_pro);
                carritos = (ArrayList<Producto>) hs.getAttribute("carritos");
                carritos.add(p);
                hs.setAttribute("carritos", carritos);
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");//alamr al servlet deproducto
                if (rd != null) {
                    rd.forward(request, response);
                }
            }
        } else if ("quitar".equals(accion)) {
            if (carritos != null) {
                p = pm.getProductoById(id_pro);
                if (!"".equals(request.getParameter("cant"))) {
                    int cant = Integer.valueOf(request.getParameter("cant"));
                    for (int j = 0; j < cant; j++) {
                        cerrar:
                        for (int i = 0; i < carritos.size(); i++) {
                            if (carritos.get(i).getId_producto() == p.getId_producto()) {
                                carritos.remove(i);
                                break cerrar;
                            }
                        }
                    }
                    hs.setAttribute("carritos", carritos);
                    RequestDispatcher rd = request.getRequestDispatcher("/Carrito.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                } else {
                    System.out.println("sientro");
                    for (int i = 0; i < carritos.size(); i++) {
                        if (carritos.get(i).getId_producto() == p.getId_producto()) {
                            carritos.remove(i);
                            break;
                        }
                    }
                    System.out.println("despues del for");
                    hs.setAttribute("carritos", carritos);
                    RequestDispatcher rd = request.getRequestDispatcher("/Carrito.jsp");//alamr al servlet deproducto
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                }
            }
       } else if ("carrito".equals(accion)) {
            RequestDispatcher rd = request.getRequestDispatcher("/Carrito.jsp");//alamr al servlet deproducto
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
            Logger.getLogger(TransaccionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TransaccionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
