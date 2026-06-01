/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.Dao;

import Modelo.Intendente;
import java.util.List;

/**
 *
 * @author amiss
 */
public interface IntendenteDAO {
    
    Intendente obtenerIntendente(int id) throws Exception;
    
    boolean agregarIntendente(Intendente intendenteAux) throws Exception;
    
    boolean modificarIntendente(Intendente intendenteAux) throws Exception;
    
    List<Integer> obtenerIdsHabitats(int idIntendente) throws Exception;
    
    boolean eliminarIdHabitat(int idHabitat) throws Exception;
}
