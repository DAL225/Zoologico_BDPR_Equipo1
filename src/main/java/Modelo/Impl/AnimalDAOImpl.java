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
    
    @Override
    public Animal obtenerAnimal(int id) throws Exception {
        Animal animal = null;
        
        //Logica obtencion habitat/mongo
        
        return animal;
    }
    
    @Override
    public List<Animal> obtenerAnimales(List<Integer> ids) throws Exception {

        List<Animal> listaAnimales = new ArrayList<>();
        
        //logica find/mongo where

        return listaAnimales;
    }
    
    @Override
    public List<Animal> obtenerTodosAnimales() throws Exception {

        List<Animal> listaAnimales = new ArrayList<>();
        
        //logica find/mongo

        return listaAnimales;
    }

    @Override
    public Integer obtenerIdDisponible() throws Exception {
        //aqui algo de orden descending en _id asi creo obtener el max y sumar 1
        return 0;
    }
    
    @Override
    public boolean modificarDatos(Animal animalAux) throws Exception{
        //update cada dato
        return true;
    }
    
     @Override
    public boolean eliminarAnimal(int idAnimal) throws Exception{
        //delete
        //tambien debe buscar en todos los cuidadores y veterinarios quin tenga ese id y borrarlo.
        return true;
    }
    
    @Override
    public boolean modificarRecomendaciones(int idAnimal, List<String> recomendaciones) throws Exception{
        
        return true;
    }
    
    @Override
    public boolean modificarTratamientos(int idAnimal, List<String> tratamientos) throws Exception{
        
        return true;
    }
}
