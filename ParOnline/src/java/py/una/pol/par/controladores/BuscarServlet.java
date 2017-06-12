/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import py.una.pol.par.entidades.Producto;
import py.una.pol.par.modelos.ProductoManager;

/*
 * @author Skynet
 */

public class BuscarServlet extends HttpServlet {

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
        int categoria = 0;
        Producto p = new Producto();
        ProductoManager pm = new ProductoManager();
        String filtro_cat = null; //variable para ver que categoria eligio
        String filtro_desc = null; //variable para ver que descripcion eligio
        ArrayList<Producto> descripciones = new ArrayList<Producto>(); //una lista de productos por descripcion 
        //obtiene el valor del parametro categoria
        if (request.getParameter("categorias") != null) { //si no fuer llamado por el form
            //categorias siempre tendra un valor por defecto
            //pero descripcion si debemos ver que haya agregado o no
            if (request.getParameter("descripcion") != null) {
                filtro_cat = request.getParameter("categorias");
                filtro_desc = request.getParameter("descripcion");

                if ("Todo".equals(filtro_cat.trim())) { //saco los espacios
                    ArrayList<Producto> arp = pm.getAll();
                    descripciones = busqueda_cadena(arp, filtro_desc);// buscamos la descripcion dentro del listado de la categoria recuperada
                    Collections.sort(descripciones); //ordena alfabeticamente las descripciones
                    request.setAttribute("array_producto", descripciones);
                } else if ("1".equals(filtro_cat)) {
                    ArrayList<Producto> arp = pm.getAllByCat(Integer.valueOf(filtro_cat));
                    descripciones = busqueda_cadena(arp, filtro_desc);// buscamos la descripcion dentro del listado de la categoria recuperada
                    Collections.sort(descripciones);
                    request.setAttribute("array_producto", descripciones);
                } else if ("2".equals(filtro_cat)) {
                    ArrayList<Producto> arp = pm.getAllByCat(Integer.valueOf(filtro_cat));
                    descripciones = busqueda_cadena(arp, filtro_desc);// buscamos la descripcion dentro del listado de la categoria recuperada
                    Collections.sort(descripciones);
                    request.setAttribute("array_producto", descripciones);
                } else if ("3".equals(filtro_cat)) {
                    ArrayList<Producto> arp = pm.getAllByCat(Integer.valueOf(filtro_cat));
                    descripciones = busqueda_cadena(arp, filtro_desc);// buscamos la descripcion dentro del listado de la categoria recuperada
                    Collections.sort(descripciones);
                    request.setAttribute("array_producto", descripciones);
                }
                // si no entro en ninguna categoria porque la descripcion que introdujo no existe en ninguna categoria
                // entonces se verifica que array_producto sea nullen el jsp
            } 
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        } else if (request.getParameter("categoria") != null) {
            categoria = Integer.valueOf(request.getParameter("categoria"));

            if (categoria == 1) {
                ArrayList<Producto> productos = pm.getAllByCat(categoria);
                request.setAttribute("array_producto", productos); //se guarda como atributo de la peticion 

            } else if (categoria == 2) {
                ArrayList<Producto> productos = pm.getAllByCat(categoria);
                request.setAttribute("array_producto", productos);

            } else {
                ArrayList<Producto> productos = pm.getAllByCat(categoria);
                request.setAttribute("array_producto", productos);

            }
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
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
            Logger.getLogger(BuscarServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BuscarServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private ArrayList<Producto> busqueda_cadena(ArrayList<Producto> array_pro, String desc) {
        ArrayList<Producto> descripciones = new ArrayList<Producto>();

        for (Producto array_pro1 : array_pro) { //recorre el array enel index se guarda la posicion donde se encuntra esa rescriocion
            int index = array_pro1.getDescripcion().toLowerCase().indexOf(desc.toLowerCase());
            if (index > -1) {
                descripciones.add(array_pro1);
            }
        }
        return descripciones;
    }
}
