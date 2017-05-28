/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.entidades;

import java.util.logging.Level;
import java.util.logging.Logger;
import py.una.pol.par.modelos.UsuarioManager;

/*
 * @author José Alvarez y Belén Desvars
 */

public class Usuario {
    private int id_usuario;
    private String nombre;
    private String apellido;
    private String tipoUsuario;
    private String aliasUsuario;
    private String pass;
    

    public Usuario(int id_usuario, String nombre, String apellido, String tipoUsuario, String aliasUsuario, String pass) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoUsuario = tipoUsuario;
        this.aliasUsuario = aliasUsuario;
        this.pass = pass;
    }
    
    public Usuario() {
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getaliasUsuario(){
        return aliasUsuario;
    }
    
    public void setaliasUsuario(String aliasUsuario){
        this.aliasUsuario = aliasUsuario;
    }
    
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        UsuarioManager um = new UsuarioManager();
        Usuario u = new Usuario();
        try {
            u = um.getUsuarioById(id_usuario);
        } catch (Exception ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Señor/ra: " + u.getNombre().toUpperCase() + "," + u.getApellido().toUpperCase();
    }
    
}
