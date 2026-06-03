/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Administrador;
import Modelo.Cuidador;
import Modelo.Dao.EmpleadoDAO;
import Modelo.Empleado;
import Modelo.Intendente;
import Modelo.Turno;
import Modelo.Veterinario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author amiss
 */
public class EmpleadoDAOImpl extends BaseDAOOracle implements EmpleadoDAO {

    /**
     * Constructor de la clase
     *
     * @throws Exception Posible Excepcion
     */
    public EmpleadoDAOImpl() throws Exception {
    }

    /**
     * Obtiene todos los empleados del sistema
     *
     * @return lista de empleados
     * @throws Exception
     */
    @Override
    public List<Empleado> obtenerTodosEmpleados() throws Exception {
        List<Empleado> empleados = new ArrayList<>();

        try (Connection con = getConexion(); CallableStatement cs = con.prepareCall(
                "{CALL sp_obtener_todos_empleados(?)}")) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                while (rs.next()) {
                    String tipo = rs.getString("tipo");
                    String strAnimales = rs.getString("animales");
                    String strEspecialidades = rs.getString("especialidades");
                    String strHabitats = rs.getString("habitats");
                    String strTurnos = rs.getString("turnos");
                    
                    Empleado empleado;

                    switch (tipo) {

                        case "ADMINISTRADOR":
                            empleado = new Administrador();
                            break;

                        case "VETERINARIO":
                            Veterinario vet = new Veterinario();

                            List<Integer> idsAnimVet = new ArrayList<>();
                            if (strAnimales != null && !strAnimales.isEmpty()) {
                                for (String id : strAnimales.split(",")) {
                                    idsAnimVet.add(Integer.parseInt(id.trim()));
                                }
                            }
                            vet.setIdsAnimales(idsAnimVet);

                            List<String> especialidades = new ArrayList<>();
                            if (strEspecialidades != null && !strEspecialidades.isEmpty()) {
                                for (String esp : strEspecialidades.split(",")) {
                                    especialidades.add(esp.trim());
                                }
                            }
                            vet.setEspecialidades(especialidades);

                            empleado = vet;
                            break;

                        case "CUIDADOR":
                            Cuidador cui = new Cuidador();

                            List<Integer> idsAnimCui = new ArrayList<>();
                            if (strAnimales != null && !strAnimales.isEmpty()) {
                                for (String id : strAnimales.split(",")) {
                                    idsAnimCui.add(Integer.parseInt(id.trim()));
                                }
                            }
                            cui.setIdsAnimales(idsAnimCui);

                            empleado = cui;
                            break;

                        case "INTENDENTE":
                            Intendente inte = new Intendente();

                            List<Integer> idsHab = new ArrayList<>();
                            if (strHabitats != null && !strHabitats.isEmpty()) {
                                for (String id : strHabitats.split(",")) {
                                    idsHab.add(Integer.parseInt(id.trim()));
                                }
                            }
                            inte.setIdsHabitats(idsHab);

                            empleado = inte;
                            break;

                        default:
                            continue;
                    }

                    empleado.setId(rs.getInt("id_emp"));
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setUsuario(rs.getString("usuario"));
                    empleado.setPassword(rs.getString("password"));

                    // ← Parsear y asignar turnos
                    List<Turno> turnos = new ArrayList<>();
                    if (strTurnos != null && !strTurnos.isEmpty()) {
                        for (String idStr : strTurnos.split(",")) {
                            Turno t = new Turno();
                            t.setId(Integer.parseInt(idStr.trim()));
                            turnos.add(t);
                        }
                    }
                    empleado.setTurnos(turnos);

                    empleados.add(empleado);
                }
            }

            return empleados;

        } catch (Exception e) {
            throw new Exception(
                    "Error al obtener todos los empleados: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina un empleado con cierto id.
     *
     * @param idEmpleado id del empleado a eliminar
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean eliminarEmpleado(int idEmpleado) throws Exception {
        try (Connection con = getConexion(); CallableStatement cs = con.prepareCall(
                "{CALL sp_eliminar_empleado(?)}")) {

            cs.setInt(1, idEmpleado);
            cs.execute();

            return true;

        } catch (Exception e) {
            throw new Exception(
                    "Error al eliminar el empleado con ID "
                    + idEmpleado + ": " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un empleado segun su id.
     *
     * @param id id del empleado a buscar
     * @return Empleado con datos
     * @throws Exception
     */
    @Override
    public Empleado obtenerEmpleado(int id) throws Exception {
        try (Connection con = getConexion(); CallableStatement cs = con.prepareCall(
                "{CALL sp_obtener_empleado(?,?)}")) {

            cs.setInt(1, id);
            cs.registerOutParameter(2, OracleTypes.CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                if (!rs.next()) {
                    return null;
                }

                String tipo = rs.getString("tipo");
                String strAnimales = rs.getString("animales");
                String strEspecialidades = rs.getString("especialidades");
                String strHabitats = rs.getString("habitats");
                String strTurnos = rs.getString("turnos");

                Empleado empleado;

                switch (tipo) {

                    case "ADMINISTRADOR":
                        empleado = new Administrador();
                        break;

                    case "VETERINARIO":
                        Veterinario vet = new Veterinario();

                        List<Integer> idsAnimVet = new ArrayList<>();
                        if (strAnimales != null && !strAnimales.isEmpty()) {
                            for (String idAnimal : strAnimales.split(",")) {
                                idsAnimVet.add(Integer.parseInt(idAnimal.trim()));
                            }
                        }
                        vet.setIdsAnimales(idsAnimVet);

                        List<String> especialidades = new ArrayList<>();
                        if (strEspecialidades != null && !strEspecialidades.isEmpty()) {
                            for (String esp : strEspecialidades.split(",")) {
                                especialidades.add(esp.trim());
                            }
                        }
                        vet.setEspecialidades(especialidades);

                        empleado = vet;
                        break;

                    case "CUIDADOR":
                        Cuidador cui = new Cuidador();

                        List<Integer> idsAnimCui = new ArrayList<>();
                        if (strAnimales != null && !strAnimales.isEmpty()) {
                            for (String idAnimal : strAnimales.split(",")) {
                                idsAnimCui.add(Integer.parseInt(idAnimal.trim()));
                            }
                        }
                        cui.setIdsAnimales(idsAnimCui);

                        empleado = cui;
                        break;

                    case "INTENDENTE":
                        Intendente inte = new Intendente();

                        List<Integer> idsHab = new ArrayList<>();
                        if (strHabitats != null && !strHabitats.isEmpty()) {
                            for (String idHab : strHabitats.split(",")) {
                                idsHab.add(Integer.parseInt(idHab.trim()));
                            }
                        }
                        inte.setIdsHabitats(idsHab);

                        empleado = inte;
                        break;

                    default:
                        return null;
                }

                empleado.setId(rs.getInt("id_emp"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setUsuario(rs.getString("usuario"));
                empleado.setPassword(rs.getString("password"));
                // ← Parsear y asignar turnos
                List<Turno> turnos = new ArrayList<>();
                if (strTurnos != null && !strTurnos.isEmpty()) {
                    for (String idStr : strTurnos.split(",")) {
                        Turno t = new Turno();
                        t.setId(Integer.parseInt(idStr.trim()));
                        turnos.add(t);
                    }
                }
                empleado.setTurnos(turnos);

                return empleado;
            }

        } catch (Exception e) {
            throw new Exception(
                    "Error al obtener el empleado con ID " + id + ": " + e.getMessage(), e);
        }
    }

    /**
     * Valida que los datos existan en la bd. de cierta forma es parecido a
     * obtener empleado, pero esta vez por usuario y password sean iguales
     *
     * @param usuario usuario del empleado
     * @param pass password del empleado
     * @return Empleado con datos
     * @throws Exception
     */
    @Override
    public Empleado validCredentials(String usuario, String pass) throws Exception {
        try (Connection con = getConexion(); CallableStatement cs = con.prepareCall(
                "{CALL sp_valid_credentials(?,?,?)}")) {

            cs.setString(1, usuario);
            cs.setString(2, pass);
            cs.registerOutParameter(3, OracleTypes.CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(3)) {

                if (!rs.next()) {
                    return null;
                }

                String tipo = rs.getString("tipo");
                String strAnimales = rs.getString("animales");
                String strEspecialidades = rs.getString("especialidades");
                String strHabitats = rs.getString("habitats");

                Empleado empleado;

                switch (tipo) {
                    case "ADMINISTRADOR":
                        empleado = new Administrador();
                        break;

                    case "VETERINARIO":
                        Veterinario vet = new Veterinario();

                        List<Integer> idsAnimVet = new ArrayList<>();
                        if (strAnimales != null && !strAnimales.isEmpty()) {
                            for (String id : strAnimales.split(",")) {
                                idsAnimVet.add(Integer.parseInt(id.trim()));
                            }
                        }
                        vet.setIdsAnimales(idsAnimVet);

                        List<String> especialidades = new ArrayList<>();
                        if (strEspecialidades != null && !strEspecialidades.isEmpty()) {
                            for (String esp : strEspecialidades.split(",")) {
                                especialidades.add(esp.trim());
                            }
                        }
                        vet.setEspecialidades(especialidades);

                        empleado = vet;
                        break;

                    case "CUIDADOR":
                        Cuidador cui = new Cuidador();

                        List<Integer> idsAnimCui = new ArrayList<>();
                        if (strAnimales != null && !strAnimales.isEmpty()) {
                            for (String id : strAnimales.split(",")) {
                                idsAnimCui.add(Integer.parseInt(id.trim()));
                            }
                        }
                        cui.setIdsAnimales(idsAnimCui);

                        empleado = cui;
                        break;

                    case "INTENDENTE":
                        Intendente inte = new Intendente();

                        List<Integer> idsHabitats = new ArrayList<>();
                        if (strHabitats != null && !strHabitats.isEmpty()) {
                            for (String id : strHabitats.split(",")) {
                                idsHabitats.add(Integer.parseInt(id.trim()));
                            }
                        }
                        inte.setIdsHabitats(idsHabitats);

                        empleado = inte;
                        break;

                    default:
                        return null;
                }

                empleado.setId(rs.getInt("id_emp"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setUsuario(rs.getString("usuario"));
                empleado.setPassword(rs.getString("password"));

                return empleado;
            }

        } catch (Exception e) {
            throw new Exception(
                    "Error al validar credenciales del empleado: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene el tipo de empleado que es segun el id
     *
     * @param id id del empleado
     * @return el tipo, o null en caso de no encontrarlo
     * @throws Exception
     */
    @Override
    public String obtenerTipoEmpleado(int id) throws Exception {
        try (Connection con = getConexion(); CallableStatement cs = con.prepareCall(
                "{CALL sp_obtener_tipo_empleado(?,?)}")) {

            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.VARCHAR);

            cs.execute();

            return cs.getString(2);

        } catch (Exception e) {
            throw new Exception(
                    "Error al obtener el tipo de empleado: " + e.getMessage(), e);
        }
    }
}
