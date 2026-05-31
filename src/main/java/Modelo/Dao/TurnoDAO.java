/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.Dao;

import Modelo.Turno;
import java.util.List;

/**
 *
 * @author amiss
 */
public interface TurnoDAO {
    
    List<Turno> obtenerTodosTurnos() throws Exception;
    
    Turno obtenerTurno(int id) throws Exception;
    
    boolean modificarDatos(Turno turno) throws Exception;
    
    boolean eliminarTurno(int idTurno) throws Exception;
}
