/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.benito.enumeradores;

/**
 *
 * @author Hector
 */
public enum Estado {

    ABIERTO(1),
    CERRADO(2),
    CANCELADO(3);
    
    
    private int numero;
    Estado(int numero){
        this.numero = numero;
    }
    
}
