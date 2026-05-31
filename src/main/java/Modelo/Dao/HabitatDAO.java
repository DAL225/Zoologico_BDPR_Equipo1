/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.Dao;

import Modelo.Habitat;
import java.util.List;

/**
 *
 * @author amiss
 */
public interface HabitatDAO {
    
    Habitat obtenerHabitat(int id) throws Exception;
    
    List<Habitat> obtenerHabitats(List<Integer> ids) throws Exception;
    
    List<Habitat> obtenerTodosHabitats() throws Exception;
    
    Integer obtenerIdDisponible() throws Exception;
}
