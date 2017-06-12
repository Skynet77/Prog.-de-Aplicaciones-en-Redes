/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.una.pol.par.entidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
 * @author José Alvarez
 */

public class TransaccionCabecera {
    private int id_transaccion;
    private Usuario usuario;
    private ArrayList<TransaccionDetalles> contenido;
    private String fecha;
    private int total_a_pagar;
    private String direccion;
    private String forma_pago;
    private int nro_tarjeta;
    private String estado;

    public TransaccionCabecera() {
    }

    public TransaccionCabecera(int id_transaccion, Usuario usuario, ArrayList<TransaccionDetalles> contenido, String fecha, String estado) {
        this.id_transaccion = id_transaccion;
        this.usuario = usuario;
        this.contenido = contenido;
        this.fecha = fecha;
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy / hh:mm:ss");
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        this.fecha = formateador.format(sqlDate);;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTotal_a_pagar() {
        return total_a_pagar;
    }

    public void setTotal_a_pagar(int total_a_pagar) {
        this.total_a_pagar = total_a_pagar;
    }

    public ArrayList<TransaccionDetalles> getContenido() {
        return contenido;
    }

    public void setContenido(ArrayList<TransaccionDetalles> contenido) {
        this.contenido = contenido;
    }

    public int getId_transaccion() {
        return id_transaccion;
    }

    public void setId_transaccion(int id_transaccion) {
        this.id_transaccion = id_transaccion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario cliente) {
        this.usuario = cliente;
    }
    
    public int getNro_tarjeta(){
        return nro_tarjeta;
    }
    
    public void setNro_tarjeta(int nro_tarjeta){
        this.nro_tarjeta = nro_tarjeta;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
