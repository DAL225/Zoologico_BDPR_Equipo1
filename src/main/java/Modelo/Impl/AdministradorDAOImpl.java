/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Administrador;
import Modelo.Dao.AdministradorDAO;
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

/**
 *
 * @author amiss
 */
public class AdministradorDAOImpl extends BaseDAOOracle implements AdministradorDAO {

    /**
     * Constructor de la clase
     *
     * @throws Exception Posible Excepcion
     */
    public AdministradorDAOImpl() throws Exception {
    }

    /**
     * Agrega un nuevo administrador a la BD.
     *
     * @param adminAux admin a agregar
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean agregarAdministrador(Administrador adminAux) throws Exception {

        String sql = "{CALL sp_agregar_administrador(?, ?, ?, ?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setString(1, adminAux.getNombre());
            cstmt.setString(2, adminAux.getUsuario());
            cstmt.setString(3, adminAux.getPassword());

            Integer[] idsTurnos = (adminAux.getTurnos() != null && !adminAux.getTurnos().isEmpty())
                    ? adminAux.getTurnos().stream().map(Turno::getId).toArray(Integer[]::new)
                    : new Integer[0];

            OracleConnection oracleCon = con.unwrap(OracleConnection.class);
            Array arrayTurnos = oracleCon.createOracleArray("TABLA_ENTEROS_TYP", idsTurnos);

            cstmt.setArray(4, arrayTurnos);
            cstmt.execute();
            return true;

        } catch (SQLException e) {
            if (e.getErrorCode() == 20001) {
                throw new Exception("El nombre de usuario ya se encuentra registrado.");
            }
            throw new Exception("Error al insertar administrador: " + e.getMessage());
        }
    }

    /**
     * Modifica lod datos de un administrador
     *
     * @param adminAux admin con datos y id que se modificara
     * @return true exito, false en caso contrario
     * @throws Exception
     */
    @Override
    public boolean modificarAdministrador(Administrador adminAux) throws Exception {
        String sql = "{CALL sp_modificar_administrador(?, ?, ?, ?, ?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setInt(1, adminAux.getId());
            cstmt.setString(2, adminAux.getNombre());
            cstmt.setString(3, adminAux.getUsuario());
            cstmt.setString(4, adminAux.getPassword());

            Integer[] idsTurnos = adminAux.getTurnos() == null
                    ? new Integer[0]
                    : adminAux.getTurnos().stream().map(Turno::getId).toArray(Integer[]::new);

            OracleConnection oracleCon = con.unwrap(OracleConnection.class);
            Array arrayTurnos = oracleCon.createOracleArray("TABLA_ENTEROS_TYP", idsTurnos);

            cstmt.setArray(5, arrayTurnos);
            cstmt.execute();

            return true;

        } catch (SQLException e) {
            if (e.getErrorCode() == 20001) {
                throw new Exception("El nombre de usuario ya se encuentra registrado por otro empleado.");
            }
            throw new Exception("Error al modificar el Administrador: " + e.getMessage());
        }
    }

    /**
     * Obtiene un admin por su id.
     *
     * @param id id a buscar
     * @return administrador
     * @throws Exception
     */
    @Override
    public Administrador obtenerAdministrador(int id) throws Exception {
        Administrador administradorAux = null;

        String sql = "{CALL sp_obtener_administrador(?, ?, ?, ?, ?, ?)}";

        try (Connection con = getConexion(); CallableStatement cstmt = con.prepareCall(sql)) {

            cstmt.setInt(1, id);

            cstmt.registerOutParameter(2, Types.NUMERIC);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.VARCHAR);
            cstmt.registerOutParameter(6, OracleTypes.CURSOR);

            cstmt.execute();

            administradorAux = new Administrador();
            administradorAux.setId(cstmt.getInt(2));
            administradorAux.setNombre(cstmt.getString(3));
            administradorAux.setUsuario(cstmt.getString(4));
            administradorAux.setPassword(cstmt.getString(5));
            administradorAux.setTurnos(new ArrayList<>());

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

                    administradorAux.getTurnos().add(turno);
                }
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1403) {
                return null;
            }
            throw new Exception("Error al obtener el Administrador: " + e.getMessage());
        }

        return administradorAux;
    }
}
