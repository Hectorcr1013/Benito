/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.benito.persistencias;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import mx.itson.benito.entidades.Articulo;
import mx.itson.benito.entidades.Proveedor;
import mx.itson.benito.utilerias.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Esta clase se guardan los metodos que se usan para el enlace entre
 * hibernate y mysql
 * @author Hector
 */
public class ArticuloDAO {
    
    /**
     * En este metodo se obtiene la lista de articulos
     * @return articulos
     */
    public static List<Articulo> obtenerTodos(){
        List<Articulo> articulos = new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            CriteriaQuery<Articulo> criteriaQuery = 
                    session.getCriteriaBuilder().createQuery(Articulo.class);
            criteriaQuery.from(Articulo.class);
            
            articulos = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return articulos;
    }
    
    /**
     * En este metodo se obtiene el id de un articulo
     * @param id El id de un articulo
     * @return articulo
     */
    public static Articulo obtenerPorId(int id){
        Articulo articulo = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            articulo = session.get(Articulo.class, id);
        } catch (HibernateException ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return articulo;
    }
    
    /**
     * Guarda en la base de datos un registro de un articulo
     * @param clave La clave del articulo
     * @param nombre El nombre del articulo
     * @param precio El precio del articulo
     * @param proveedor El proveedor del articulo
     * @return resultado
     */
    public static boolean guardar(String clave, String nombre, double precio, Proveedor proveedor){
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            Articulo a = new Articulo();
            a.setClave(clave);
            a.setNombre(nombre);
            a.setPrecio(precio);
            a.setProveedores(proveedor);
            
            session.save(a);
            
            session.getTransaction().commit();
            
            resultado = a.getId() != 0;
        } catch (Exception ex) {
            System.err.println("Ocurrio un error" + ex.getMessage());
        }
        return resultado;
    }
    
    /**
     * Se edita en la base de datos un articulo
     * @param id El id del articulo
     * @param clave La clave del articulo
     * @param nombre El nombre del articulo
     * @param precio El precio del articulo
     * @param proveedor El proveedor del articulo
     * @return resultado
     */
    public static boolean editar(int id, String clave, String nombre, double precio, Proveedor proveedor){
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            Articulo articulo = obtenerPorId(id);
            if(articulo != null){
                articulo.setClave(clave);
                articulo.setNombre(nombre);
                articulo.setPrecio(precio);
                articulo.setProveedores(proveedor);
                
                session.saveOrUpdate(articulo);              
                session.getTransaction().commit();
                resultado = true;
            }
        } catch (HibernateException ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return resultado;
    }
    
    /**
     * Elimina de la base de datos un registro de un articulo
     * @param id El id del articulo
     * @return resultado
     */
    public static boolean eliminar(int id){
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            Articulo articulo = obtenerPorId(id);
            articulo.getId();
            
            if(articulo != null){
                session.delete(articulo);
                HibernateUtil.getSessionFactory().getCurrentSession().delete(articulo);
                session.getTransaction().commit();
                resultado = true;
            }
        } catch (HibernateException ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return resultado;
    }
    
}
