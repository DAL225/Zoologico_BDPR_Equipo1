/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Dao.TurnoDAO;
import Modelo.Impl.TurnoDAOImpl;
import Modelo.Turno;
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

public class EliminarTurnoController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private AnchorPane subPnlCamposElim;

    @FXML
    private Spinner<Integer> spnIdTurno;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextArea txtDatos;

    private Turno turnoActual;

    private TurnoDAO turnoDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        spnIdTurno.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );

        subPnlCamposElim.setVisible(false);
        txtDatos.setEditable(false);
    }

    @FXML
    private void buscarTurno(ActionEvent event) {

        int id = spnIdTurno.getValue();

        try {

            turnoDao = new TurnoDAOImpl();
            turnoActual = turnoDao.obtenerTurno(id);

        } catch (Exception e) {

            mostrarAlerta("Error","Error al cargar los datos",Alert.AlertType.ERROR);
            return;
        }

        if (turnoActual != null) {

            txtDatos.setText(turnoActual.toString2());
            txtDatos.setEditable(false);

            subPnlCamposElim.setVisible(true);

        } else {

            mostrarAlerta("Error","No existe un turno con ese ID",Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {

        if (turnoActual == null) {

            mostrarAlerta("Error","Debe buscar un turno primero",Alert.AlertType.WARNING);
            return;
        }

        try {

            if (turnoDao.eliminarTurno(turnoActual.getId())) {

                mostrarAlerta("Éxito","Turno eliminado correctamente",Alert.AlertType.INFORMATION);

                limpiarCamposEliminar();
                return;
            }

            mostrarAlerta("Error","No se pudo eliminar el turno",Alert.AlertType.ERROR);

        } catch (Exception e) {

            mostrarAlerta("Error",e.getMessage(),Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposEliminar() {
        turnoActual = null;
        txtDatos.clear();
        spnIdTurno.getValueFactory().setValue(1);
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