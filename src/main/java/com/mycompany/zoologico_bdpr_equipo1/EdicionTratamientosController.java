/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EdicionTratamientosController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private Button btnCerrar;

    @FXML
    private TableView<String> tblTratamientos;

    @FXML
    private TableColumn<String, String> colTratamiento;

    @FXML
    private TableColumn<String, Void> colBotonEliminar;

    @FXML
    private TextField txtNuevoTratamiento;

    @FXML
    private Button btnAgregar;

    private ObservableList<String> listaTratamientos;

    /**
     * Permite asignar la lista principal de tratamientos.
     *
     * @param listaTratamientos lista compartida
     */
    public void setListaTratamientos(ObservableList<String> listaTratamientos) {
        this.listaTratamientos = listaTratamientos;
        tblTratamientos.setItems(this.listaTratamientos);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // La celda mostrará directamente el String
        colTratamiento.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue()));

        configurarColumnaEliminar();

        cargarDatos();
    }

    /**
     * Cierra la ventana actual.
     */
    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Agrega un nuevo tratamiento a la lista.
     */
    @FXML
    private void agregarTratamiento(ActionEvent event) {

        String nuevoTratamiento = txtNuevoTratamiento.getText().trim();

        if (nuevoTratamiento.isEmpty()) {
            mostrarAlerta(
                    "Campo vacío",
                    "Debe escribir un tratamiento.",
                    Alert.AlertType.WARNING
            );
            return;
        }

        listaTratamientos.add(nuevoTratamiento);

        txtNuevoTratamiento.clear();
    }

    /**
     * Configura la columna de botones eliminar.
     */
    private void configurarColumnaEliminar() {

        colBotonEliminar.setCellFactory(param -> new TableCell<>() {

            private final Button btnEliminarFila = new Button("Eliminar");

            {
                btnEliminarFila.setStyle(
                        "-fx-background-color: #ff4d4d; "
                        + "-fx-text-fill: white;"
                );

                btnEliminarFila.setOnAction(event -> {

                    String tratamientoSeleccionado =
                            getTableView().getItems().get(getIndex());

                    listaTratamientos.remove(tratamientoSeleccionado);

                    System.out.println(
                            "Eliminar tratamiento: "
                            + tratamientoSeleccionado
                    );
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminarFila);
                }
            }
        });
    }

    /**
     * Carga los datos en la tabla.
     */
    private void cargarDatos() {

        if (listaTratamientos == null || listaTratamientos.isEmpty()) {
            mostrarAlerta( "Vacío", "No hay tratamientos para mostrar.", Alert.AlertType.INFORMATION);
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
