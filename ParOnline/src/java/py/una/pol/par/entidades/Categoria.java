/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.par.entidades;

/*
 * @author José Alvarez
 */

public class Categoria {
    private int id_categoria;
    private String descripcion;

    public Categoria() {
    }

    public Categoria(int id_categoria, String descripcion) {
        this.id_categoria = id_categoria;
        this.descripcion = descripcion;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Categoria{" + "id_categoria=" + id_categoria + ", descripcion=" + descripcion + '}';
    }
}
