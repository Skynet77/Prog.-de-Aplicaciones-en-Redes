/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.entidades;

/*
 * @author José Alvarez
 */

public class Producto implements Comparable<Producto>{
    private int id_producto;
    private Categoria categoria;
    private int precio_unidad;
    private String descripcion;
    private int cantidad;

    public Producto() {
    }

    public Producto(int id_produto, Categoria categoria, int precio_unidad, String descripcion, int cantidad) {
        this.id_producto = id_produto;
        this.categoria = categoria;
        this.precio_unidad = precio_unidad;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public int getId_producto() {
        return id_producto;
    }
    
    public void setId_producto(int id_produto) {
        this.id_producto = id_produto;
    }

    public Categoria get_categoria() {
        return categoria;
    }

    public void set_categoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public int getPrecio() {
        return precio_unidad;
    }

    public void setPrecio(int precio_unidad) {
        this.precio_unidad = precio_unidad;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto{" + "id_produto=" + id_producto + ", id_categoria=" +
                categoria + ", precio=" + precio_unidad + ", descripcion=" + descripcion +
                ", cantidad=" + cantidad + '}';
    }

    @Override
    public int compareTo(Producto o) {
        return descripcion.compareTo(o.getDescripcion());
    }
    
}
