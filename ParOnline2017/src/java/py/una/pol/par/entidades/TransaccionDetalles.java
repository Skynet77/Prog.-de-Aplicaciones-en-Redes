/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.una.pol.par.entidades;

/*
 * @author José Alvarez
 */

public class TransaccionDetalles {
    private TransaccionCabecera carrito;
    private Producto producto;
    private int cantidad;
    private int subtotal;
    private int id_contenido;

    public TransaccionDetalles() {
    }

    public TransaccionDetalles(TransaccionCabecera carrito, Producto producto, int cantidad) {
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getId_contenido() {
        return id_contenido;
    }

    public void setId_contenido(int id_contenido) {
        this.id_contenido = id_contenido;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public TransaccionCabecera getCarrito() {
        return carrito;
    }

    public void setCarrito(TransaccionCabecera carrito) {
        this.carrito = carrito;
    }

    public Producto getProductos() {
        return producto;
    }

    public void setProductos(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
