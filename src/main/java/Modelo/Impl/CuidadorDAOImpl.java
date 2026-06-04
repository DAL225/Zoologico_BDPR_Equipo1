/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Cuidador;
import Modelo.Dao.CuidadorDAO;
import Modelo.Turno;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class CuidadorDAOImpl extends BaseDAOOracle implements CuidadorDAO {

    /**
     * Constructor de la clase
     *
     * @throws Exception Posible Excepcion
     */
    public CuidadorDAOImpl() throws Exception {
    }

    /**
     * Agrega un nuevo cuidador al sistema.
     *
     * @param cuidadorAux cuidador con los datos a almacenar
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean agregarCuidador(Cuidador cuidadorAux) throws Exception {

        String sql = "{CALL sp_agregar_cuidador(?, ?, ?, ?, ?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setString(1, cuidadorAux.getNombre());
            cstmt.setString(2, cuidadorAux.getUsuario());
            cstmt.setString(3, cuidadorAux.getPassword());

            OracleConnection oracleCon = con.unwrap(OracleConnection.class);

            // turnos
            Integer[] idsTurnos = (cuidadorAux.getTurnos() == null)
                    ? new Integer[0]
                    : cuidadorAux.getTurnos()
                            .stream()
                            .map(Turno::getId)
                            .toArray(Integer[]::new);

            ArrayDescriptor descTurnos
                    = ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

            ARRAY arrayTurnos = new ARRAY(descTurnos, oracleCon, idsTurnos);

            // ids animales
            Integer[] idsAnimales = (cuidadorAux.getIdsAnimales() == null)
                    ? new Integer[0]
                    : cuidadorAux.getIdsAnimales().toArray(new Integer[0]);

            ArrayDescriptor descAnimales
                    = ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

            ARRAY arrayAnimales = new ARRAY(descAnimales, oracleCon, idsAnimales);

            // parametros
            cstmt.setArray(4, arrayTurnos);
            cstmt.setArray(5, arrayAnimales);

            cstmt.execute();
            return true;

        } catch (SQLException e) {

            if (e.getErrorCode() == 20001) {
                throw new Exception("El nombre de usuario ya se encuentra registrado.");
            }

            throw new Exception("Error al insertar el Cuidador: " + e.getMessage(), e);
        }
    }

    /**
     * Modifica los datos de un cuidador, recibiendo los nuevos datos en un
     * objeto con el id donde se realizaran los cambios.
     *
     * @param cuidadorAux datos a cambiar.
     * @return true exito, false caso contrario
     * @throws Exception
     */
    @Override
    public boolean modificarCuidador(Cuidador cuidadorAux) throws Exception {

        String sql = "{CALL sp_modificar_cuidador(?, ?, ?, ?, ?, ?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setInt(1, cuidadorAux.getId());
            cstmt.setString(2, cuidadorAux.getNombre());
            cstmt.setString(3, cuidadorAux.getUsuario());
            cstmt.setString(4, cuidadorAux.getPassword());

            OracleConnection oracleCon = con.unwrap(OracleConnection.class);

            // turnos
            Integer[] idsTurnos = (cuidadorAux.getTurnos() == null)
                    ? new Integer[0]
                    : cuidadorAux.getTurnos()
                            .stream()
                            .map(Turno::getId)
                            .toArray(Integer[]::new);

            ArrayDescriptor descTurnos
                    = ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

            ARRAY arrayTurnos = new ARRAY(descTurnos, oracleCon, idsTurnos);

            // ids animales
            Integer[] idsAnimales = (cuidadorAux.getIdsAnimales() == null)
                    ? new Integer[0]
                    : cuidadorAux.getIdsAnimales().toArray(new Integer[0]);

            ArrayDescriptor descAnimales
                    = ArrayDescriptor.createDescriptor("TABLA_ENTEROS_TYP", oracleCon);

            ARRAY arrayAnimales = new ARRAY(descAnimales, oracleCon, idsAnimales);

            // parametros
            cstmt.setArray(5, arrayTurnos);
            cstmt.setArray(6, arrayAnimales);

            cstmt.execute();
            return true;

        } catch (SQLException e) {

            if (e.getErrorCode() == 20001) {
                throw new Exception("El nombre de usuario ya se encuentra registrado por otro empleado.");
            }

            throw new Exception("Error al modificar el Cuidador: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene un cuidador por su id.
     *
     * @param id id requerido
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public Cuidador obtenerCuidador(int id) throws Exception {
        Cuidador cuidadorAux = null;

        String sql = "{CALL sp_obtener_cuidador(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setInt(1, id);

            cstmt.registerOutParameter(2, Types.NUMERIC);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.VARCHAR);
            cstmt.registerOutParameter(6, OracleTypes.CURSOR);
            cstmt.registerOutParameter(7, OracleTypes.CURSOR);

            cstmt.execute();

            cuidadorAux = new Cuidador();
            cuidadorAux.setId(cstmt.getInt(2));
            cuidadorAux.setNombre(cstmt.getString(3));
            cuidadorAux.setUsuario(cstmt.getString(4));
            cuidadorAux.setPassword(cstmt.getString(5));

            cuidadorAux.setTurnos(new ArrayList<>());
            cuidadorAux.setIdsAnimales(new ArrayList<>());

            try (ResultSet rs = (ResultSet) cstmt.getObject(6)) {
                while (rs.next()) {
                    Turno turno = new Turno();

                    turno.setId(rs.getInt("id_turno"));

                    Date fecha = rs.getDate("fecha");
                    if (fecha != null) {
                        turno.setFecha(fecha.toLocalDate());
                    }

                    Timestamp inicio = rs.getTimestamp("hora_inicio");
                    if (inicio != null) {
                        turno.setHoraInicio(inicio.toLocalDateTime().toLocalTime());
                    }

                    Timestamp fin = rs.getTimestamp("hora_fin");
                    if (fin != null) {
                        turno.setHoraFin(fin.toLocalDateTime().toLocalTime());
                    }

                    cuidadorAux.getTurnos().add(turno);
                }
            }

            try (ResultSet rs = (ResultSet) cstmt.getObject(7)) {
                while (rs.next()) {
                    cuidadorAux.getIdsAnimales().add(rs.getInt("column_value"));
                }
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1403) {
                return null;
            }

            throw new Exception("Error al obtener el Cuidador: " + e.getMessage());
        }

        return cuidadorAux;
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
        String sql = "{CALL sp_eliminar_id_animal_cuidadores(?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setInt(1, idAnimal);

            cstmt.execute();
            return true;

        } catch (SQLException e) {
            throw new Exception("Error al eliminar el ID del animal de los cuidadores: " + e.getMessage());
        }
    }
}
