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
}
