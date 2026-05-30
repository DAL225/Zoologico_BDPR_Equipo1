/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author amiss
 */
public class Habitat {
    private int id;
    private String nombre;
    private String tipo;
    private String clima;
    private String nivelLimpieza;
    private int capacidadAnimales;

    /**
     * 
     * @param id Identificador Unico de cada habitat
     * @param nombre nombre del habitat
     * @param tipo tipo de habitat
     * @param clima clima dimulado del habitat
     * @param nivelLimpieza nivel de limpieza deel habitat
     * @param capacidadAnimales capacidad maxima de animales que puede albergar el habitat
     */
    public Habitat(int id, String nombre, String tipo, String clima, String nivelLimpieza, int capacidadAnimales) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.clima = clima;
        this.nivelLimpieza = nivelLimpieza;
        this.capacidadAnimales = capacidadAnimales;
    }

    /**
     * Constructor vacio por defecto.
     */
    public Habitat() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getClima() {
        return clima;
    }

    public String getNivelLimpieza() {
        return nivelLimpieza;
    }

    public int getCapacidadAnimales() {
        return capacidadAnimales;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public void setNivelLimpieza(String nivelLimpieza) {
        this.nivelLimpieza = nivelLimpieza;
    }

    public void setCapacidadAnimales(int capacidadAnimales) {
        this.capacidadAnimales = capacidadAnimales;
    }

    @Override
    public String toString() {
        return "id=" + id + 
                ",\nnombre=" + nombre + 
                ",\ntipo=" + tipo + 
                ",\nclima=" + clima + 
                ",\nnivelLimpieza=" + nivelLimpieza + 
                ",\ncapacidadAnimales=" + capacidadAnimales;
    }
}
