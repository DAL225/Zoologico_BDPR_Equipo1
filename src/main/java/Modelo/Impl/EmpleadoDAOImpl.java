/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.EmpleadoDAO;
import Modelo.Empleado;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amiss
 */
public class EmpleadoDAOImpl  extends BaseDAOOracle implements EmpleadoDAO {
    
    /**
     * Constructor de la clase
     * @throws Exception Posible Excepcion
     */
    public EmpleadoDAOImpl() throws Exception {
    }

    @Override
    public List<Empleado> obtenerTodosEmpleados() throws Exception {
        
        List<Empleado> listaEmpleados = new ArrayList<>();
        
        //logica find/mongo

        return listaEmpleados;
    }

    @Override
    public boolean eliminarEmpleado(int idEmpleado) throws Exception {
        //delete
        return true;
    }
    
    public Empleado obtenerEmpleado(int id) throws Exception{
        Empleado empleadoAux = new Empleado();
        
        //logica, obtiene con el instance of y luego devuelve el objeto
        //asignandolo a la variable empleadoAux
        
        return empleadoAux;
    }
    
    @Override
    public Empleado validCredentials(String usuario, String pass) throws Exception{
        Empleado empleadoAux = new Empleado();
        
        //logica, valida en bd y obtiene el tipo correspondiente y lo construye
        
        return empleadoAux;
    }
    
    public boolean modificarDatos(Empleado empleadoAux) throws Exception{
        
        return true;
    }
}