/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.Dao;

import Modelo.Cuidador;

/**
 *
 * @author amiss
 */
public interface CuidadorDAO {
    
    Cuidador obtenerCuidador(int id) throws Exception;
    
    boolean agregarCuidador(Cuidador cuidadorAux) throws Exception;
    
    boolean modificarCuidador(Cuidador cuidadorAux) throws Exception;
    
    boolean eliminarIdAnimal(int idAnimal) throws Exception;
}
