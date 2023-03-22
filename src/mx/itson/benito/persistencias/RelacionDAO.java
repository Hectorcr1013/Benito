/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.benito.persistencias;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import mx.itson.benito.entidades.Relacion;
import mx.itson.benito.utilerias.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author Hector
 */
public class RelacionDAO {
    
    /**
     * 
     * @return 
     */
    public static List<Relacion> obtenerTodos(){
        List<Relacion> relacion = new ArrayList<>();
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            CriteriaQuery<Relacion> criteriaQuery = 
                    session.getCriteriaBuilder().createQuery(Relacion.class);
            criteriaQuery.from(Relacion.class);
            
            relacion = session.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex) {
            System.err.println("Ocurrio un error: " + ex.getMessage());
        }
        return relacion;
    }
    
}
