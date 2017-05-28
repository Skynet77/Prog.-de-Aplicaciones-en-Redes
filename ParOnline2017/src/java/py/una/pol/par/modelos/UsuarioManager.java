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
import py.una.pol.par.entidades.Usuario;

/*
 * @author José Alvarez y Belén Desvars
 */

public class UsuarioManager {
        public boolean insertar(Usuario u) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;
        Usuario usuario = null;
        usuario = getUsuarioById(u.getId_usuario());

        if (usuario == null) {
            try {
                conn = ConexionBD.getConnection(); //Se abre la conexion con la bd.
                pstmt = conn.prepareStatement("insert into usuario (cedula, nombre, "
                        + "apellido, tipoUsuario, aliasUsuario, pass) values (?,?,?,?)");
                pstmt.setInt(1, u.getId_usuario());
                pstmt.setString(2, u.getNombre());
                pstmt.setString(3, u.getApellido());
                pstmt.setString(4, u.getTipoUsuario());
                pstmt.setString(5, u.getaliasUsuario());
                pstmt.setString(6, u.getPass());
                pstmt.execute(); //Se ejecuta la sentencia sql.
            } catch (SQLException ex) {
                retValue = false;
                Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                ConexionBD.closeConnection(conn); //Se cierra la conexion.
            }
        } else {
            retValue = false;
        }
        return retValue;
    }

    public boolean actualizar(Usuario u) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection(); //Se abre la conexion con la bd.
            pstmt = conn.prepareStatement("update cliente set nombre = ?, "
                    + "apellido = ?, tipoUsuario = ?, aliasUsuario = ?, pass = ?  where id_cliente = ? ");
            pstmt.setString(1, u.getNombre());
            pstmt.setString(2, u.getApellido());
            pstmt.setString(3, u.getTipoUsuario());
            pstmt.setString(4, u.getaliasUsuario());
            pstmt.setString(5, u.getPass());
            pstmt.setInt(6, u.getId_usuario());
            pstmt.execute(); //Se ejecuta la sentencia sql.
        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn); //Se cierra la conexion.
        }
        return retValue;
    }

    public boolean eliminar(Usuario u) throws Exception {
        boolean retValue = true;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = ConexionBD.getConnection(); //Se abre la conexion con la bd.
            pstmt = conn.prepareStatement("delete from usuario where id_cliente = ?");
            pstmt.setInt(1, u.getId_usuario());
            pstmt.execute(); //Se ejecuta la sentencia sql.
        } catch (SQLException ex) {
            retValue = false;
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn); //Se cierra la conexion.
        }
        return retValue;
    }

    public Usuario getUsuarioById(int id_usuario) throws Exception {
        Usuario usuario = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select nombre, apellido, tipoUsuario, aliasUsuario, pass"
                    + " from usuario where id_cliente = ?");
            pstmt.setInt(1, id_usuario);
            rs = pstmt.executeQuery(); //Se obtiene la lista que esta en el select.

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setNombre(rs.getString(1));
                usuario.setApellido(rs.getString(2));
                usuario.setTipoUsuario(rs.getString(3));
                usuario.setaliasUsuario(rs.getString(4));
                usuario.setPass(rs.getString(5));

            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        return usuario;
    }

    public ArrayList<Usuario> getAll() throws Exception {
        ArrayList<Usuario> retValue = new ArrayList<Usuario>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select id_cliente, cedula, nombre, apellido,"
                    + "pass from cliente");
            rs = pstmt.executeQuery(); //Se obtiene la lista que esta en el select.
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setTipoUsuario(rs.getString(4));
                usuario.setaliasUsuario(rs.getString(5));
                usuario.setPass(rs.getString(6));
                retValue.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        return retValue;
    }

    public int login_usuario(String aliasUsuario, String pass) throws Exception {
        int retValue = 4;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select * from cliente where aliasUsuario = ? and pass = ?");
            pstmt.setString(1, aliasUsuario);
            pstmt.setString(2, pass);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                
                if ("Administrador" == rs.getString(1) && pass.equals(rs.getString(2).trim())) {
                    retValue = 1; //Es el administrador.
                } else {
                    retValue = 2; //Es un usuario normal.
                }
            }

        } catch (SQLException ex) {
            retValue = 3;
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        return retValue;
    }
}
