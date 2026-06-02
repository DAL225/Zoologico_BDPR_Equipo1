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
        try {

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return false;
    }

    @Override
    public Administrador obtenerAdministrador(int id) throws Exception {
        Administrador administradorAux = new Administrador();
        try {

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return administradorAux;
    }
}
