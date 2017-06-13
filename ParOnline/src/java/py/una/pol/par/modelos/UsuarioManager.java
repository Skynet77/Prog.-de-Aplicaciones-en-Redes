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
 * @author Skynet
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
                pstmt = conn.prepareStatement("insert into usuario (nombre, "
                        + "apellido, tipo_usuario, login_name, contrasenha) values (?,?,?,?,md5(?))");
                pstmt.setInt(1, u.getId_usuario());
                pstmt.setString(2, u.getNombre());
                pstmt.setString(3, u.getApellido());
                pstmt.setInt(4, u.getTipo_usuario());
                pstmt.setString(5, u.getlogin_name());
                pstmt.setString(6, u.getContrasenha());
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
            pstmt.setInt(3, u.getTipo_usuario());
            pstmt.setString(4, u.getlogin_name());
            pstmt.setString(5, u.getContrasenha());
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
            pstmt = conn.prepareStatement("select nombre, apellido, tipo_usuario,"
                    + "login_name from usuario where id_cliente = ?");
            pstmt.setInt(1, id_usuario);
            rs = pstmt.executeQuery(); //Se obtiene la lista que esta en el select.

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setNombre(rs.getString(1));
                usuario.setApellido(rs.getString(2));
                usuario.setTipo_usuario(rs.getInt(3));
                usuario.setlogin_name(rs.getString(4));

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
            pstmt = conn.prepareStatement("select id_cliente, nombre, apellido,"
                    + "Tipo_usuario, login_name from usuario");
            rs = pstmt.executeQuery(); //Se obtiene la lista que esta en el select.
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt(1));
                usuario.setNombre(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setTipo_usuario(rs.getInt(4));
                usuario.setlogin_name(rs.getString(5));
                retValue.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConexionBD.closeConnection(conn);
        }
        return retValue;
    }
    
    public int es_usuario(String login_name, String contrasenha) throws Exception {
        int retValue = 3;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Usuario u = null;
        int tipo = 2;

        try {
            conn = ConexionBD.getConnection();
            pstmt = conn.prepareStatement("select tipo_usuario from cliente where login_name = ? and contrasenha = md5(?)");
            pstmt.setString(1, login_name);
            pstmt.setString(2, contrasenha);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                // Si el tipo de usuario es 0 es el admin
                u = new Usuario();
                u.setTipo_usuario(rs.getInt(1));
                if (tipo == 0) {
                    retValue = 0; //Es el administrador.
                } else {
                    retValue = 1; //Es un usuario normal.
                }
            }
            if ("usuario" == login_name && "desconoc".equals(contrasenha.trim())) {
                retValue = 2; //Es un desconocido.
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
