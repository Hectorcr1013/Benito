/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.benito.persistencias;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import mx.itson.benito.entidades.*;
import mx.itson.benito.utilerias.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Esta clase se guardan los metodos que se usan para el enlace entre hibernate
 * y mysql 
 * @author Hector
 */
public class OrdenCompraDAO {
    
    /**
     * En este metodo se obtiene la lista de la orden de compra
     * @return articulos
     */
    public static List<OrdenCompra> obtenerTodos() {
        List<OrdenCompra> Ordencompra = new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            CriteriaQuery<OrdenCompra> criteriaQuery
                    = session.getCriteriaBuilder().createQuery(OrdenCompra.class);
            criteriaQuery.from(OrdenCompra.class);

            Ordencompra = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex) {
            System.err.println("Ocurrio un error" + ex.getMessage());
        }
        return Ordencompra;
    }
    
    /**
     * Guarda en la base de datos un registro de una orden de compra
     * @param proveedor El proveedor de la orden de compra
     * @param folio El folio de la orden de compra
     * @param articulos El o los articulos de la orden de compra
     * @param fecha La fecha de creacion de la orden de compra
     * @param cantidad La cantidad de articulos de la orden de compra
     * @return resultado
     */
    public static boolean guardar(List<Proveedor> proveedor, String folio, List<Articulo> articulos, Date fecha, int cantidad) {
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            OrdenCompra c = new OrdenCompra();
            c.setProveedor(proveedor);
            c.setFolio(folio);
            c.setArticulos(articulos);
            c.setFecha(fecha);
            c.setCantidad(cantidad);

            session.save(c);

            session.getTransaction().commit();

            resultado = c.getId() != 0;
        } catch (Exception ex) {
            System.err.println("Ocurrio un error" + ex.getMessage());
        }
        return resultado;
    }

    /**
     * Se obtiene una orden de compra por su id
     * @param id El id de la orden de compra
     * @return oredenCompra
     */
    public static OrdenCompra obtenerPorId(int id) {
        OrdenCompra ordenCompra = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            ordenCompra = session.get(OrdenCompra.class, id);
        } catch (HibernateException ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return ordenCompra;
    }
    
    /**
     * Se edita de la base de datos un registro de una orden de compra
     * @param id El id de la orden de compra
     * @param proveedor El proveedor de la orden de compra
     * @param folio El folio de la orden de compra
     * @param articulos El articulo de la orden de compra
     * @param fecha La fecha de la orden de compra
     * @param cantidad La cantidad de articulos de la orden de compra
     * @return resultado
     */
    public static boolean editar(int id, List<Proveedor> proveedor, String folio, List<Articulo> articulos, Date fecha, int cantidad){
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            OrdenCompra c = obtenerPorId(id);
            if(c != null){
                c.setProveedor(proveedor);
                c.setFolio(folio);
                c.setArticulos(articulos);
                c.setFecha(fecha);
                c.setCantidad(cantidad);
                
                session.saveOrUpdate(c);              
                session.getTransaction().commit();
                resultado = true;
            }
        } catch (HibernateException ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return resultado;
    }
    
    /**
     * Elimina de la base de datos un registro de orden de compra
     * @param id El id de la orden de compra
     * @return resultado
     */
    public static boolean eliminar(int id){
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            OrdenCompra ordenCompra = obtenerPorId(id);
            ordenCompra.getId();
            
            if(ordenCompra != null){
                session.delete(ordenCompra);
                HibernateUtil.getSessionFactory().getCurrentSession().delete(ordenCompra);
                session.getTransaction().commit();
                resultado = true;
            }
        } catch (HibernateException ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return resultado;
    }
    
}
