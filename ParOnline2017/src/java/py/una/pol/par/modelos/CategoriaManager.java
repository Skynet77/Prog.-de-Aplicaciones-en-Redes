/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.una.pol.par.entidades.Categoria;
import py.una.pol.par.utilidades.ConexionBD;

/**
 *
 * @author José Alvarez
 */

public class CategoriaManager {
        public boolean insertar(Categoria c) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("insert into categoria (id_categoria, descripcion) values (?,?)");
            pstmt.setInt(1, c.getId_categoria());
            pstmt.setString(2, c.getDescripcion());
            pstmt.execute();

        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(CategoriaManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CategoriaManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ConexionBD.closeConnection(conn);
        }

        return retValue;
    }

    public boolean update(Categoria c) throws Exception {
        boolean retValue = true;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("update categoria set descripcion = ? where id_categoria = ?");
            pstmt.setString(1, c.getDescripcion());
            pstmt.setInt(2, c.getId_categoria());
            pstmt.execute();

        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(CategoriaManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }

        return retValue;
    }

    public boolean delete(Categoria c) throws Exception {
        boolean retValue = true;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("delete from categoria where id_categoria = ?");
            pstmt.setInt(1, c.getId_categoria());
            pstmt.execute();

        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(CategoriaManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }

        return retValue;
    }

    public Categoria getCategoriaById(int id) throws Exception {
        Categoria retValue = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select descripcion from categoria where id_categoria = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                retValue = new Categoria();
                retValue.setId_categoria(id);
                retValue.setDescripcion(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }

        return retValue;
    }

    public ArrayList<Categoria> getAll() throws Exception {
        ArrayList<Categoria> retValue = new ArrayList<Categoria>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select id_categoria, descripcion from categoria");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId_categoria(rs.getInt(1));
                c.setDescripcion(rs.getString(2));
                retValue.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }

        return retValue;
    }
}
