/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Dao.HabitatDAO;
import Modelo.Habitat;
import Modelo.Impl.HabitatDAOImpl;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
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
    
    private HabitatDAO habitatDao;

    private List<Integer> listaIdHabitats;

    /**
     * Permite asignar la lista que se mostrará en la tabla.
     *
     * @param listaIdHabitats lista de ids de hábitats
     */
    public void setListaHabitats(List<Integer> listaIdHabitats) {
        this.listaIdHabitats = listaIdHabitats;
        cargarDatos();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colClima.setCellValueFactory(new PropertyValueFactory<>("clima"));
        colNivelLimpieza.setCellValueFactory(new PropertyValueFactory<>("nivelLimpieza"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadAnimales"));
    }

    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    /**
    /**
     * Carga de datos a la tabla.
     */
    private void cargarDatos() {
        ObservableList<Habitat> lista = FXCollections.observableArrayList();

        try {
            habitatDao = new HabitatDAOImpl();
            if (listaIdHabitats == null) {
                lista.addAll(habitatDao.obtenerTodosHabitats());

            } else {
                lista.addAll(habitatDao.obtenerHabitats(listaIdHabitats));
            }
            
            if (lista.isEmpty()) {
                mostrarAlerta("Vacio", "No hay elementos para mostrar ", Alert.AlertType.INFORMATION);
                tblHabitats.setItems(lista);
                return;
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
        }

        tblHabitats.setItems(lista);
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