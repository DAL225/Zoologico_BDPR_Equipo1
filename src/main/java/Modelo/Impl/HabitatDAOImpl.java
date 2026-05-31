/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

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

    @Override
    public Habitat obtenerHabitat(int id) throws Exception {
        Habitat habitat = null;
        
        //Logica obtencion habitat/mongo
        
        return habitat;
    }
    
    @Override
    public List<Habitat> obtenerHabitats(List<Integer> ids) throws Exception {

        List<Habitat> habitats = new ArrayList<>();
        
        //logica mongo find where

        return habitats;
    }
    
    @Override
    public List<Habitat> obtenerTodosHabitats() throws Exception {

        List<Habitat> habitats = new ArrayList<>();

        //logica mongo find
        
        return habitats;
    }

    @Override
    public Integer obtenerIdDisponible() throws Exception {
        //aqui algo de orden descending en _id asi creo obtener el max y sumar 1
        return 0;
    }
}
