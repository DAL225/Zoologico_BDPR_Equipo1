/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Administrador;
import Modelo.Dao.AdministradorDAO;

/**
 *
 * @author amiss
 */
public class AdministradorDAOImpl extends BaseDAOOracle implements AdministradorDAO {
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public AdministradorDAOImpl() throws Exception {
    }

    @Override
    public boolean agregarAdministardor(Administrador adminAux) throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }

    @Override
    public boolean modificarAdministardor(Administrador adminAux) throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }

    @Override
    public Administrador obtenerAdministrador(int id) throws Exception {
        Administrador administradorAux = new Administrador();
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        return administradorAux;
    }
}
