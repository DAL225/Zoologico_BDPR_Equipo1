/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

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

public class ListaTratamientosController implements Initializable {

    @FXML
    private Button btnCerrar;

    @FXML
    private TableView<String> tblTratamientos;

    @FXML
    private TableColumn<String, String> colTratamiento;

    private ObservableList<String> listaTratamientos;

    /**
     * Permite asignar la lista que se mostrará en la tabla.
     *
     * @param listaTratamientos lista de tratamientos
     */
    public void setListaTratamientos(ObservableList<String> listaTratamientos) {
        this.listaTratamientos = listaTratamientos;
        tblTratamientos.setItems(this.listaTratamientos);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colTratamiento.setCellValueFactory( new PropertyValueFactory<>("value"));

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

        if (listaTratamientos == null || listaTratamientos.isEmpty()) {
            mostrarAlerta("Vacío", "No hay tratamientos para mostrar.", Alert.AlertType.INFORMATION);
            return;
        }

        tblTratamientos.setItems(listaTratamientos);
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