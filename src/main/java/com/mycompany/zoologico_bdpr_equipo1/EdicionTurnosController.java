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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EdicionTurnosController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

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

    @FXML
    private TableColumn<Turno, Void> colBotonEliminar;

    @FXML
    private Spinner<Integer> spnIdTurno;

    @FXML
    private Button btnAgregar;

    private ObservableList<Turno> listaTurnos;

    /**
     * Permite asignar la lista principal de turnos.
     *
     * @param listaTurnos lista compartida
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

        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);

        spnIdTurno.setValueFactory(valueFactory);

        configurarColumnaEliminar();

        cargarDatos();
    }

    /**
     * Cierra la ventana.
     */
    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Agrega un turno utilizando el id seleccionado.
     */
    @FXML
    private void agregarTurno(ActionEvent event) {

        int idTurno = spnIdTurno.getValue();

        // Aquí posteriormente buscarás el turno en la BD
        // y lo agregarás a la lista.

        System.out.println("Agregar turno con id: " + idTurno);
    }

    /**
     * Configura la columna de eliminación.
     */
    private void configurarColumnaEliminar() {

        colBotonEliminar.setCellFactory(param -> new TableCell<>() {

            private final Button btnEliminarFila = new Button("Eliminar"); {
                btnEliminarFila.setStyle("-fx-background-color: #ff4d4d; " + "-fx-text-fill: white;");

                btnEliminarFila.setOnAction(event -> {

                    Turno turnoSeleccionado =
                            getTableView().getItems().get(getIndex());

                    listaTurnos.remove(turnoSeleccionado);

                    System.out.println(
                            "Eliminar turno: "
                            + turnoSeleccionado.getId()
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
        if (listaTurnos == null || listaTurnos.isEmpty()) {
            mostrarAlerta("Vacío", "No hay turnos para mostrar.", Alert.AlertType.INFORMATION);
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