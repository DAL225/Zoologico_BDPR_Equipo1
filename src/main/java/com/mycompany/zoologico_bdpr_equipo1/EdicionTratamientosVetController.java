/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import Modelo.Impl.AnimalDAOImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller encargado de la edición de tratamientos veterinarios
 * de un animal en el módulo de veterinario.
 *
 * Se trabaja únicamente con el id del animal y se consulta la BD
 * mediante el DAO.
 */
public class EdicionTratamientosVetController implements Initializable {

    @FXML
    private TableView<String> tblTratamientos;

    @FXML
    private TableColumn<String, String> colTratamiento;

    @FXML
    private TableColumn<String, Void> colBotonEliminar;

    @FXML
    private TextField txtNuevoTratamiento;

    @FXML
    private Button btnCerrar;

    @FXML
    private Button btnGuardarCambios;

    @FXML
    private Button btnAgregar;

    private ObservableList<String> listaTratamientos;

    private Animal animalActual;

    private AnimalDAO animalDao;

    private int idAnimal;

    /**
     * Método llamado desde el controller padre para enviar el id del animal.
     * A partir de este id se cargan los datos desde la base de datos.
     *
     * @param idAnimal identificador del animal a editar
     */
    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
        cargarDatos();
    }

    /**
     * Inicializa componentes gráficos y estructuras de datos.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaTratamientos = FXCollections.observableArrayList();

        colTratamiento.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));

        tblTratamientos.setItems(listaTratamientos);

        configurarColumnaEliminar();
    }

    /**
     * Carga los datos del animal desde la base de datos usando el id.
     */
    private void cargarDatos() {

        try {
            animalDao = new AnimalDAOImpl();
            animalActual = animalDao.obtenerAnimal(idAnimal);

            if (animalActual != null && animalActual.getTratamientos() != null) {
                listaTratamientos.setAll(animalActual.getTratamientos());
                return;
            }
            
            mostrarAlerta("Vacio", "No hay tratamientos para mostrar", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo cargar el animal",Alert.AlertType.ERROR);
        }
    }

    /**
     * Agrega un nuevo tratamiento a la lista observable.
     *
     * @param event evento del botón agregar
     */
    @FXML
    private void agregarTratamiento(ActionEvent event) {

        String texto = txtNuevoTratamiento.getText().trim();

        if (texto.isEmpty()) {
            mostrarAlerta("Error", "Escribe un tratamiento",Alert.AlertType.WARNING);
            return;
        }

        listaTratamientos.add(texto);
        txtNuevoTratamiento.clear();
    }

    /**
     * Guarda los cambios realizados en la base de datos usando el DAO.
     */
    @FXML
    private void guardarCambios(ActionEvent event) {

        try {
            animalDao = new AnimalDAOImpl();

            boolean ok = animalDao.modificarTratamientos(idAnimal,listaTratamientos);

            if (ok) {
                mostrarAlerta("Éxito", "Tratamientos actualizados correctamente",Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudieron guardar los cambios",Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar cambios",Alert.AlertType.ERROR);
        }
    }

    /**
     * Configura el botón de eliminación dentro de la tabla.
     */
    private void configurarColumnaEliminar() {

        colBotonEliminar.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Eliminar");

            {
                btn.setOnAction(e -> {

                    String item = getTableView().getItems().get(getIndex());
                    listaTratamientos.remove(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                setGraphic(empty ? null : btn);
            }
        });
    }

    /**
     * Cierra la ventana actual.
     */
    @FXML
    private void cerrar(ActionEvent event) {

        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra una alerta en pantalla.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}