/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.entidades;

/*
 * @author José Alvarez y Belén Desvars
 */

public class Producto implements Comparable<Producto>{
    private int id_producto;
    private Categoria categoria;
    private int precio;
    private String descripcion;

    public Producto() {
    }

    public Producto(int id_produto, Categoria categoria, int precio, String descripcion) {
        this.id_producto = id_produto;
        this.categoria = categoria;
        this.precio = precio;
        this.descripcion = descripcion;
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
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Producto{" + "id_produto=" + id_producto + ", id_categoria=" + categoria + ", precio=" + precio + ", descripcion=" + descripcion + '}';
    }

    @Override
    public int compareTo(Producto o) {
        return descripcion.compareTo(o.getDescripcion());
    }
    
}
