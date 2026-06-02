/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.AnimalDAO;
import Modelo.Dao.HabitatDAO;
import Modelo.Habitat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amiss
 */
public class HabitatDAOImpl extends BaseDAOMongo implements HabitatDAO {
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public HabitatDAOImpl() throws Exception {
    }

    /**
     * Obtiene un habitat en particular segun su id, usado en modificar y eliminar.
     * @param id id buscado
     * @return
     * @throws Exception 
     */
    @Override
    public Habitat obtenerHabitat(int id) throws Exception {
        Habitat habitat = null;
        
        //Logica obtencion habitat/mongo
        
        return habitat;
    }
    
    
    /**
     * Obtiene la lista de habitats, segun la lista de ids habitat que tiene
     * un intendente.
     * @param ids lista de ids que tiene un intendente
     * @returnlista de habitats
     * @throws Exception 
     */
    @Override
    public List<Habitat> obtenerHabitats(List<Integer> ids) throws Exception {
        List<Habitat> habitats = new ArrayList<>();
        try {

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return habitats;
    }
    
    /**
     * Obtiene todos los habitats del sistema.
     * Utilizado por el admin para ver la lista de habitats general
     * @return lista de habitats
     * @throws Exception 
     */
    @Override
    public List<Habitat> obtenerTodosHabitats() throws Exception {
        List<Habitat> habitats = new ArrayList<>();
        try {

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return habitats;
    }

    /**
     * Obtiene el proximo id que se le asignara al nuevo habitat, cuando se cree
     * @return
     * @throws Exception 
     */
    @Override
    public Integer obtenerIdDisponible() throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return 0;
    }
    
    /**
     * Modifica los datos de un habitat que se pase como parametro.
     * La idea es que el parametro traiga el id a modificar
     * y aqui se modifiquen todos los datos segun ese id.
     * @param habitatAux referencia al habitat a modificar
     * @return true si se logro, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarDatos(Habitat habitatAux) throws Exception{
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }
    
    /**
     * Elimina un habitat segun su id.
     * @param idHabitat id del habitat a eliminar
     * @return
     * @throws Exception 
     */
    @Override
    public boolean eliminarHabitat(int idHabitat) throws Exception {
        try {
            
            //logica
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        //logicaOracle
        //tambien debe buscar en todos los animales quien tenga ese idHabitat y dejarlo en 0
    }

    /**
     * Agrega un habitat a la BD.
     * @param habitatAux habitat con los datos a agregar.
     * @return true exito, false caso contrario.
     * @throws Exception 
     */
    @Override
    public boolean agregarHabitat(Habitat habitatAux) throws Exception {
        try{//logica verificar si atributo es distinto de isblank, y .append, para las listas si es distinto de isEmpty
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        return false;
    }
}
