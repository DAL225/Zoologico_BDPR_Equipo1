/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Dao.EmpleadoDAO;
import Modelo.Empleado;
import Modelo.Impl.EmpleadoDAOImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class EliminarEmpleadoController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private AnchorPane subPnlCamposElim;

    @FXML
    private Spinner<Integer> spnIdEmpleado;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextArea txtDatos;

    private Empleado empleadoActual;

    private EmpleadoDAO empleadoDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        spnIdEmpleado.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );

        subPnlCamposElim.setVisible(false);
    }

    @FXML
    private void buscarEmpleado(ActionEvent event) {

        int id = spnIdEmpleado.getValue();

        try {

            empleadoDao = new EmpleadoDAOImpl();
            empleadoActual = empleadoDao.obtenerEmpleado(id);

        } catch (Exception e) {

            mostrarAlerta("Error","Error al cargar los datos",Alert.AlertType.ERROR);
            return;
        }

        if (empleadoActual != null) {

            txtDatos.setText(empleadoActual.toString2());
            txtDatos.setEditable(false);

            subPnlCamposElim.setVisible(true);

        } else {

            mostrarAlerta("Error","No existe un empleado con ese ID",Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {

        if (empleadoActual == null) {

            mostrarAlerta("Error","Debe buscar un empleado primero",Alert.AlertType.WARNING);
            return;
        }

        try {

            if (empleadoDao.eliminarEmpleado(empleadoActual.getId())) {

                mostrarAlerta("Éxito","Empleado eliminado correctamente",Alert.AlertType.INFORMATION);

                limpiarCamposEliminar();
                return;
            }

            mostrarAlerta("Error","No se pudo eliminar el empleado",Alert.AlertType.ERROR);

        } catch (Exception e) {

            mostrarAlerta("Error",e.getMessage(),Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposEliminar() {
        empleadoActual = null;
        txtDatos.clear();
        spnIdEmpleado.getValueFactory().setValue(1);
        subPnlCamposElim.setVisible(false);
    }

    /**
     * Muestra una alerta con el título, mensaje y tipo especificados.
     *
     * @param titulo título de la ventana de alerta
     * @param mensaje contenido mostrado en la alerta
     * @param tipo tipo de alerta a mostrar
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}