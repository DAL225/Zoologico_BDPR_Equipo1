/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amiss
 */
public class AnimalDAOImpl extends BaseDAOMongo implements AnimalDAO{
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public AnimalDAOImpl() throws Exception {
    }
    
    /**
     * Obtiene un animal por su id.
     * @param id id de animal buscado
     * @return el animal en cuestion, null si no se encontro
     * @throws Exception 
     */
    @Override
    public Animal obtenerAnimal(int id) throws Exception {
        Animal animal = null;
        
        //Logica obtencion habitat/mongo
        
        return animal;
    }
    
    /**
     * Obtiene una lista de animales, que contengan el id de cada elemento de la lista.
     * Un cuidador/veterinario tienen como atributo una lista de ids,
     * este metodo busca que cuando ellos accedan se cargue en automatico su lista de animales,
     * de acuerdo a la lista de isd como parametro.
     * @param ids Lista de ids buscados
     * @return Lista de animales, ya sea con contenido o vacia, si no se encontro ninguno
     * @throws Exception 
     */
    @Override
    public List<Animal> obtenerAnimales(List<Integer> ids) throws Exception {

        List<Animal> listaAnimales = new ArrayList<>();
        
        //logica find/mongo where

        return listaAnimales;
    }
    
    /**
     * Obtiene todos los animales almacenados sin excepcion.
     * @return Lista de animales, ya sea con contenido o vacia, si no se encontro ninguno
     * @throws Exception 
     */
    @Override
    public List<Animal> obtenerTodosAnimales() throws Exception {

        List<Animal> listaAnimales = new ArrayList<>();
        
        //logica find/mongo

        return listaAnimales;
    }

    /**
     * Obtiene el id maximo dentro de la bd.
     * La idea es usar 'sort(descending("_id")).first()'
     * que se cree obtiene el maximo id, luego sumarle 1, y regresar eso como valor.
     * @return el id disponible
     * @throws Exception 
     */
    @Override
    public Integer obtenerIdDisponible() throws Exception {
        //aqui algo de orden descending en _id asi creo obtener el max y sumar 1
        return 0;
    }
    
    /**
     * Modifica los datos de un animal pasando un parametro con los datos a modificar
     * y el id del cual se modificara.
     * @param animalAux animal con los nuevos datos pero el id requerido
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarDatos(Animal animalAux) throws Exception{
        //update cada dato
        return true;
    }
    
    /**
     * Eliminar un animal por su id.
     * @param idAnimal id del animal requerido
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean eliminarAnimal(int idAnimal) throws Exception{
        //delete
        //tambien debe buscar en todos los cuidadores y veterinarios quin tenga ese id y borrarlo.
        return true;
    }
    
    /**
     * Se modifica la lista de tratamientos de un animal.
     * el comando set reemplaza lo actual por lo nuevo
     * @param idAnimal id del animal a modificar
     * @param recomendaciones nuevas recomendaciones
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarRecomendaciones(int idAnimal, List<String> recomendaciones) throws Exception{
        
        return true;
    }
    
    /**
     * Se modifica la lista de recomendaciones de un animal.
     * el comando set reemplaza lo actual por lo nuevo
     * @param idAnimal id del animal a modificar
     * @param tratamientos nuevas recomendaciones
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarTratamientos(int idAnimal, List<String> tratamientos) throws Exception{
        
        return true;
    }

    /**
     * Cambia el valor idHabitat del animal a 0.Se usa cuando se elimina un animal, para mantener consistencia.
     * La idea es que se recorran todos los animales y donde el valor de idHabitat sea igual al parametro
     * cambiarlo a 0
     * @param idHabitat id del habitat que se eliminara del
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean desasignarHabitat(int idHabitat) throws Exception {
        return false;
    }
}
