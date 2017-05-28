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
import py.una.pol.par.utilidades.ConexionBD;
import py.una.pol.par.entidades.TransaccionCabecera;
import py.una.pol.par.entidades.TransaccionDetalles;
import py.una.pol.par.entidades.Usuario;

/*
 * @author José Alvarez y Belén Desvars
 */

public class TransaccionManager {
    public boolean insertar(TransaccionCabecera c) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        int secuen = 0;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("insert into carrito (id_cliente, "
                    + "monto_total, direccion, forma_pago, fecha) values (?,?,?,?,?)");
            pstmt.setInt(1, c.getUsuario().getId_usuario());
            pstmt.setInt(2, c.getTotal_a_pagar());
            pstmt.setString(3, c.getDireccion());
            pstmt.setString(4, c.getForma_pago());
            pstmt.setString(5, c.getFecha());
            pstmt.execute();
            pstmt2 = conn.prepareStatement("select last_value from secuencia_carri"); //Retorna la ultima secuencia del carrito.
            rs = pstmt2.executeQuery();
            if (rs.next()) {
                secuen = rs.getInt(1);
            }
            //insertar la lista de contenido
            for (TransaccionDetalles con : c.getContenido()) {
                pstmt1 = conn.prepareStatement("insert into contenido_carrito(id_carrito,"
                        + " id_producto, cantidad, precio_cantidad) values (?,?,?,?)");
                pstmt1.setInt(1, secuen);
                pstmt1.setInt(2, con.getProductos().getId_producto());
                pstmt1.setInt(3, con.getCantidad());
                pstmt1.setInt(4, con.getMonto_total());
                pstmt1.execute();
                pstmt1.close();
            }
        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(TransaccionManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null && pstmt2 != null) {
                try {
                    pstmt.close();
                    pstmt2.close();

                } catch (SQLException ex) {
                    Logger.getLogger(TransaccionManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ConexionBD.closeConnection(conn);
        }
        return retValue;
    }

    public boolean update(TransaccionCabecera c) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("update carrito set id_cliente = ?,"
                    + "monto_total = ?, direccion = ?, forma_pago = ?, fecha = ? where id_carrito = ?");
            pstmt.setInt(1, c.getUsuario().getId_usuario());
            pstmt.setInt(2, c.getTotal_a_pagar());
            pstmt.setString(3, c.getDireccion());
            pstmt.setString(4, c.getForma_pago());
            pstmt.setString(5, c.getFecha());
            pstmt.setInt(6, c.getId_transaccion());
            pstmt.execute();
        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(TransaccionManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        return retValue;
    }

    public boolean delete(TransaccionCabecera tc) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("delete from carrito where id_cliente = ?");
            pstmt.setInt(1, tc.getUsuario().getId_usuario());
            pstmt.execute();

        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(TransaccionManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        return retValue;
    }

    /*
     * Funcion que busca los distintos carritos que puede llegar a tener un cliente los
     * cuales son buscados por la cedula del cliente.
     *
     * @param id_usuario
     * @return ArrayList<Carrito>
     * @throws Exception
     */
    
    public ArrayList<TransaccionCabecera> getTransaccionByUsuario(int id_usuario) throws Exception {
        TransaccionCabecera carrito = null;
        ProductoManager pm = new ProductoManager();
        Usuario usuario = new Usuario();
        UsuarioManager um = new UsuarioManager();
        ArrayList<TransaccionCabecera> lista_transacciones = new ArrayList<TransaccionCabecera>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;
        TransaccionDetalles contenido = new TransaccionDetalles();
        ArrayList<TransaccionDetalles> contenidos = new ArrayList<TransaccionDetalles>();

        try {
            usuario = um.getUsuarioById(id_usuario);
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select id_transaccion, id_cliente, direccion, forma_pago"
                    + " from carrito where id_cliente = ?");
            pstmt.setInt(1, usuario.getId_usuario());
            rs = pstmt.executeQuery();
            pstmt1 = conn.prepareStatement("select id_carrito, id_producto, cantidad, precio_cantidad, id_contenido"
                    + "from contenido_carrito where id_carrito = ?");
            pstmt1.setInt(1, rs.getInt(1));
            rs1 = pstmt1.executeQuery();
            while (rs.next()) {
                carrito = new TransaccionCabecera();
                carrito.setUsuario(usuario);
                carrito.setId_transaccion(rs.getInt(1));
                carrito.setTotal_a_pagar(rs.getInt(3));
                carrito.setDireccion(rs.getString(4));
                carrito.setForma_pago(rs.getString(5));

                while (rs1.next()) {
                    contenido.setCantidad(rs1.getInt(3));
                    contenido.setCarrito(carrito);
                    contenido.setMonto_total(rs1.getInt(4));
                    contenido.setProductos(pm.getProductoById(rs1.getInt(2)));
                    contenido.setId_contenido(rs1.getInt(5));
                    contenidos.add(contenido);
                }
                //guarda la lista de contenidos relacionados a un carrito
                carrito.setContenido(contenidos);
                //guardamos los carritos para UN cliente buscado por cedula
                lista_transacciones.add(carrito);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }

        return lista_transacciones;
    }

    /**
     * Funcion que retorna el listado de carritos creados en el sistema.
     *
     * @return ArrayList<Carrito>
     * @throws Exception
     */
    public ArrayList<TransaccionCabecera> getAll() throws Exception {
        ArrayList<TransaccionCabecera> retValue = new ArrayList<TransaccionCabecera>();
        UsuarioManager tm = new UsuarioManager();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select id_carrito, id_cliente, monto_total, direccion, forma_pago"
                    + " from carrito");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                TransaccionCabecera c = new TransaccionCabecera();
                c.setId_transaccion(rs.getInt(1));
                c.setUsuario(tm.getUsuarioById(rs.getInt(2)));
                c.setTotal_a_pagar(rs.getInt(3));
                c.setDireccion(rs.getString(4));
                c.setForma_pago(rs.getString(5));
                retValue.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaccionManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        return retValue;
    }
}
