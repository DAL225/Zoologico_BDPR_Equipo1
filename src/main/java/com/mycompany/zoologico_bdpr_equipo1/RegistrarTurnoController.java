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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

public class RegistrarTurnoController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private DatePicker chkFecha;

    @FXML
    private ChoiceBox<String> chkHorario;

    @FXML
    private Button btnRegistrarTurno;
    
    private TurnoDAO turnoDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        chkHorario.getItems().addAll(
                "Matutino",
                "Vespertino",
                "Nocturno"
        );
    }

    @FXML
    private void registrarTurno(ActionEvent event) {

        LocalDate fecha = chkFecha.getValue();
        String horario = chkHorario.getValue();

        if (fecha == null || horario == null) {
            mostrarAlerta("Campos incompletos", "Todos los campos son obligatorios",Alert.AlertType.WARNING);
            return;
        }
        
        if(fecha.isBefore(LocalDate.now())){
            mostrarAlerta("Fecha invalida", "La fecha no puede ser anterior al dia actual", Alert.AlertType.WARNING);
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

        try{
            turnoDao = new TurnoDAOImpl();
            
            Turno turnoAux = new Turno();
            turnoAux.setFecha(fecha);
            turnoAux.setHoraInicio(horaInicio);
            turnoAux.setHoraFin(horaFin);
        
            if (turnoDao.agregarTurno(turnoAux)) {
                mostrarAlerta("Registro", "Elemento registrado correctamente.", Alert.AlertType.INFORMATION);
                this.limpiarCampos();
                return;
            }
            mostrarAlerta("Aviso", "Fracaso al registrar", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
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
    
    /**
     * Limpia el componente de fecha (DatePicker) y restablece la selección del
     * horario.
     */
    private void limpiarCampos() {
        // 1. Limpiar el DatePicker
        chkFecha.setValue(null);
        if (chkFecha.getEditor() != null) {
            chkFecha.getEditor().clear();
        }

        // 2. Quitar la selección del ChoiceBox de horarios
        chkHorario.getSelectionModel().clearSelection();
    }
}
