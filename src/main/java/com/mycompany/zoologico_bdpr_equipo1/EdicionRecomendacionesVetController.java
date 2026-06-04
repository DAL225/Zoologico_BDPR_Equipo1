package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import Modelo.Impl.AnimalDAOImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller encargado de la edición de recomendaciones de cuidado
 * de un animal en el módulo de veterinario.
 */
public class EdicionRecomendacionesVetController implements Initializable {

    @FXML private TableView<String> tblRecomendaciones;
    @FXML private TableColumn<String, String> colRecomendacion;
    @FXML private TableColumn<String, Void> colBotonEliminar;
    @FXML private TextField txtNuevaRecomendacion;
    @FXML private Button btnCerrar;
    @FXML private Button btnGuardarCambios;
    @FXML private Button btnAgregar;

    private ObservableList<String> listaRecomendaciones;
    private Animal animalActual;
    private AnimalDAO animalDao;
    private int idAnimal;

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
        cargarDatos(); // Carga inicial desde la BD
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaRecomendaciones = FXCollections.observableArrayList();

        // Mapea  el String directo a la celda
        colRecomendacion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));
        tblRecomendaciones.setItems(listaRecomendaciones);

        configurarColumnaEliminar();
    }

    /**
     * Carga inicial de datos desde la BD
     */
    private void cargarDatos() {
        try {
            animalDao = new AnimalDAOImpl();
            animalActual = animalDao.obtenerAnimal(idAnimal);

            if (animalActual != null && animalActual.getRecomendacionesCuidado() != null) {
                listaRecomendaciones.setAll(animalActual.getRecomendacionesCuidado());
            } else {
                mostrarAlerta("Vacío", "No hay recomendaciones para mostrar", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo cargar el animal", Alert.AlertType.ERROR);
        }
    }

    /**
     * Agrega localmente a la tabla sin sobreescribir con la BD
     */
    @FXML
    private void agregarRecomendacion(ActionEvent event) {
        String texto = txtNuevaRecomendacion.getText().trim();

        if (texto.isEmpty()) {
            mostrarAlerta("Error", "Escribe una recomendación", Alert.AlertType.WARNING);
            return;
        }

        // Se agrega a la lista local (la tabla se actualiza sola automáticamente)
        listaRecomendaciones.add(texto);
        
        // 
        txtNuevaRecomendacion.clear();
    }

    /**
     * Guarda el estado final de la lista observable en la BD
     */
    @FXML
    private void guardarCambios(ActionEvent event) {
        try {
            animalDao = new AnimalDAOImpl();

            
            boolean ok = animalDao.modificarRecomendaciones(idAnimal, listaRecomendaciones);

            if (ok) {
                mostrarAlerta("Éxito", "Recomendaciones actualizadas correctamente", Alert.AlertType.INFORMATION);
                cargarDatos(); 
            } else {
                mostrarAlerta("Error", "No se pudieron guardar los cambios", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar cambios", Alert.AlertType.ERROR);
        }
    }

    /**
     * obtener el ítem de la fila actual para eliminarlo
     */
    private void configurarColumnaEliminar() {
        colBotonEliminar.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Eliminar");

            {
                btn.setOnAction(e -> {
                    // Forma segura: obtener el objeto asignado a la fila de esta celda
                    String item = getTableRow().getItem();
                    if (item != null) {
                        listaRecomendaciones.remove(item);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    @FXML
    private void cerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}