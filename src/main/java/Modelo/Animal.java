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
public class Animal {
    private int id;
    private String nombreCientifico;
    private String especie;
    private int idHabitat;
    private String nombreComun;
    private int edad;
    private String sexo;
    private String estadoSalud;
    private List<String> recomendacionesCuidado;
    private List<String> tratamientos;

    /**
     * Constructor de la clase.
     * 
     * @param id Identificador unico de cada animal
     * @param nombreCientifico nombre cientifico unico de cada animal
     * @param especie especie del animal
     * @param idHabitat Identificador unico del habitat al que pertenece el animal
     * @param nombreComun nombre comun o propio que se le asigna al animal 
     * @param edad edad en años del animal
     * @param sexo sexo del animal
     * @param estadoSalud estado de salud actual del animal
     * @param recomendacionesCuidado lista de recomendaciones de cuidado para tomar en cuenta
     * @param tratamientos tratamientos veterinarios que recibe el animal
     */
    public Animal(int id, String nombreCientifico, String especie, int idHabitat, String nombreComun, int edad, String sexo, String estadoSalud, List<String> recomendacionesCuidado, List<String> tratamientos) {
        this.id = id;
        this.nombreCientifico = nombreCientifico;
        this.especie = especie;
        this.idHabitat = idHabitat;
        this.nombreComun = nombreComun;
        this.edad = edad;
        this.sexo = sexo;
        this.estadoSalud = estadoSalud;
        this.recomendacionesCuidado = recomendacionesCuidado;
        this.tratamientos = tratamientos;
    }

    /**
     * Constructor vacio por defecto.
     */
    public Animal() {
        this.recomendacionesCuidado = new ArrayList<>();
        this.tratamientos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public String getEspecie() {
        return especie;
    }

    public int getIdHabitat() {
        return idHabitat;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public int getEdad() {
        return edad;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public List<String> getRecomendacionesCuidado() {
        return recomendacionesCuidado;
    }

    public List<String> getTratamientos() {
        return tratamientos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setIdHabitat(int idHabitat) {
        this.idHabitat = idHabitat;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public void setRecomendacionesCuidado(List<String> recomendacionesCuidado) {
        this.recomendacionesCuidado = recomendacionesCuidado;
    }

    public void setTratamientos(List<String> tratamientos) {
        this.tratamientos = tratamientos;
    }

    @Override
    public String toString() {
        return "id=" + id + 
                ",\nnombreCientifico=" + nombreCientifico + 
                ",\nespecie=" + especie + 
                ",\nidHabitat=" + idHabitat + 
                ",\nnombreComun=" + nombreComun + 
                ",\nedad=" + edad + 
                ",\nsexo=" + sexo + 
                ",\nestadoSalud=" + estadoSalud;
    }
}
