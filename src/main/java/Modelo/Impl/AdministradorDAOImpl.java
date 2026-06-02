/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Impl;

import Modelo.Administrador;
import Modelo.Dao.AdministradorDAO;
import Modelo.Turno;

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

    @Override
    public boolean agregarAdministrador(Administrador adminAux) throws Exception {
        // 1. Bloque PL/SQL que inserta el admin y luego le asocia los turnos por su ID
        StringBuilder sql = new StringBuilder();
        sql.append("DECLARE ");
        sql.append("  v_id_emp NUMBER; ");
        sql.append("BEGIN ");
        sql.append("  -- Obtener el siguiente ID de la secuencia \n");
        sql.append("  v_id_emp := sec_empleados.NEXTVAL; ");
        sql.append("  ");
        sql.append("  -- 1. Insertar el Administrador con la colección de turnos inicializada vacía \n");
        sql.append("  INSERT INTO empleados_tab VALUES ( ");
        sql.append("    administrador_typ(v_id_emp, ?, ?, ?, turnos_ref()) ");
        sql.append("  ); ");

        // 2. Si el objeto trae turnos en Java, generamos dinámicamente los inserts de sus referencias
        if (adminAux.getTurnos() != null && !adminAux.getTurnos().isEmpty()) {
            sql.append("\n  -- 2. Asociar las referencias (REF) de los turnos existentes \n");
            for (Turno t : adminAux.getTurnos()) {
                sql.append("  INSERT INTO TABLE( ")
                        .append("    SELECT e.turnos_asignados FROM empleados_tab e WHERE e.id_emp = v_id_emp ")
                        .append("  ) SELECT REF(t) FROM turnos_tab t WHERE t.id_turno = ").append(t.getId()).append("; ");
            }
        }

        sql.append("\n  COMMIT; ");
        sql.append("END;");

        // 3. Ejecución a través de JDBC
        try (java.sql.CallableStatement cstmt = getConexion().prepareCall(sql.toString())) {

            // Pasamos los parámetros básicos (Nombre, Usuario, Password)
            cstmt.setString(1, adminAux.getNombre());
            cstmt.setString(2, adminAux.getUsuario());
            cstmt.setString(3, adminAux.getPassword());

            int filasAfectadas = cstmt.executeUpdate();
            return filasAfectadas >= 0;

        } catch (Exception e) {
            throw new Exception("Error al insertar Administrador con turnos: " + e.getMessage());
        }
    }

    @Override
    public boolean modificarAdministrador(Administrador adminAux) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("DECLARE ");
        sql.append("  v_admin administrador_typ; ");
        sql.append("BEGIN ");

        // 1. Instanciamos el objeto administrador para poder usar sus métodos
        // Pasamos los datos que vienen de Java. (El último parámetro turnos_ref() limpia los turnos previos)
        sql.append("  v_admin := administrador_typ(?, ?, ?, ?, turnos_ref()); ");

        // 2. Invocamos el procedimiento que ya tienes en la BD para actualizar los datos básicos
        sql.append("  v_admin.modificar_empleado(?, ?, ?, ?); ");

        // 3. Si en Java mandaste una lista de turnos, los asociamos uno a uno
        if (adminAux.getTurnos() != null && !adminAux.getTurnos().isEmpty()) {
            sql.append("\n  -- Reasociar las nuevas referencias de turnos \n");
            for (Turno t : adminAux.getTurnos()) {
                sql.append("  INSERT INTO TABLE( ")
                        .append("    SELECT e.turnos_asignados FROM empleados_tab e WHERE e.id_emp = ? ")
                        .append("  ) SELECT REF(t) FROM turnos_tab t WHERE t.id_turno = ").append(t.getId()).append("; ");
            }
        }

        sql.append("\n  COMMIT; ");
        sql.append("END;");

        try (java.sql.CallableStatement cstmt = getConexion().prepareCall(sql.toString())) {
            int paramIndex = 1;

            // --- Parámetros para el constructor del objeto v_admin ---
            cstmt.setInt(paramIndex++, adminAux.getId());
            cstmt.setString(paramIndex++, adminAux.getNombre());
            cstmt.setString(paramIndex++, adminAux.getUsuario());
            cstmt.setString(paramIndex++, adminAux.getPassword());

            // --- Parámetros para el método v_admin.modificar_empleado(...) ---
            cstmt.setInt(paramIndex++, adminAux.getId());        // id_e
            cstmt.setString(paramIndex++, adminAux.getNombre());  // p_nombre
            cstmt.setString(paramIndex++, adminAux.getUsuario()); // p_usuario
            cstmt.setString(paramIndex++, adminAux.getPassword());// p_password

            // --- Parámetros dinámicos para el bucle de los turnos (si existen) ---
            if (adminAux.getTurnos() != null && !adminAux.getTurnos().isEmpty()) {
                for (Turno t : adminAux.getTurnos()) {
                    cstmt.setInt(paramIndex++, adminAux.getId()); // ID del empleado para el WHERE del INSERT INTO TABLE
                }
            }

            // Ejecutamos la actualización completa
            int filasAfectadas = cstmt.executeUpdate();
            return filasAfectadas >= 0;

        } catch (Exception e) {
            // Esto atrapará también el RAISE_APPLICATION_ERROR(-20005) si el ID no existía
            throw new Exception("Error al modificar el Administrador: " + e.getMessage());
        }
    }

    @Override
    public Administrador obtenerAdministrador(int id) throws Exception {
        Administrador administradorAux = null;

        // Consulta para obtener los datos básicos del administrador (filtrando por subtipo)
        String sqlAdmin = "SELECT e.id_emp, e.nombre, e.usuario, e.password "
                + "FROM empleados_tab e "
                + "WHERE e.id_emp = ? AND VALUE(e) IS OF (administrador_typ)";

        // Consulta para obtener los datos de los turnos desreferenciando los REF
        String sqlTurnos = "SELECT DEREF(t.column_value).id_turno, "
                + "       DEREF(t.column_value).fecha, "
                + "       DEREF(t.column_value).hora_inicio, "
                + "       DEREF(t.column_value).hora_fin "
                + "FROM empleados_tab e, TABLE(e.turnos_asignados) t "
                + "WHERE e.id_emp = ?";

        try {
            // 1. Buscamos los datos del Administrador
            try (java.sql.PreparedStatement pstmtAdmin = getConexion().prepareStatement(sqlAdmin)) {
                pstmtAdmin.setInt(1, id);

                try (java.sql.ResultSet rsAdmin = pstmtAdmin.executeQuery()) {
                    if (rsAdmin.next()) {
                        // Instanciamos el objeto con los datos básicos
                        administradorAux = new Administrador();
                        administradorAux.setId(rsAdmin.getInt("id_emp"));
                        administradorAux.setNombre(rsAdmin.getString("nombre"));
                        administradorAux.setUsuario(rsAdmin.getString("usuario"));
                        administradorAux.setPassword(rsAdmin.getString("password"));

                        // Inicializamos la lista de turnos en el objeto Java
                        java.util.List<Turno> listaTurnos = new java.util.ArrayList<>();
                        administradorAux.setTurnos(listaTurnos);
                    } else {
                        // Si no se encuentra el administrador, retornamos null o lanzamos excepción
                        return null;
                    }
                }
            }

            // 2. Si el administrador existe, cargamos sus turnos asociados
            try (java.sql.PreparedStatement pstmtTurnos = getConexion().prepareStatement(sqlTurnos)) {
                pstmtTurnos.setInt(1, id);

                try (java.sql.ResultSet rsTurnos = pstmtTurnos.executeQuery()) {
                    while (rsTurnos.next()) {
                        Turno turno = new Turno();
                        turno.setId(rsTurnos.getInt(1)); // id_turno
                        turno.setFecha(rsTurnos.getDate(2).toLocalDate()); // fecha
                        turno.setHoraInicio(rsTurnos.getTimestamp(3).toLocalDateTime().toLocalTime()); // hora_inicio
                        turno.setHoraFin(rsTurnos.getTimestamp(4).toLocalDateTime().toLocalTime()); // hora_fin

                        // Agregamos el turno a la lista del administrador
                        administradorAux.getTurnos().add(turno);
                    }
                }
            }

        } catch (Exception e) {
            throw new Exception("Error al obtener el Administrador: " + e.getMessage());
        }

        return administradorAux;
    }
}
