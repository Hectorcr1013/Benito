/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.benito.entidades;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Hector
 */
@Entity
@Table()
public class Relacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //Cardinalidad de N:N
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "rel_articulo_proveedor",
            joinColumns = {@JoinColumn(name = "idProveedor")},
            inverseJoinColumns = {@JoinColumn(name = "idArticulo")})
    private List<Articulo> articulos;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the articulos
     */
    public List<Articulo> getArticulos() {
        return articulos;
    }

    /**
     * @param articulos the articulos to set
     */
    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }
    
}
