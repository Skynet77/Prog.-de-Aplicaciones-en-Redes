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
import py.una.pol.par.entidades.Categoria;
import py.una.pol.par.modelos.CategoriaManager;
import py.una.pol.par.entidades.Producto;
import py.una.pol.par.modelos.ProductoManager;

/*
 * @author Skynet
 */

public class ProductoABMServlet extends HttpServlet {

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

        ProductoManager pm = new ProductoManager();
        CategoriaManager cm = new CategoriaManager();

        if (vaccion == null) {
            //modo grilla...se muestran todos los registros
            ArrayList<Producto> productos = pm.getAll();
            request.setAttribute("productos", productos);

            RequestDispatcher rd = request.getRequestDispatcher("/Producto/Producto.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Eliminar".equals(vaccion)) {
            int idPro = Integer.valueOf(request.getParameter("vid"));
            Producto pro = new Producto();
            pro.setId_producto(idPro);

            pm.delete(pro);

            ArrayList<Producto> productos = pm.getAll();
            request.setAttribute("productos", productos);

            RequestDispatcher rd = request.getRequestDispatcher("/Producto/Producto.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Grabar".equals(vaccion)) {
            int id = Integer.valueOf(request.getParameter("id_cat"));
            int id_pro = Integer.valueOf(request.getParameter("id_pro"));
            int precio = Integer.valueOf(request.getParameter("precio"));
            String desc = request.getParameter("descripcion");
            Categoria cat = new Categoria();
            // ver que pasa si no existe categoria
            Producto pro = new Producto();
            pro.setDescripcion(desc);
            pro.set_categoria(cm.getCategoriaById(id));
            pro.setId_producto(id_pro);
            pro.setPrecio(precio);
            pm.insertar(pro);
            //se agrega un pro y se devuelve nuevamente todos losproductos
            ArrayList<Producto> productos = pm.getAll();
            request.setAttribute("productos", productos);

            RequestDispatcher rd = request.getRequestDispatcher("/Producto/Producto.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Editar".equals(vaccion)) {
            int idPro = Integer.valueOf(request.getParameter("vid"));
            Producto pro = pm.getProductoById(idPro);
            request.setAttribute("producto", pro);
            RequestDispatcher rd = request.getRequestDispatcher("/Producto/ProductoEditar.jsp");
            
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("GrabarModificado".equals(vaccion)) {
            int id = Integer.valueOf(request.getParameter("id_cat"));
            int id_pro = Integer.valueOf(request.getParameter("vid"));
            int precio = Integer.valueOf(request.getParameter("precio"));
            String desc = request.getParameter("descripcion");
            Producto pro = new Producto();
            pro.set_categoria(cm.getCategoriaById(id));
            pro.setDescripcion(desc);
            pro.setId_producto(id_pro);
            pro.setPrecio(precio);
            pm.update(pro);
            ArrayList<Producto> productos = pm.getAll();
            request.setAttribute("productos", productos);
            RequestDispatcher rd = request.getRequestDispatcher("/Producto/Producto.jsp");
            
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
            Logger.getLogger(ProductoABMServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProductoABMServlet.class.getName()).log(Level.SEVERE, null, ex);
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
