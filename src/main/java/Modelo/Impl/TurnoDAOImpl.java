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
    
    @Override
    public Turno obtenerTurno(int id) throws Exception{
        Turno turnoAux = new Turno();
        //logica oracle
        return turnoAux;
    }
    
    @Override
    public boolean modificarDatos(Turno turno) throws Exception{
        //logica oracle
        return true;
    }
    
    @Override
    public boolean eliminarTurno(int idTurno) throws Exception{
        //logica oracle
        return true;
    }
    
}
