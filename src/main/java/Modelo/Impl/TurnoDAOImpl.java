/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.TurnoDAO;
import Modelo.Turno;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amiss
 */
public class TurnoDAOImpl extends BaseDAOOracle implements TurnoDAO {
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public TurnoDAOImpl() throws Exception {
    }

    @Override
    public List<Turno> obtenerTodosTurnos() throws Exception {
        List<Turno> listaTurnos = new ArrayList<>();
        
        //logica find/mongo

        return listaTurnos;
    }
    
    /**
     * Ontiene un objeto Turno segun su id.
     * @param id id del Turno a buscar
     * @return Turno
     * @throws Exception 
     */
    @Override
    public Turno obtenerTurno(int id) throws Exception{
        Turno turnoAux = new Turno();
        //logica oracle
        return turnoAux;
    }
    /**
     * Modifica los datos de un turno con los de un parametro
     * @param turno nuevos datos del turno con el mismo id
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarTurno(Turno turno) throws Exception{
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }
    
    /**
     * Elimina un turno segun su id.
     * @param idTurno id del truno a eliminar
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean eliminarTurno(int idTurno) throws Exception{
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }

    /**
     * Agrega un nuevo turno a la BD.
     * @param turnoAux Nuevo turno a agregar.
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean agregarTurno(Turno turnoAux) throws Exception {
        try{
            
            
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
        
        
        return false;
    }
    
}
