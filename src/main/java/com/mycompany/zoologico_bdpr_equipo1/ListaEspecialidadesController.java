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

public class ListaEspecialidadesController implements Initializable {

    @FXML
    private Button btnCerrar;

    @FXML
    private TableView<String> tblEspecialidades;

    @FXML
    private TableColumn<String, String> colEspecialidad;

    private ObservableList<String> listaEspecialidades;

    /**
     * Permite asignar la lista que se mostrará en la tabla.
     *
     * @param listaEspecialidades lista de especialidades
     */
    public void setListaEspecialidades(ObservableList<String> listaEspecialidades) {
        this.listaEspecialidades = listaEspecialidades;
        cargarDatos();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colEspecialidad.setCellValueFactory(
                new PropertyValueFactory<>("value")
        );

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

        if (listaEspecialidades == null || listaEspecialidades.isEmpty()) {
            mostrarAlerta("Vacío","No hay especialidades para mostrar.",Alert.AlertType.INFORMATION
            );
            return;
        }

        tblEspecialidades.setItems(listaEspecialidades);
    }

    /**
     * Muestra una alerta.
     *
     * @param titulo título de la alerta
     * @param mensaje mensaje de la alerta
     * @param tipo tipo de alerta
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}