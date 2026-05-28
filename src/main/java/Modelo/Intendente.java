/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amiss
 */
public class Intendente extends Empleado{
    private List<Integer> idsHabitats;
    
    /**
     * Constructor de recuperacion de la BD, pues se conocen todos los datos en la BD.
     * 
     * @param idsHabitats lista de ids de habitats que tiene a su cargo el intendente
     * @param id Identificador unico del empleado
     * @param nombre nombre personal del cuidador
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el cuidador
     */
    public Intendente(List<Integer> idsHabitats, int id, String nombre, String usuario, String password, List<Turno> turnos) {
        super(id, nombre, usuario, password, turnos);
        this.idsHabitats = idsHabitats;
    }
    
    /**
     * Constructor de insercion a la BD, pues el id se asignara en la BD.
     * 
     * @param idsHabitats lista de ids de habitats que tiene a su cargo el intendente
     * @param nombre nombre personal del cuidador
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el cuidador
     */
    public Intendente(List<Integer> idsHabitats, String nombre, String usuario, String password, List<Turno> turnos) {
        super(nombre, usuario, password, turnos);
        this.idsHabitats = idsHabitats;
    }
    
    /**
     * Constructor vacio por defecto.
     */
    public Intendente() {
        this.idsHabitats = new ArrayList<>();
    }
}
