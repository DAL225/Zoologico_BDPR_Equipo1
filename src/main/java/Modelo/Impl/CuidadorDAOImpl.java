/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Cuidador;
import Modelo.Dao.CuidadorDAO;

/**
 *
 * @author amiss
 */
public class CuidadorDAOImpl extends BaseDAOOracle implements CuidadorDAO {
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public CuidadorDAOImpl() throws Exception {
    }

    /**
     * Agrega un nuevo cuidador al sistema.
     * @param cuidadorAux cuidador con los datos a almacenar
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean agregarCuidador(Cuidador cuidadorAux) throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }

    /**
     * Modifica los datos de un cuidador, recibiendo los nuevos datos en un objeto 
     * con el id donde se realizaran los cambios.
     * @param cuidadorAux datos a cambiar.
     * @return true exito, false caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarCuidador(Cuidador cuidadorAux) throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }

    /**
     * Obtiene un cuidador por su id.
     * @param id id requerido
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public Cuidador obtenerCuidador(int id) throws Exception {
        Cuidador cuidadorAux = new Cuidador();
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        return cuidadorAux;
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
