/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada del diseño POJO de un empleado.
 * 
 * @author amiss
 */
public class Empleado {
    protected int id;
    protected String nombre;
    protected String usuario;
    protected String password;
    protected List<Turno> turnos;
    
    /**
     * Constructor de recuperacion de la BD, pues se conocen todos los datos en la BD.
     * 
     * @param id Identificador unico del empleado
     * @param nombre nombre personal del empleado
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el empleado
     */
    public Empleado(int id, String nombre, String usuario, String password, List<Turno> turnos) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.turnos = turnos;
    }
    

    /**
     * Constructor de insercion a la BD, pues el id se asignara en la BD.
     * 
     * @param nombre nombre personal del empleado
     * @param usuario usuario de acceso al sistema
     * @param password password de acceso al sistema
     * @param turnos lista de turnos que tiene asignados el empleado
     */
    public Empleado(String nombre, String usuario, String password, List<Turno> turnos) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.turnos = turnos;
    }

    /**
     * Constructor vacio por defecto.
     */
    public Empleado() {
        this.turnos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
    
    /**
     * Regresa el tipo de empleado que es esta clase.
     * @return tipo de empleado
     */
    public String getTipo(){
        return "";
    }

    
    public String toString2() {
        return "id=" + id + 
                ",\nnombre=" + nombre + 
                ",\nusuario=" + usuario + 
                ",\npassword=" + password +
                ",\ntipo=" + getTipo();
    }
}
