/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Habitat;
import java.net.URL;
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

public class ListaHabitatsController implements Initializable {

    @FXML
    private Button btnCerrar;

    @FXML
    private TableView<Habitat> tblHabitats;

    @FXML
    private TableColumn<Habitat, Integer> colId;

    @FXML
    private TableColumn<Habitat, String> colNombre;

    @FXML
    private TableColumn<Habitat, String> colTipo;

    @FXML
    private TableColumn<Habitat, String> colClima;

    @FXML
    private TableColumn<Habitat, String> colNivelLimpieza;

    @FXML
    private TableColumn<Habitat, Integer> colCapacidad;

    private ObservableList<Habitat> listaHabitats;

    /**
     * Permite asignar la lista que se mostrará en la tabla.
     *
     * @param listaHabitats lista de hábitats
     */
    public void setListaHabitats(ObservableList<Habitat> listaHabitats) {
        this.listaHabitats = listaHabitats;
        tblHabitats.setItems(this.listaHabitats);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colClima.setCellValueFactory(new PropertyValueFactory<>("clima"));
        colNivelLimpieza.setCellValueFactory(new PropertyValueFactory<>("nivelLimpieza"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadAnimales"));

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

        if (listaHabitats == null
                || listaHabitats.isEmpty()) {

            mostrarAlerta("Vacío", "No hay hábitats para mostrar.", Alert.AlertType.INFORMATION);
            return;
        }

        tblHabitats.setItems(listaHabitats);
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