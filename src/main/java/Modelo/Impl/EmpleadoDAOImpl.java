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

    /**
     * Obtiene todos los empleados del sistema
     * @return lista de empleados
     * @throws Exception 
     */
    @Override
    public List<Empleado> obtenerTodosEmpleados() throws Exception {
        
        List<Empleado> listaEmpleados = new ArrayList<>();
        
        //logica find/mongo

        return listaEmpleados;
    }

    /**
     * Elimina un empleado con cierto id.
     * @param idEmpleado id del empleado a eliminar
     * @return true exito, false en caso contrario
     * @throws Exception 
     */
    @Override
    public boolean eliminarEmpleado(int idEmpleado) throws Exception {
        //delete
        return true;
    }
    
    /**
     * Obtiene un empleado segun su id.
     * @param id id del empleado a buscar
     * @return Empleado con datos
     * @throws Exception 
     */
    @Override
    public Empleado obtenerEmpleado(int id) throws Exception{
        Empleado empleadoAux = new Empleado();
        
        //logica, obtiene con el instance of y luego devuelve el objeto
        //asignandolo a la variable empleadoAux
        
        return empleadoAux;
    }
    
    /**
     * Valida que los datos existan en la bd.
     * de cierta forma es parecido a obtener empleado, pero esta vez por 
     * usuario y password sean iguales
     * @param usuario usuario del empleado
     * @param pass password del empleado
     * @return Empleado con datos
     * @throws Exception 
     */
    @Override
    public Empleado validCredentials(String usuario, String pass) throws Exception{
        Empleado empleadoAux = new Empleado();
        
        //logica, valida en bd y obtiene el tipo correspondiente y lo construye
        
        return empleadoAux;
    }
    
    /**
     * Modifica los datos de un empleado
     * @param empleadoAux empleado con los datos a modificar.
     * @return true exito, false caso contrario
     * @throws Exception 
     */
    @Override
    public boolean modificarDatos(Empleado empleadoAux) throws Exception{
        
        return true;
    }

    /**
     * Obtiene el tipo de empleado que es segun el id
     * @param id id del empleado
     * @return el tipo, o null en caso de no encontrarlo
     * @throws Exception 
     */
    @Override
    public String obtenerTipoEmpleado(int id) throws Exception {
        String tipo = null;
        try{//se condulta y se modifica el tipo
            
        }catch(Exception e){
            
        }
        return tipo;
    }
}