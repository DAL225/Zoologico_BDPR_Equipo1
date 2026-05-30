/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

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

        Turno turno = new Turno(
                fecha,
                horaInicio,
                horaFin
        );

        System.out.println(turno);

        // Aquí llamarías al DAO para registrar el turno
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