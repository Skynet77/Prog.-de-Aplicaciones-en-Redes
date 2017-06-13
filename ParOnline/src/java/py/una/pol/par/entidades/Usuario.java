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
 * @author José Alvarez
 */

public class Usuario {
    private int id_usuario;
    private String nombre;
    private String apellido;
    private int tipo_usuario; //0 Admin, 1 Usuario normal 
    private String login_name;
    private String contrasenha;
    

    public Usuario(int id_usuario, String nombre, String apellido, int tipo_usuario, String login_name, String pass) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipo_usuario = tipo_usuario;
        this.login_name = login_name;
        this.contrasenha = pass;
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

    public int getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(int tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getlogin_name(){
        return login_name;
    }
    
    public void setlogin_name(String login_name){
        this.login_name = login_name;
    }
    
    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
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
        return "Cliente: " + u.getNombre().toUpperCase() + "," + u.getApellido().toUpperCase();
    }
    
}
