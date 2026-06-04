/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.IntendenteDAO;
import Modelo.Intendente;
import Modelo.Turno;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

/**
 *
 * @author amiss
 */
public class IntendenteDAOImpl extends BaseDAOOracle implements IntendenteDAO {

    /**
     * Constructor de la clase
     *
     * @throws Exception Posible Excepcion
     */
    public IntendenteDAOImpl() throws Exception {
    }

    /**
     * Agrega un nuevo intendente a la BD.
     *
     * @param intendenteAux Nuevo intendente a agregar.
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
public boolean agregarIntendente(Intendente intendenteAux) throws Exception {

    String sql = "{CALL sp_agregar_intendente(?, ?, ?, ?, ?)}";

    try (Connection con = getConexion();
         CallableStatement cstmt = con.prepareCall(sql)) {

        cstmt.setString(1, intendenteAux.getNombre());
        cstmt.setString(2, intendenteAux.getUsuario());
        cstmt.setString(3, intendenteAux.getPassword());

        OracleConnection oracleCon = con.unwrap(OracleConnection.class);

        // turnos ids
        Integer[] idsTurnos = intendenteAux.getTurnos() == null
                ? new Integer[0]
                : intendenteAux.getTurnos()
                        .stream()
                        .map(Turno::getId)
                        .toArray(Integer[]::new);

        ArrayDescriptor descTurnos =
                ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

        ARRAY arrayTurnos = new ARRAY(descTurnos, oracleCon, idsTurnos);

        // habitats
        Integer[] idsHabitats = intendenteAux.getIdsHabitats() == null
                ? new Integer[0]
                : intendenteAux.getIdsHabitats().toArray(new Integer[0]);

        ArrayDescriptor descHabitats =
                ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

        ARRAY arrayHabitats = new ARRAY(descHabitats, oracleCon, idsHabitats);

        // parametros
        cstmt.setArray(4, arrayTurnos);
        cstmt.setArray(5, arrayHabitats);

        cstmt.execute();
        return true;

    } catch (SQLException e) {

        if (e.getErrorCode() == 20001) {
            throw new Exception("El nombre de usuario ya se encuentra registrado.");
        }

        throw new Exception("Error al insertar el Intendente: " + e.getMessage(), e);
    }
}

    /**
     * Modifica los datos de un intendente con los de un parametro
     *
     * @param intendenteAux nuevos datos del intendente con el mismo id
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
public boolean modificarIntendente(Intendente intendenteAux) throws Exception {

    String sql = "{CALL sp_modificar_intendente(?,?,?,?,?,?)}";

    try (Connection con = getConexion();
         CallableStatement cstmt = con.prepareCall(sql)) {

        cstmt.setInt(1, intendenteAux.getId());
        cstmt.setString(2, intendenteAux.getNombre());
        cstmt.setString(3, intendenteAux.getUsuario());
        cstmt.setString(4, intendenteAux.getPassword());

        OracleConnection oracleCon = con.unwrap(OracleConnection.class);

        // turnos ids
        Integer[] idsTurnos = (intendenteAux.getTurnos() == null)
                ? new Integer[0]
                : intendenteAux.getTurnos()
                        .stream()
                        .map(Turno::getId)
                        .toArray(Integer[]::new);

        ArrayDescriptor descTurnos =
                ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

        ARRAY arrayTurnos = new ARRAY(descTurnos, oracleCon, idsTurnos);

        // habitats
        Integer[] idsHabitats = (intendenteAux.getIdsHabitats() == null)
                ? new Integer[0]
                : intendenteAux.getIdsHabitats().toArray(new Integer[0]);

        ArrayDescriptor descHabitats =
                ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

        ARRAY arrayHabitats = new ARRAY(descHabitats, oracleCon, idsHabitats);

        // parametros
        cstmt.setArray(5, arrayTurnos);
        cstmt.setArray(6, arrayHabitats);

        return cstmt.executeUpdate() >= 0;

    } catch (Exception e) {
        throw new Exception("Error al modificar el Intendente: " + e.getMessage(), e);
    }
}

    /**
     * Obtiene de oracle la lista de ids que tiene asignados(habitats).
     *
     * @param idIntendente idBuscado
     * @return lista de ids de habitats
     * @throws Exception
     */
    @Override
    public List<Integer> obtenerIdsHabitats(int idIntendente) throws Exception {
        List<Integer> idsHabitats = new ArrayList<>();
        String sql = "{CALL sp_obtener_ids_habitats_intendente(?,?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setInt(1, idIntendente);
            cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);

            cstmt.execute();

            try (ResultSet rs = (ResultSet) cstmt.getObject(2)) {
                while (rs.next()) {
                    idsHabitats.add(rs.getInt(1));
                }
            }

            return idsHabitats;

        } catch (Exception e) {
            throw new Exception("Error al obtener los IDs de los hábitats: " + e.getMessage());
        }
    }

    /**
     * Obtiene un intendente segun su id.
     *
     * @param id id del intendente a buscar
     * @return Intendente
     * @throws Exception
     */
    @Override
    public Intendente obtenerIntendente(int id) throws Exception {
        String sql = "{CALL sp_obtener_intendente(?,?,?,?,?,?,?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setInt(1, id);
            cstmt.registerOutParameter(2, Types.NUMERIC);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.VARCHAR);
            cstmt.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);
            cstmt.registerOutParameter(7, oracle.jdbc.OracleTypes.CURSOR);

            cstmt.execute();

            if (cstmt.getObject(2) == null) {
                return null;
            }

            Intendente intendenteAux = new Intendente();
            intendenteAux.setId(cstmt.getInt(2));
            intendenteAux.setNombre(cstmt.getString(3));
            intendenteAux.setUsuario(cstmt.getString(4));
            intendenteAux.setPassword(cstmt.getString(5));
            intendenteAux.setTurnos(new ArrayList<>());
            intendenteAux.setIdsHabitats(new ArrayList<>());

            try (ResultSet rsTurnos = (ResultSet) cstmt.getObject(6)) {
                while (rsTurnos.next()) {
                    Turno turno = new Turno();
                    turno.setId(rsTurnos.getInt("id_turno"));

                    java.sql.Date fechaSQL = rsTurnos.getDate("fecha");
                    if (fechaSQL != null) {
                        turno.setFecha(fechaSQL.toLocalDate());
                    }

                    java.sql.Timestamp inicioSQL = rsTurnos.getTimestamp("hora_inicio");
                    if (inicioSQL != null) {
                        turno.setHoraInicio(inicioSQL.toLocalDateTime().toLocalTime());
                    }

                    java.sql.Timestamp finSQL = rsTurnos.getTimestamp("hora_fin");
                    if (finSQL != null) {
                        turno.setHoraFin(finSQL.toLocalDateTime().toLocalTime());
                    }

                    intendenteAux.getTurnos().add(turno);
                }
            }

            try (ResultSet rsHabitats = (ResultSet) cstmt.getObject(7)) {
                while (rsHabitats.next()) {
                    intendenteAux.getIdsHabitats().add(rsHabitats.getInt(1));
                }
            }

            return intendenteAux;

        } catch (Exception e) {
            throw new Exception("Error al obtener el Intendente: " + e.getMessage());
        }
    }

    /**
     * Recorre todos los intendentes y aquellos que en su lista idHabitats
     * tengan el parametro, se les eliminara ese valor.
     *
     * @param idHabitat id del habitat que se elimino
     * @return true exito, false caso contrario.
     * @throws Exception
     */
    @Override
    public boolean eliminarIdHabitat(int idHabitat) throws Exception {
        String sql = "{call sp_eliminar_id_habitat_intendentes(?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setInt(1, idHabitat);
            return cstmt.executeUpdate() >= 0;

        } catch (Exception e) {
            throw new Exception("Error al eliminar el ID del hábitat en los intendentes: " + e.getMessage());
        }
    }
}
