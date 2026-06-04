/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.VeterinarioDAO;
import Modelo.Turno;
import Modelo.Veterinario;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

/**
 *
 * @author amiss
 */
public class VeterinarioDAOImpl extends BaseDAOOracle implements VeterinarioDAO {

    /**
     * Constructor de la clase
     *
     * @throws Exception Posible Excepcion
     */
    public VeterinarioDAOImpl() throws Exception {
    }

    /**
     * Agrega un nuevo veterinario a la BD.
     *
     * @param veterinarioAux Nuevo veterinario a agregar.
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean agregarVeterinario(Veterinario veterinarioAux) throws Exception {

        String sql = "{CALL sp_agregar_veterinario(?,?,?,?,?,?,?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setString(1, veterinarioAux.getNombre());
            cstmt.setString(2, veterinarioAux.getUsuario());
            cstmt.setString(3, veterinarioAux.getPassword());

            cstmt.registerOutParameter(4, java.sql.Types.NUMERIC);

            OracleConnection ocon = con.unwrap(OracleConnection.class);

            // TURNOS
            ArrayDescriptor descTurnos
                    = ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", ocon);

            ARRAY arrTurnos = new ARRAY(
                    descTurnos,
                    ocon,
                    veterinarioAux.getTurnos() == null
                    ? new Integer[]{}
                    : veterinarioAux.getTurnos().stream()
                            .map(Turno::getId)
                            .toArray(Integer[]::new)
            );

            cstmt.setArray(5, arrTurnos);

            // ANIMALES
            ArrayDescriptor descAnimales
                    = ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", ocon);

            ARRAY arrAnimales = new ARRAY(
                    descAnimales,
                    ocon,
                    veterinarioAux.getIdsAnimales() == null
                    ? new Integer[]{}
                    : veterinarioAux.getIdsAnimales().toArray(new Integer[0])
            );

            cstmt.setArray(6, arrAnimales);

            // ESPECIALIDADES
            ArrayDescriptor descEsp
                    = ArrayDescriptor.createDescriptor("TABLA_ESPECIALIDADES_TYP", ocon);

            ARRAY arrEsp = new ARRAY(
                    descEsp,
                    ocon,
                    veterinarioAux.getEspecialidades() == null
                    ? new String[]{}
                    : veterinarioAux.getEspecialidades().toArray(new String[0])
            );

            cstmt.setArray(7, arrEsp);

            cstmt.execute();

            veterinarioAux.setId(cstmt.getInt(4));
            return true;

        } catch (Exception e) {
            throw new Exception("Error al insertar el Veterinario: " + e.getMessage(), e);
        }
    }

    /**
     * Modifica los datos de un veterinario con los de un parametro
     *
     * @param veterinarioAux nuevos datos del veterinario con el mismo id
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean modificarVeterinario(Veterinario veterinarioAux) throws Exception {

        try (Connection con = getConexion(); CallableStatement cs = con.prepareCall(
                "{CALL sp_modificar_veterinario(?,?,?,?,?,?,?)}")) {

            cs.setInt(1, veterinarioAux.getId());
            cs.setString(2, veterinarioAux.getNombre());
            cs.setString(3, veterinarioAux.getUsuario());
            cs.setString(4, veterinarioAux.getPassword());

            OracleConnection oracleCon = con.unwrap(OracleConnection.class);

            // ===================== TURNOS =====================
            Integer[] idsTurnos = (veterinarioAux.getTurnos() == null)
                    ? new Integer[0]
                    : veterinarioAux.getTurnos()
                            .stream()
                            .map(Turno::getId)
                            .toArray(Integer[]::new);

            ArrayDescriptor descTurnos
                    = ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

            ARRAY arrayTurnos = new ARRAY(descTurnos, oracleCon, idsTurnos);

            // ===================== ANIMALES =====================
            Integer[] idsAnimales = (veterinarioAux.getIdsAnimales() == null)
                    ? new Integer[0]
                    : veterinarioAux.getIdsAnimales().toArray(new Integer[0]);

            ArrayDescriptor descAnimales
                    = ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

            ARRAY arrayAnimales = new ARRAY(descAnimales, oracleCon, idsAnimales);

            // ===================== ESPECIALIDADES =====================
            String[] especialidades = (veterinarioAux.getEspecialidades() == null)
                    ? new String[0]
                    : veterinarioAux.getEspecialidades().toArray(new String[0]);

            ArrayDescriptor descEsp
                    = ArrayDescriptor.createDescriptor("TABLA_ESPECIALIDADES_TYP", oracleCon);

            ARRAY arrayEspecialidades = new ARRAY(descEsp, oracleCon, especialidades);

            // SET PARAMS
            cs.setArray(5, arrayTurnos);
            cs.setArray(6, arrayAnimales);
            cs.setArray(7, arrayEspecialidades);

            cs.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Error al modificar el veterinario: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un veterinario segun su id.
     *
     * @param id id del veterinario a buscar
     * @return Veterinario
     * @throws Exception
     */
    @Override
    public Veterinario obtenerVeterinario(int id) throws Exception {
        try (Connection con = getConexion(); CallableStatement cs = con.prepareCall(
                "{CALL sp_obtener_veterinario(?,?,?,?,?,?,?,?)}")) {

            cs.setInt(1, id);

            cs.registerOutParameter(2, Types.INTEGER);
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.registerOutParameter(5, Types.VARCHAR);

            cs.registerOutParameter(6, OracleTypes.CURSOR);
            cs.registerOutParameter(7, OracleTypes.CURSOR);
            cs.registerOutParameter(8, OracleTypes.CURSOR);

            cs.execute();

            Veterinario veterinario = new Veterinario();
            veterinario.setId(cs.getInt(2));
            veterinario.setNombre(cs.getString(3));
            veterinario.setUsuario(cs.getString(4));
            veterinario.setPassword(cs.getString(5));

            veterinario.setTurnos(new ArrayList<>());
            veterinario.setIdsAnimales(new ArrayList<>());
            veterinario.setEspecialidades(new ArrayList<>());

            try (ResultSet rsTurnos = (ResultSet) cs.getObject(6)) {
                while (rsTurnos.next()) {
                    Turno turno = new Turno();

                    turno.setId(rsTurnos.getInt("id_turno"));
                    turno.setFecha(rsTurnos.getDate("fecha").toLocalDate());

                    Timestamp inicio = rsTurnos.getTimestamp("hora_inicio");
                    if (inicio != null) {
                        turno.setHoraInicio(inicio.toLocalDateTime().toLocalTime());
                    }

                    Timestamp fin = rsTurnos.getTimestamp("hora_fin");
                    if (fin != null) {
                        turno.setHoraFin(fin.toLocalDateTime().toLocalTime());
                    }

                    veterinario.getTurnos().add(turno);
                }
            }

            try (ResultSet rsAnimales = (ResultSet) cs.getObject(7)) {
                while (rsAnimales.next()) {
                    veterinario.getIdsAnimales().add(rsAnimales.getInt(1));
                }
            }

            try (ResultSet rsEspecialidades = (ResultSet) cs.getObject(8)) {
                while (rsEspecialidades.next()) {
                    veterinario.getEspecialidades().add(rsEspecialidades.getString(1));
                }
            }

            return veterinario;

        } catch (Exception e) {
            throw new Exception("Error al obtener el veterinario: " + e.getMessage(), e);
        }
    }

    /**
     * Recorre todos los cuidadores y aquellos que en su lista idAnimales tengan
     * el parametro se les eliminara ese valor.
     *
     * @param idAnimal id del animal que se elimino
     * @return true exito, false caso contrario.
     * @throws Exception
     */
    @Override
    public boolean eliminarIdAnimal(int idAnimal) throws Exception {
        try (Connection con = getConexion(); CallableStatement cs = con.prepareCall("{CALL sp_eliminar_id_animal_veterinarios(?)}")) {

            cs.setInt(1, idAnimal);
            cs.execute();

            return true;

        } catch (Exception e) {
            throw new Exception("Error al eliminar el ID del animal en los veterinarios: " + e.getMessage(), e);
        }
    }
}
