/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Dao;

import Modelo.Administrador;

/**
 *
 * @author amiss
 */
public interface AdministradorDAO {
    
    Administrador obtenerAdministrador(int id) throws Exception;
    
    boolean agregarAdministardor(Administrador adminAux) throws Exception;
    
    boolean modificarAdministardor(Administrador adminAux) throws Exception;
}
