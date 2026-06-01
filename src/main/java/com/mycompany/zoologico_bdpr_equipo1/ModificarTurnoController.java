/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Dao.TurnoDAO;
import Modelo.Impl.TurnoDAOImpl;
import Modelo.Turno;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ModificarTurnoController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private StackPane stckPane;

    @FXML
    private AnchorPane subPnlCamposMod;

    @FXML
    private AnchorPane subPnlVeterinario;

    @FXML
    private AnchorPane subPnlCuidador;

    @FXML
    private AnchorPane subPnlIntendente;

    @FXML
    private DatePicker chkFecha;

    @FXML
    private ChoiceBox<String> chkHorario;

    @FXML
    private Spinner<Integer> spnIdTurno;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnModificarDatos;

    @FXML
    private Button btnAnimalesVeterinario;

    @FXML
    private Button btnEspecialidades;

    @FXML
    private Button btnAnimalesCuidador;

    private Turno turnoActual;

    private TurnoDAO turnoDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        chkHorario.getItems().addAll(
                "Matutino",
                "Vespertino",
                "Nocturno"
        );

        spnIdTurno.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );

        subPnlCamposMod.setVisible(false);
    }

    @FXML
    private void buscarTurno(ActionEvent event) {

        int id = spnIdTurno.getValue();

        try {

            turnoDao = new TurnoDAOImpl();
            turnoActual = turnoDao.obtenerTurno(id);

        } catch (Exception e) {

            mostrarAlerta(
                    "Error",
                    "Error al cargar los datos",
                    Alert.AlertType.ERROR
            );
            return;
        }

        if (turnoActual != null) {

            chkFecha.setValue(turnoActual.getFecha());

            if (turnoActual.getHoraInicio().equals(LocalTime.of(6, 0))
                    && turnoActual.getHoraFin().equals(LocalTime.of(14, 0))) {

                chkHorario.setValue("Matutino");

            } else if (turnoActual.getHoraInicio().equals(LocalTime.of(14, 0))
                    && turnoActual.getHoraFin().equals(LocalTime.of(22, 0))) {

                chkHorario.setValue("Vespertino");

            } else {

                chkHorario.setValue("Nocturno");
            }

            subPnlCamposMod.setDisable(false);
            subPnlCamposMod.setVisible(true);

        } else {

            limpiarCamposModificar();

            mostrarAlerta(
                    "Error",
                    "No existe un turno con ese ID",
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    private void modificarDatos(ActionEvent event) {

        LocalDate fecha = chkFecha.getValue();
        String horario = chkHorario.getValue();

        if (fecha == null || horario == null) {
            mostrarAlerta("Campos incompletos", "Todos los campos son obligatorios", Alert.AlertType.WARNING);
            return;
        }

        if (fecha.isBefore(LocalDate.now())) {
            mostrarAlerta("Fecha inválida", "La fecha no puede ser anterior al día actual", Alert.AlertType.WARNING);
            return;
        }

        LocalTime horaInicio;
        LocalTime horaFin;

        switch (horario) {

            case "Matutino":
                horaInicio = LocalTime.of(6, 0);
                horaFin = LocalTime.of(14, 0);
                break;

            case "Vespertino":
                horaInicio = LocalTime.of(14, 0);
                horaFin = LocalTime.of(22, 0);
                break;

            default: // Nocturno
                horaInicio = LocalTime.of(22, 0);
                horaFin = LocalTime.of(6, 0);
                break;
        }

        turnoActual.setFecha(fecha);
        turnoActual.setHoraInicio(horaInicio);
        turnoActual.setHoraFin(horaFin);

        try {
            turnoDao = new TurnoDAOImpl();
            if (turnoDao.modificarTurno(turnoActual)) {

                mostrarAlerta("Éxito", "Turno modificado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposModificar();
                return;
            }

            mostrarAlerta("Fracaso", "No se pudo modificar el turno", Alert.AlertType.WARNING);

        } catch (Exception e) {

            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposModificar() {

        chkFecha.setValue(null);
        chkHorario.setValue(null);

        turnoActual = null;

        subPnlCamposMod.setVisible(false);
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
