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
public class Cuidador extends Empleado {
    private List<Integer> idsAnimales;

    /**
     * Constructor de recuperacion de la BD, pues se conocen todos los datos en la BD.
     * 
     * @param idsAnimales lista de ids de animales que tiene a su cargo el cuidador
     * @param id Identificador unico del empleado
     * @param nombre nombre personal del cuidador
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el cuidador
     */
    public Cuidador(List<Integer> idsAnimales, int id, String nombre, String usuario, String password, List<Turno> turnos) {
        super(id, nombre, usuario, password, turnos);
        this.idsAnimales = idsAnimales;
    }
    
    /**
     * Constructor de insercion a la BD, pues el id se asignara en la BD.
     * 
     * @param idsAnimales lista de ids de animales que tiene a su cargo el cuidador
     * @param nombre nombre personal del cuidador
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el cuidador
     */
    public Cuidador(List<Integer> idsAnimales, String nombre, String usuario, String password, List<Turno> turnos) {
        super(nombre, usuario, password, turnos);
        this.idsAnimales = idsAnimales;
    }
    
    /**
     * Constructor vacio por defecto.
     */
    public Cuidador() {
        this.idsAnimales = new ArrayList<>();
    }

    public List<Integer> getIdsAnimales() {
        return idsAnimales;
    }

    public void setIdsAnimales(List<Integer> idsAnimales) {
        this.idsAnimales = idsAnimales;
    }
    
    
}
