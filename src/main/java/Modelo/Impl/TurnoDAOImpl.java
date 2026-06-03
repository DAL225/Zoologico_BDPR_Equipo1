/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Dao.TurnoDAO;
import Modelo.Turno;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author amiss
 */
public class TurnoDAOImpl extends BaseDAOOracle implements TurnoDAO {

    /**
     * Constructor de la clase
     *
     * @throws Exception Posible Excepcion
     */
    public TurnoDAOImpl() throws Exception {
    }

    /**
     * Obtiene todos los turnos existentes.
     *
     * @return lista de turnos.
     * @throws Exception
     */
    @Override
    public List<Turno> obtenerTodosTurnos() throws Exception {

        List<Turno> listaTurnos = new ArrayList<>();

        String sql = "{CALL sp_obtener_todos_turnos(?)}";

        try (Connection con = getConexion(); 
                CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
            cstmt.execute();

            try (ResultSet rs = (ResultSet) cstmt.getObject(1)) {
                
                while (rs.next()) {
                    Turno turno = new Turno();
                    
                    // ID
                    turno.setId(rs.getInt("id_turno"));

                    // Fecha
                    java.sql.Date fechaSQL = rs.getDate("fecha");
                    if (fechaSQL != null) {
                        turno.setFecha(fechaSQL.toLocalDate());
                    }

                    // Hora inicio
                    java.sql.Timestamp inicioSQL = rs.getTimestamp("hora_inicio");
                    if (inicioSQL != null) {
                        turno.setHoraInicio(
                                inicioSQL.toLocalDateTime().toLocalTime()
                        );
                    }

                    // Hora fin
                    java.sql.Timestamp finSQL = rs.getTimestamp("hora_fin");
                    if (finSQL != null) {
                        turno.setHoraFin(
                                finSQL.toLocalDateTime().toLocalTime()
                        );
                    }

                    listaTurnos.add(turno);
                }
            }

        } catch (SQLException e) {
            throw new Exception("Error al obtener la lista de turnos: " + e.getMessage());
        }

        return listaTurnos;
    }

    /**
     * Ontiene un objeto Turno segun su id.
     *
     * @param id id del Turno a buscar
     * @return Turno
     * @throws Exception
     */
    @Override
    public Turno obtenerTurno(int id) throws Exception {

        Turno turnoAux = null;

        String sql = "{CALL sp_obtener_turno(?, ?, ?, ?)}";

        try (Connection con = getConexion(); 
                CallableStatement cstmt = con.prepareCall(sql)) {

            // Parámetro IN
            cstmt.setInt(1, id);

            // Parámetros OUT
            cstmt.registerOutParameter(2, Types.DATE);
            cstmt.registerOutParameter(3, Types.TIMESTAMP);
            cstmt.registerOutParameter(4, Types.TIMESTAMP);

            cstmt.execute();

            turnoAux = new Turno();
            turnoAux.setId(id);

            // Fecha
            java.sql.Date fechaSQL = cstmt.getDate(2);
            if (fechaSQL != null) {
                turnoAux.setFecha(fechaSQL.toLocalDate());
            }

            // Hora inicio
            java.sql.Timestamp inicioSQL = cstmt.getTimestamp(3);
            if (inicioSQL != null) {
                turnoAux.setHoraInicio(inicioSQL.toLocalDateTime().toLocalTime());
            }

            // Hora fin
            java.sql.Timestamp finSQL = cstmt.getTimestamp(4);
            if (finSQL != null) {
                turnoAux.setHoraFin(finSQL.toLocalDateTime().toLocalTime());
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1403) {
                return null;
            }

            throw new Exception("Error al obtener el turno con ID " + id + ": " + e.getMessage());
        }

        return turnoAux;
    }

    /**
     * Modifica los datos de un turno con los de un parametro
     *
     * @param turno nuevos datos del turno con el mismo id
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean modificarTurno(Turno turnoAux) throws Exception {

        String sql = "{CALL sp_modificar_turno(?, ?, ?, ?)}";

        try (Connection con = getConexion(); 
                CallableStatement cstmt = con.prepareCall(sql)) {

            // ID del turno
            cstmt.setInt(1, turnoAux.getId());

            // Fecha
            if (turnoAux.getFecha() != null) {
                cstmt.setDate(2, Date.valueOf(turnoAux.getFecha()));
            } else {
                cstmt.setNull(2, Types.DATE);
            }

            // Hora inicio
            if (turnoAux.getHoraInicio() != null) {
                Timestamp inicio = Timestamp.valueOf(turnoAux.getHoraInicio().atDate(turnoAux.getFecha()));
                cstmt.setTimestamp(3, inicio);
            } else {
                cstmt.setNull(3, Types.TIMESTAMP);
            }

            // Hora fin
            if (turnoAux.getHoraFin() != null) {
                Timestamp fin = Timestamp.valueOf(turnoAux.getHoraFin().atDate(turnoAux.getFecha()));
                cstmt.setTimestamp(4, fin);
            } else {
                cstmt.setNull(4, Types.TIMESTAMP);
            }
            
            cstmt.execute();
            return true;

        } catch (SQLException e) {

            if (e.getErrorCode() == 20002) {
                throw new Exception("Ya existe otro turno registrado con la misma fecha y rango horario.");
            }

            throw new Exception("Error al modificar el turno: " + e.getMessage());
        }
    }

    /**
     * Elimina un turno segun su id.
     *
     * @param idTurno id del truno a eliminar
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean eliminarTurno(int idTurno) throws Exception {

        String sql = "{CALL sp_eliminar_turno(?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setInt(1, idTurno);
            cstmt.execute();
            return true;

        } catch (SQLException e) {
            throw new Exception("Error al eliminar el turno: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo turno a la BD.
     *
     * @param turnoAux Nuevo turno a agregar.
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean agregarTurno(Turno turnoAux) throws Exception {

        String sql = "{CALL sp_agregar_turno(?, ?, ?)}";

        try (
                Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            // Fecha
            if (turnoAux.getFecha() != null) {
                cstmt.setDate(1, Date.valueOf(turnoAux.getFecha()));
            } else {
                cstmt.setNull(1, Types.DATE);
            }

            // Hora inicio
            if (turnoAux.getHoraInicio() != null) {
                Timestamp inicio = Timestamp.valueOf(turnoAux.getHoraInicio().atDate(turnoAux.getFecha()));
                cstmt.setTimestamp(2, inicio);
            } else {
                cstmt.setNull(2, Types.TIMESTAMP);
            }

            // Hora fin
            if (turnoAux.getHoraFin() != null) {
                Timestamp fin = Timestamp.valueOf(turnoAux.getHoraFin().atDate(turnoAux.getFecha()));
                cstmt.setTimestamp(3, fin);
            } else {
                cstmt.setNull(3, Types.TIMESTAMP);
            }
            cstmt.execute();
            return true;

        } catch (SQLException e) {

            // Error personalizado lanzado por Oracle
            if (e.getErrorCode() == 20002) {
                throw new Exception("Ya existe un turno registrado con la misma fecha y rango horario.");
            }
            
            throw new Exception("Error al agregar el turno: " + e.getMessage());
        }
    }

}
