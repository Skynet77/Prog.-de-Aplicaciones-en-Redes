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
import py.una.pol.par.entidades.Producto;
import py.una.pol.par.utilidades.ConexionBD;

/*
 * @author José Alvarez y Belén Desvars
 */

public class ProductoManager {
    public boolean insertar(Producto p) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection(); //Se abre la conexion con la bd.
            pstmt = conn.prepareStatement("insert into producto (id_producto, id_categoria, precio, descripcion) "
                    + "values (?,?,?,?)");
            pstmt.setInt(1, p.getId_producto());
            pstmt.setInt(2, p.get_categoria().getId_categoria());
            pstmt.setInt(3, p.getPrecio());
            pstmt.setString(4, p.getDescripcion());
            pstmt.execute(); //Se ejecuta la sentencia sql.
        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(ProductoManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductoManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ConexionBD.closeConnection(conn); //Se cierra la conexion.
        }
        return retValue;
    }

    public boolean update(Producto p) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection(); //Se abre la conexion con la bd.
            pstmt = conn.prepareStatement("update producto set precio = ?, "
                    + "descripcion = ?, id_categoria = ?   where id_producto = ? ");
            pstmt.setInt(1, p.getPrecio());
            pstmt.setString(2, p.getDescripcion());
            pstmt.setInt(3, p.get_categoria().getId_categoria());
            pstmt.setInt(4, p.getId_producto());
            pstmt.execute(); //Se ejecuta la sentencia sql.
        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn); //Se cierra la conexion.
        }
        return retValue;
    }

    public boolean delete(Producto p) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("delete from producto where id_producto = ?");
            pstmt.setInt(1, p.getId_producto());
            pstmt.execute();
        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn); //Se cierra la conexion.
        }
        return retValue;
    }

    public Producto getProductoByName(String nombre) throws Exception {
        Producto producto = null;
        CategoriaManager cc = new CategoriaManager();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select id_producto, descripcion, id_categoria, precio"
                    + " from producto where descripcion = ?");
            pstmt.setString(1, nombre);
            rs = pstmt.executeQuery(); //un objto que puede soportar una tabla de resultado ejecutar consulta
            if (rs.next()) {
                producto = new Producto();
                producto.setId_producto(rs.getInt(1));
                producto.setDescripcion(rs.getString(2));
                producto.setPrecio(rs.getInt(4));
                producto.set_categoria(cc.getCategoriaById(rs.getInt(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        return producto;
    }

    public ArrayList<Producto> getAll() throws Exception {
        ArrayList<Producto> retValue = new ArrayList<Producto>();
        CategoriaManager cc = new CategoriaManager();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select id_producto, id_categoria, precio, descripcion "
                    + "from producto");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId_producto(rs.getInt(1));
                p.set_categoria(cc.getCategoriaById(rs.getInt(2)));
                p.setPrecio(rs.getInt(3));
                p.setDescripcion(rs.getString(4));
                retValue.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        return retValue;
    }

    public Producto getProductoById(int id) throws Exception {
        Producto producto = null;
        CategoriaManager cc = new CategoriaManager();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select id_producto, descripcion, id_categoria, precio"
                    + " from producto where id_producto = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery(); //un objto que puede soportar una tabla de resultado ejecutar consulta
            if (rs.next()) {
                producto = new Producto();
                producto.setId_producto(rs.getInt(1));
                producto.setDescripcion(rs.getString(2));
                producto.setPrecio(rs.getInt(4));
                producto.set_categoria(cc.getCategoriaById(rs.getInt(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        System.out.println(producto);
        return producto;
    }

    public ArrayList<Producto> getAllByCat(int categoria) throws Exception {
        ArrayList<Producto> retValue = new ArrayList<Producto>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CategoriaManager cc = new CategoriaManager();

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select id_producto, descripcion, id_categoria, precio"
                    + " from producto where id_categoria = ?");
            pstmt.setInt(1, categoria);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Producto c = new Producto();
                c.setId_producto(rs.getInt(1));
                c.setDescripcion(rs.getString(2));
                c.setPrecio(rs.getInt(4));
                c.set_categoria(cc.getCategoriaById(rs.getInt(3)));
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
