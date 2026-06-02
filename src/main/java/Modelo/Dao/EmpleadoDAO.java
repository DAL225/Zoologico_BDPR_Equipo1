/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Dao;

import Modelo.Empleado;
import java.util.List;

/**
 *
 * @author amiss
 */
public interface EmpleadoDAO {
    
    List<Empleado> obtenerTodosEmpleados() throws Exception;
    
    boolean eliminarEmpleado(int idEmpleado) throws Exception;
    
    Empleado obtenerEmpleado(int id) throws Exception;
    
    Empleado validCredentials(String usuario, String pass) throws Exception;
    
    boolean modificarDatos(Empleado empleadoAux) throws Exception;
    
    String obtenerTipoEmpleado(int id) throws Exception;
    
}
