/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;

/**
 *
 * @author amiss
 */
public class Administrador extends Empleado{

    /**
     * Constructor de recuperacion de la BD, pues se conocen todos los datos en la BD.
     * 
     * @param id Identificador unico del empleado
     * @param nombre nombre personal del empleado
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el empleado
     */
    public Administrador(int id, String nombre, String usuario, String password, List<Turno> turnos) {
        super(id, nombre, usuario, password, turnos);
    }

    /**
     * Constructor de insercion a la BD, pues el id se asignara en la BD.
     * 
     * @param nombre nombre personal del empleado
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el empleado
     */
    public Administrador(String nombre, String usuario, String password, List<Turno> turnos) {
        super(nombre, usuario, password, turnos);
    }

    /**
     * Constructor vacio por defecto.
     */
    public Administrador() {
    }
    
    
    /**
     * Regresa el tipo de empleado que es esta clase.
     * @return tipo de empleado
     */
    @Override 
    public String getTipo(){
        return "administrador";
    }

    @Override
    public String toString2(){
        return super.toString2();
    }
    
    
}
