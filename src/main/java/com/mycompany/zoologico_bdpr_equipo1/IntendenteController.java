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

    @FXML private TableView<Habitat> tblHabitats;
    @FXML private TableColumn<Habitat, Integer> colId;
    @FXML private TableColumn<Habitat, String> colNombre;
    @FXML private TableColumn<Habitat, String> colTipo;
    @FXML private TableColumn<Habitat, String> colClima;
    @FXML private TableColumn<Habitat, Integer> colNivelLimpieza;
    @FXML private TableColumn<Habitat, Integer> colCapacidad;
    @FXML private Button btnCerrarSesion;

    private HabitatDAO habitatDao;
    private ObservableList<Habitat> listaHabitats; // Atributo global de la clase

    private int idEmpleado; 
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
            
            listaHabitats.clear(); // Limpiamos el atributo global
            
            List<Integer> listaIds = intendenteDao.obtenerIdsHabitats(idEmpleado);
            
            if (listaIds == null || listaIds.isEmpty()) {
                mostrarAlerta("Información", "No tienes hábitats asignados a tu cargo actualmente.", Alert.AlertType.INFORMATION);
                return;
            }
            
            // SOLUCIÓN 1: Cambiar el nombre de la variable local para que no opaque al atributo global
            List<Habitat> habitatsConsultados = habitatDao.obtenerHabitats(listaIds);
            
            if (habitatsConsultados == null || habitatsConsultados.isEmpty()) {
                mostrarAlerta("Vacío", "No se encontraron los datos de los hábitats asignados.", Alert.AlertType.INFORMATION);
                return;
            }
            
            // Agregamos los datos consultados a la lista observable global conectada al TableView
            listaHabitats.addAll(habitatsConsultados);

            System.out.println("-> Hábitats cargados con éxito para el intendente: " + listaHabitats.size());

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los hábitats", Alert.AlertType.ERROR);
        }
    }

    /**
     * Cierra sesión y regresa al login.
     */
    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/login.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Iniciar Sesión");
            loginStage.setScene(new Scene(root));
            loginStage.show();

            Stage stageActual = (Stage) btnCerrarSesion.getScene().getWindow();
            stageActual.close();

        } catch (IOException e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}