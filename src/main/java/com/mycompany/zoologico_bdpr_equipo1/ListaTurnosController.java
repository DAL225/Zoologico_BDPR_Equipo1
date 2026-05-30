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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ListaTurnosController implements Initializable {

    @FXML
    private Button btnCerrar;

    @FXML
    private TableView<Turno> tblTurnos;

    @FXML
    private TableColumn<Turno, Integer> colId;

    @FXML
        private TableColumn<Turno, LocalDate> colFecha;

    @FXML
    private TableColumn<Turno, LocalTime> colHoraInicio;

    @FXML
    private TableColumn<Turno, LocalTime> colHoraFin;

    private ObservableList<Turno> listaTurnos;

    /**
     * Permite asignar la lista de turnos.
     *
     * @param listaTurnos lista a mostrar
     */
    public void setListaTurnos(ObservableList<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
        tblTurnos.setItems(this.listaTurnos);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        colHoraFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));

        cargarDatos();
    }

    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Carga los datos en la tabla.
     */
    private void cargarDatos() {

        if (listaTurnos == null || listaTurnos.isEmpty()) {

            mostrarAlerta("Vacío","No hay turnos para mostrar.",Alert.AlertType.INFORMATION);
            return;
        }

        tblTurnos.setItems(listaTurnos);
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