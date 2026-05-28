/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author amiss
 */
public class Turno {
    private int id;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    /**
     * Constructor de recuperacion de la BD, pues se conocen todos los datos en la BD.
     * 
     * @param id Identificador Unico del turno laboral
     * @param fecha fecha en que aplica el turno laboral
     * @param horaInicio hora en que comienza el turno laboral
     * @param horaFin hora en que termina el turno laboral
     */
    public Turno(int id, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    /**
     * Constructor de insercion a la BD, pues el id se asignara en la BD.
     * 
     * @param fecha fecha en que aplica el turno laboral
     * @param horaInicio hora en que comienza el turno laboral
     * @param horaFin hora en que termina el turno laboral
     */
    public Turno(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    /**
     * Constructor vacio por defecto.
     */
    public Turno() {
    }

    public int getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
    
}
