/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.IntendenteDAO;
import Modelo.Intendente;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amiss
 */
public class IntendenteDAOImpl extends BaseDAOOracle implements IntendenteDAO {
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public IntendenteDAOImpl() throws Exception {
    }

    /**
     * Agrega un nuevo intendente a la BD.
     * @param intendenteAux Nuevo intendente a agregar.
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean agregarIntendente(Intendente intendenteAux) throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }

    /**
     * Modifica los datos de un intendente con los de un parametro
     * @param intendenteAux nuevos datos del intendente con el mismo id
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarIntendente(Intendente intendenteAux) throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }

    /**
     * Obtiene de oracle la lista de ids que tiene asignados(habitats).
     * @param idIntendente idBuscado
     * @return lista de ids de habitats
     * @throws Exception 
     */
    @Override
    public List<Integer> obtenerIdsHabitats(int idIntendente) throws Exception {
        List<Integer> idsHabitats = new ArrayList<>();
        
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return idsHabitats;
    }

    /**
     * Obtiene un intendente segun su id.
     * @param id id del intendente a buscar
     * @return Intendente
     * @throws Exception 
     */
    @Override
    public Intendente obtenerIntendente(int id) throws Exception {
        Intendente intendenteAux = new Intendente();
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        return intendenteAux;
    }

    /**
     * Recorre todos los intendentes y aquellos que en su lista idHabitats tengan el parametro,
     * se les eliminara ese valor.
     * @param idHabitat id del habitat que se elimino
     * @return true exito, false caso contrario.
     * @throws Exception 
     */
    @Override
    public boolean eliminarIdHabitat(int idHabitat) throws Exception {
        try{
            
            return true;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
