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
public class ProveedorDAO {
    
    /**
     * En este metodo se obtiene la lista de los proveedores
     * @return proveedores  
     */
    public static List<Proveedor> obtenerTodos(){
        List<Proveedor> proveedores = new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            CriteriaQuery<Proveedor> criteriaQuery = 
                    session.getCriteriaBuilder().createQuery(Proveedor.class);
            criteriaQuery.from(Proveedor.class);
            
            proveedores = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return proveedores;
    }
    
    /**
     * Guarda en la base de datos un registro de un proveedor
     * @param clave La clave del proveedor
     * @param nombre El nombre del proveedor
     * @param direccion La direccion del proveedor
     * @param telefono El telefono del proveedor
     * @param correo El correo del proveedor
     * @param articulos Los articulos del proveedor
     * @return 
     */
    public static boolean guardar(String clave, String nombre, String direccion, String telefono, String correo, List<Articulo> articulos){
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
            Proveedor p = new Proveedor();
            p.setClave(clave);
            p.setNombre(nombre);
            p.setDireccion(direccion);
            p.setTelefono(telefono);
            p.setCorreo(correo);
            p.setArticulos(articulos);
            
            session.save(p);
            
            session.getTransaction().commit();
            
            resultado = p.getId() != 0;
        } catch (Exception ex) {
            System.err.println("Ocurrio un error" + ex.getMessage());
        }
        return resultado;
    }
    
    /**
     * Se obtiene un proveedor por medio de su id
     * @param id El id del proveedor
     * @return proveedor
     */
    public static Proveedor obtenerPorId(int id){
        Proveedor proveedor = null;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            proveedor = session.get(Proveedor.class, id);
        } catch (HibernateException ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return proveedor;
    }
    
    /**
     * Se edita de la base de datos un registro de un proveedor
     * @param id El id del proveedor
     * @param clave La clave del proveedor
     * @param nombre El nombre del proveedor
     * @param direccion La direccion del proveedor
     * @param telefono El telefono del proveedor
     * @param correo El correo del proveedor
     * @param articulos Los articulos del proveedor
     * @return resultado
     */
    public static boolean editar(int id, String clave, String nombre, String direccion, String telefono, String correo, List<Articulo> articulos){
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            Proveedor p = obtenerPorId(id);
            if(p != null){
                p.setClave(clave);
                p.setNombre(nombre);
                p.setDireccion(direccion);
                p.setTelefono(telefono);
                p.setCorreo(correo);
                p.setArticulos(articulos);
                
                session.saveOrUpdate(p);              
                session.getTransaction().commit();
                resultado = true;
            }
        } catch (HibernateException ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return resultado;
    }
    
    /**
     * Elimina de la base un registro de un proveedor
     * @param id El id del proveedor
     * @return resultado
     */
    public static boolean eliminar(int id){
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            Proveedor proveedor = obtenerPorId(id);
            
            if(proveedor != null){
                session.delete(proveedor);
                HibernateUtil.getSessionFactory().getCurrentSession().delete(proveedor);
                session.getTransaction().commit();
                resultado = true;
            }
        } catch (HibernateException ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return resultado;
    }
    
}
