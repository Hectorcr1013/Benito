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
 *
 * @author Hector
 */
public class ProveedorDAO {
    
    /**
     * 
     * @return 
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
     * 
     * @param clave
     * @param nombre
     * @param direccion
     * @param telefono
     * @param correo
     * @return 
     */
    public static boolean guardar(String clave, String nombre, String direccion, String telefono, String correo){
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
            
            session.save(p);
            
            session.getTransaction().commit();
            
            resultado = p.getId() != 0;
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
     * 
     * @param id
     * @param clave
     * @param nombre
     * @param direccion
     * @param telefono
     * @param correo
     * @return 
     */
    public static boolean editar(int id, String clave, String nombre, String direccion, String telefono, String correo){
        boolean resultado = false;
        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            
            Proveedor proveedor = obtenerPorId(id);
            if(proveedor != null){
                proveedor.setClave(clave);
                proveedor.setNombre(nombre);
                proveedor.setDireccion(direccion);
                proveedor.setTelefono(telefono);
                proveedor.setCorreo(correo);
                
                session.saveOrUpdate(proveedor);              
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
