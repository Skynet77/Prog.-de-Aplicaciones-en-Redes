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

/**
 *
 * @author Skynet
 */
public class CategoriaABMServlet extends HttpServlet {

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
        int cat = 0;
        String vaccion = request.getParameter("vaccion");
        request.setAttribute("vaccion", vaccion);
        CategoriaManager cm = new CategoriaManager();

        if (vaccion == null) {
            //modo grilla...se muestran todos los registros
            ArrayList<Categoria> categorias = cm.getAll();
            request.setAttribute("categorias", categorias);
            RequestDispatcher rd = request.getRequestDispatcher("/Categoria.jsp");
            
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Eliminar".equals(vaccion)) {
            int idCat = Integer.valueOf(request.getParameter("vid"));
            Categoria c = new Categoria();
            c.setId_categoria(idCat);

            cm.delete(c);//puede que reciba un id nomas de categoria

            ArrayList<Categoria> categorias = cm.getAll();
            request.setAttribute("categorias", categorias);
            RequestDispatcher rd = request.getRequestDispatcher("/Categoria.jsp");
            
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Grabar".equals(vaccion)) {
            String desc = request.getParameter("descripcion");
            int id = Integer.valueOf(request.getParameter("id_cat"));
            Categoria c = new Categoria();
            c.setId_categoria(id);
            c.setDescripcion(desc);

            cm.insertar(c);

            ArrayList<Categoria> categorias = cm.getAll();
            request.setAttribute("categorias", categorias);

            RequestDispatcher rd = request.getRequestDispatcher("/Categoria.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("Editar".equals(vaccion)) {
            int idCat = Integer.valueOf(request.getParameter("vid"));
            Categoria c = cm.getCategoriaById(idCat);

            request.setAttribute("categoria", c);

            RequestDispatcher rd = request.getRequestDispatcher("/CategoriaEdit.jsp");
            if (rd != null) {
                rd.forward(request, response);
            }
        }

        if ("GrabarModificado".equals(vaccion)) {
            int idCat = Integer.valueOf(request.getParameter("vid"));
            String desc = request.getParameter("descripcion");
            Categoria c = new Categoria();
            c.setId_categoria(idCat);
            c.setDescripcion(desc);

            cm.update(c);

            ArrayList<Categoria> categorias = cm.getAll();
            request.setAttribute("categorias", categorias);

            RequestDispatcher rd = request.getRequestDispatcher("/Categoria.jsp");
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
            Logger.getLogger(CategoriaABMServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CategoriaABMServlet.class.getName()).log(Level.SEVERE, null, ex);
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
