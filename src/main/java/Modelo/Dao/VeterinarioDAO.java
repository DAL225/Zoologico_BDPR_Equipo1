/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo.Dao;

import Modelo.Veterinario;

/**
 *
 * @author amiss
 */
public interface VeterinarioDAO {
    
    Veterinario obtenerVeterinario(int id) throws Exception;
    
    boolean agregarVeterinario(Veterinario veterinarioAux) throws Exception;
    
    boolean modificarVeterinario(Veterinario veterinarioAux) throws Exception;
    
    boolean eliminarIdAnimal(int idAnimal) throws Exception;
}
