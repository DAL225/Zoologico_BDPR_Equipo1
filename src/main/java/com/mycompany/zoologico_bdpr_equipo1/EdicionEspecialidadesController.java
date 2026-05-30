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

public class EdicionEspecialidadesController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private Button btnCerrar;

    @FXML
    private TableView<String> tblEspecialidades;

    @FXML
    private TableColumn<String, String> colEspecialidad;

    @FXML
    private TableColumn<String, Void> colBotonEliminar;

    @FXML
    private TextField txtNuevaEspecialidad;

    @FXML
    private Button btnAgregar;

    private ObservableList<String> listaEspecialidades;

    /**
     * Permite asignar la lista de especialidades.
     * Public porque se accede desde la ventana principal.
     *
     * @param listaRecomendaciones lista observable de especialidades
     */
    public void setListaEspecialidades(ObservableList<String> listaRecomendaciones) {
        this.listaEspecialidades = listaRecomendaciones;
        tblEspecialidades.setItems(this.listaEspecialidades);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Configuracion columna especialidad
        // Para String se usa "value"
        colEspecialidad.setCellValueFactory( new PropertyValueFactory<>("value"));

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

        String nuevaEspecialidad
                = txtNuevaEspecialidad.getText().trim();

        if (nuevaEspecialidad.isBlank()) {
            mostrarAlerta("Campo vacio", "Ingrese una especialidad", Alert.AlertType.WARNING);
            return;
        }

        listaEspecialidades.add(nuevaEspecialidad);

        txtNuevaEspecialidad.clear();

        System.out.println("Especialidad agregada: " + nuevaEspecialidad);
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

                    String especialidadSeleccionada
                            = getTableView()
                                    .getItems()
                                    .get(getIndex());

                    listaEspecialidades.remove(especialidadSeleccionada);

                    System.out.println("Eliminar especialidad: " + especialidadSeleccionada);
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

        if (listaEspecialidades == null
                || listaEspecialidades.isEmpty()) {

            mostrarAlerta("Vacio", "No hay especialidades para mostrar", Alert.AlertType.INFORMATION);
            return;
        }

        tblEspecialidades.setItems(listaEspecialidades);
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
