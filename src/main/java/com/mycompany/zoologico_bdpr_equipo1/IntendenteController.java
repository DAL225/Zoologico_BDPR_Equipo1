/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Habitat;
import Modelo.Dao.HabitatDAO;
import Modelo.Dao.IntendenteDAO;
import Modelo.Impl.HabitatDAOImpl;
import Modelo.Impl.IntendenteDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller del módulo de Intendente.
 * Muestra únicamente los hábitats asignados al usuario.
 */
public class IntendenteController implements Initializable {

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
    private TableColumn<Habitat, Integer> colNivelLimpieza;

    @FXML
    private TableColumn<Habitat, Integer> colCapacidad;

    @FXML
    private Button btnCerrarSesion;

    private HabitatDAO habitatDao;

    private ObservableList<Habitat> listaHabitats;

    private int idEmpleado; // 👈 aquí guardas el id del intendente
    private IntendenteDAO intendenteDao;

    /**
     * Método para recibir el id del empleado logueado.
     */
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
        cargarHabitats();
    }

    /**
     * Inicializa columnas de la tabla.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        listaHabitats = FXCollections.observableArrayList();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colClima.setCellValueFactory(new PropertyValueFactory<>("clima"));
        colNivelLimpieza.setCellValueFactory(new PropertyValueFactory<>("nivelLimpieza"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadAnimales"));
        
        tblHabitats.setItems(listaHabitats);
    }

    /**
     * Carga solo los hábitats asignados al intendente.
     */
    private void cargarHabitats() {

        try {
            intendenteDao = new IntendenteDAOImpl();
            habitatDao = new HabitatDAOImpl();
            listaHabitats.clear();
            
            List<Integer> listaIds = intendenteDao.obtenerIdsHabitats(idEmpleado);
            
            if(listaIds == null || listaIds.isEmpty()){
                mostrarAlerta("Error", "No hay habitats para mostrar",Alert.AlertType.INFORMATION);
                return;
            }
            List<Habitat> listaHabitats = habitatDao.obtenerHabitats(listaIds);
            
            listaHabitats.addAll(listaHabitats);

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los hábitats",Alert.AlertType.ERROR);
        }
    }

    /**
     * Cierra sesión y regresa al login.
     */
    @FXML
    private void cerrarSesion() {
        try {
            // Cargar la vista del login
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/scenes/login.fxml"));
            Parent root = loader.load();

            // Crear nueva ventana
            Stage loginStage = new Stage();
            loginStage.setTitle("Iniciar Sesión");
            loginStage.setScene(new Scene(root));
            loginStage.show();

            // Cerrar ventana actual
            Stage stageActual = (Stage) btnCerrarSesion.getScene().getWindow();
            stageActual.close();

        } catch (IOException e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Muestra alertas.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}