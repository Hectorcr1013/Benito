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
 *
 * @author Hector
 */
public class OrdenCompraDAO {
    
    /**
     * 
     * @return 
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
     * 
     * @param proveedor
     * @param folio
     * @param articulos
     * @param fecha
     * @param cantidad
     * @return 
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
     * 
     * @param id
     * @return 
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
     * 
     * @param id
     * @param proveedor
     * @param folio
     * @param articulos
     * @param fecha
     * @param cantidad
     * @return 
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
     * 
     * @param id
     * @return 
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
