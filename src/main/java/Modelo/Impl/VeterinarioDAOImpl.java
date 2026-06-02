/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.VeterinarioDAO;
import Modelo.Veterinario;

/**
 *
 * @author amiss
 */
public class VeterinarioDAOImpl  extends BaseDAOOracle implements VeterinarioDAO {
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public VeterinarioDAOImpl() throws Exception {
    }

    /**
     * Agrega un nuevo veterinario a la BD.
     * @param veterinarioAux Nuevo veterinario a agregar.
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean agregarVeterinario(Veterinario veterinarioAux) throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }

    /**
     * Modifica los datos de un veterinario con los de un parametro
     * @param veterinarioAux nuevos datos del veterinario con el mismo id
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarVeterinario(Veterinario veterinarioAux) throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }

    /**
     * Obtiene un veterinario segun su id.
     * @param id id del veterinario a buscar
     * @return Veterinario
     * @throws Exception 
     */
    @Override
    public Veterinario obtenerVeterinario(int id) throws Exception {
        Veterinario veterinarioAux = new Veterinario();
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        return veterinarioAux;
    }

    /**
     * Recorre todos los cuidadores y aquellos que en su lista idAnimales tengan el parametro
     * se les eliminara ese valor.
     * @param idAnimal id del animal que se elimino
     * @return true exito, false caso contrario.
     * @throws Exception 
     */
    @Override
    public boolean eliminarIdAnimal(int idAnimal) throws Exception {
        try{
            
            return true;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
