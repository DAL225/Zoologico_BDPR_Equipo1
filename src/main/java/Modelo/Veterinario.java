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
public class Veterinario extends Empleado{
    private List<Integer> idsAnimales;
    private List<String> especialidades;

    /**
     * Constructor de recuperacion de la BD, pues se conocen todos los datos en la BD.
     * 
     * @param especialidades especialidades con las que cuenta el veterinario
     * @param idsAnimales lista de ids de animales que tiene a su cargo el cuidador
     * @param id Identificador unico del empleado
     * @param nombre nombre personal del cuidador
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el cuidador
     */
    public Veterinario(List<String> especialidades, List<Integer> idsAnimales, int id, String nombre, String usuario, String password, List<Turno> turnos) {
        super(id, nombre, usuario, password, turnos);
        this.especialidades = especialidades;
        this.idsAnimales = idsAnimales;
    }
    
    /**
     * Constructor de insercion a la BD, pues el id se asignara en la BD.
     * 
     * @param especialidades especialidades con las que cuenta el veterinario
     * @param idsAnimales lista de ids de animales que tiene a su cargo el cuidador
     * @param nombre nombre personal del cuidador
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el cuidador
     */
    public Veterinario(List<String> especialidades, List<Integer> idsAnimales, String nombre, String usuario, String password, List<Turno> turnos) {
        super(nombre, usuario, password, turnos);
        this.especialidades = especialidades;
        this.idsAnimales = idsAnimales;
    }
    
    /**
     * Constructor vacio por defecto.
     */
    public Veterinario() {
        this.especialidades = new ArrayList<>();
        this.idsAnimales = new ArrayList<>();
    }

    public List<Integer> getIdsAnimales() {
        return idsAnimales;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public void setIdsAnimales(List<Integer> idsAnimales) {
        this.idsAnimales = idsAnimales;
    }

    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }
    
    
}
