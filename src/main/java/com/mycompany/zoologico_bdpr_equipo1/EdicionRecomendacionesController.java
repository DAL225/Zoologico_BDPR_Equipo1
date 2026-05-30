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

public class EdicionRecomendacionesController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private Button btnCerrar;

    @FXML
    private TableView<String> tblRecomendaciones;

    @FXML
    private TableColumn<String, String> colRecomendacion;

    @FXML
    private TableColumn<String, Void> colBotonEliminar;

    @FXML
    private TextField txtNuevaRecomendacion;

    @FXML
    private Button btnAgregar;

    private ObservableList<String> listaRecomendaciones;

    /**
     * Permite asignar la lista de recomendaciones.
     * Public porque se accede desde la ventana principal.
     *
     * @param listaRecomendaciones lista observable de recomendaciones
     */
    public void setListaRecomendaciones(ObservableList<String> listaRecomendaciones) {
        this.listaRecomendaciones = listaRecomendaciones;
        tblRecomendaciones.setItems(this.listaRecomendaciones);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Configuracion columna recomendacion
        // Para String se usa "value"
        colRecomendacion.setCellValueFactory( new PropertyValueFactory<>("value"));

        // Configuracion columna eliminar
        configurarColumnaEliminar();

        // Carga datos
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
     * Agrega una nueva recomendacion a la lista.
     *
     * @param event evento del boton
     */
    @FXML
    private void agregarRecomendacion(ActionEvent event) {

        String nuevaRecomendacion
                = txtNuevaRecomendacion.getText().trim();

        if (nuevaRecomendacion.isEmpty()) {

            mostrarAlerta(
                    "Campo vacio",
                    "Ingrese una recomendacion",
                    Alert.AlertType.WARNING
            );

            return;
        }

        listaRecomendaciones.add(nuevaRecomendacion);

        txtNuevaRecomendacion.clear();

        System.out.println(
                "Recomendacion agregada: "
                + nuevaRecomendacion
        );
    }

    /**
     * Configura la columna de eliminacion.
     * Cada fila tendra un boton para eliminar la recomendacion.
     */
    private void configurarColumnaEliminar() {

        colBotonEliminar.setCellFactory(param -> new TableCell<>() {

            private final Button btnEliminarFila = new Button("Eliminar"); {

                btnEliminarFila.setStyle("-fx-background-color: #ff4d4d; " + "-fx-text-fill: white;");

                btnEliminarFila.setOnAction(event -> {

                    String recomendacionSeleccionada
                            = getTableView()
                                    .getItems()
                                    .get(getIndex());

                    listaRecomendaciones.remove(recomendacionSeleccionada);

                    System.out.println("Eliminar recomendacion: " + recomendacionSeleccionada);
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
     * Carga las recomendaciones en la tabla.
     */
    private void cargarDatos() {

        if (listaRecomendaciones == null
                || listaRecomendaciones.isEmpty()) {

            mostrarAlerta("Vacio", "No hay recomendaciones para mostrar", Alert.AlertType.INFORMATION);
            return;
        }

        tblRecomendaciones.setItems(listaRecomendaciones);
    }

    /**
     * Muestra una alerta personalizada.
     *
     * @param titulo titulo de la alerta
     * @param mensaje contenido de la alerta
     * @param tipo tipo de alerta
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}
