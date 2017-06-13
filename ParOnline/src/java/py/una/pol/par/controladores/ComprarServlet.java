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
import py.una.pol.par.entidades.Usuario;
import py.una.pol.par.modelos.UsuarioManager;
import py.una.pol.par.entidades.Producto;
import py.una.pol.par.modelos.ProductoManager;
import py.una.pol.par.entidades.TransaccionCabecera;
import py.una.pol.par.entidades.TransaccionDetalles;
import py.una.pol.par.modelos.TransaccionManager;

/*
 * @author Skynet
 */

public class ComprarServlet extends HttpServlet {

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
        Usuario usu_old=null;
        String accion = request.getParameter("accion");
        HttpSession hs = request.getSession();
        Usuario usuario = (Usuario) hs.getAttribute("usuario");
        TransaccionManager tm = new TransaccionManager();
        String login_name = null;
        String contrasenha = null;

        if ("comprar".equals(accion)) {

            if (usuario != null) {
                login_name = usuario.getlogin_name();
                contrasenha = usuario.getContrasenha();

                if (um.es_usuario(login_name, contrasenha) == 2) { //Usuario desconocido.
                    RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");  
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                } else if (um.es_usuario(login_name, contrasenha) == 3) { //Usuario normal.
                    RequestDispatcher rd = request.getRequestDispatcher("/Compra.jsp");
                    if (rd != null) {
                        rd.forward(request, response);
                    }
                }
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp"); // 
                if (rd != null) {
                    rd.forward(request, response);
                }
            }
        } else if ("Pagar".equals(accion)) {
            if (usuario != null) {
                Usuario cliente = new Usuario();
                TransaccionDetalles contenido = null;
                Usuario u = (Usuario) request.getSession().getAttribute("usuario");
                ProductoManager pc = new ProductoManager();
                TransaccionCabecera carri = new TransaccionCabecera();
                String direccion = request.getParameter("direccion");
                String forma_pago = request.getParameter("forma_pago");
                int can = 0;
                int index = 0;
                int suma_total = 0;
                //Obtenemos el cliente.
                cliente = um.getUsuarioById(usuario.getId_usuario());
                //Lista de los contenidos de un carrito.
                ArrayList<TransaccionDetalles> contenidos = new ArrayList<TransaccionDetalles>();
                //Lista de productos que estan en el carritos.
                ArrayList<Producto> lp = (ArrayList<Producto>) request.getSession().getAttribute("carritos");
                //Lista de productos en la bd.
                ArrayList<Producto> todo_producto = pc.getAll();

                if (lp != null) {
                    for (int j = 0; j < todo_producto.size(); j++) {
                        can = 0;
                        for (int i = 0; i < lp.size(); i++) {
                            if (todo_producto.get(j).getId_producto() == lp.get(i).getId_producto()) {
                                can += 1;
                                index = i;
                            }
                        }
                        if (can != 0) {
                            contenido = new TransaccionDetalles();
                            contenido.setCantidad(can);
                            contenido.setProductos(lp.get(index));
                            contenido.setSubtotal(lp.get(index).getPrecio() * can);
                            contenido.setCarrito(carri);
                            contenidos.add(contenido);
                        }
                        suma_total += lp.get(index).getPrecio() * can;
                    }
                    carri.setUsuario(cliente);
                    carri.setTotal_a_pagar(suma_total);
                    carri.setContenido(contenidos);
                    carri.setDireccion(direccion);
                    carri.setForma_pago(forma_pago);
                    carri.setFecha();
                    request.getSession().setAttribute("true", "true");
                }
                tm.insertar(carri);
            }
            request.getSession().setAttribute("carritos", null);
            //request.getSession().setAttribute("true", "false");
            RequestDispatcher rd = request.getRequestDispatcher("/Compra.jsp");
                     
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
            Logger.getLogger(ComprarServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ComprarServlet.class.getName()).log(Level.SEVERE, null, ex);
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
