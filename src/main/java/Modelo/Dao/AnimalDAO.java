/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.Dao;

import Modelo.Animal;
import java.util.List;

/**
 *
 * @author amiss
 */
public interface AnimalDAO {
    
    Animal obtenerAnimal(int id) throws Exception;
    
    List<Animal> obtenerAnimales(List<Integer> ids) throws Exception;
    
    List<Animal> obtenerTodosAnimales() throws Exception;
    
    Integer obtenerIdDisponible() throws Exception;
}
