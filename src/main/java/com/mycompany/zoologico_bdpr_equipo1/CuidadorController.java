/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import Modelo.Impl.AnimalDAOImpl;
import Modelo.Impl.EmpleadoDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author amiss
 */
public class CuidadorController {

    @FXML
    private TableView<Animal> tblAnimales;

    @FXML
    private TableColumn<Animal, Integer> colId;

    @FXML
    private TableColumn<Animal, String> colNombreCientifico;

    @FXML
    private TableColumn<Animal, String> colEspecie;

    @FXML
    private TableColumn<Animal, Integer> colIdHabitat;

    @FXML
    private TableColumn<Animal, String> colNombreComun;

    @FXML
    private TableColumn<Animal, Integer> colEdad;

    @FXML
    private TableColumn<Animal, String> colSexo;

    @FXML
    private TableColumn<Animal, String> colEstadoSalud;

    @FXML
    private TableColumn<Animal, Void> colRecomendaciones;

    @FXML
    private TableColumn<Animal, Void> colTratamientos;
    
    @FXML
    private Button btnCerrarSesion;
    
    private AnimalDAO animalDao;
    
    private List<Integer> listaIdAnimales;
    

    /**
     * Permite asignar la lista de la cual cargara los datos de animales
     * public porque se accede desde la ventana padre del empleado a modificar o agregar
     * @param listaIdAnimales lista de la cual se cargaran datos en la tabla
     */
    public void setListaIdAnimales(List<Integer> listaIdAnimales) {
        this.listaIdAnimales = listaIdAnimales;
        cargarDatos();
    }

    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCientifico.setCellValueFactory(new PropertyValueFactory<>("nombreCientifico"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colIdHabitat.setCellValueFactory(new PropertyValueFactory<>("idHabitat"));
        colNombreComun.setCellValueFactory(new PropertyValueFactory<>("nombreComun"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colEstadoSalud.setCellValueFactory(new PropertyValueFactory<>("estadoSalud"));

        configurarColumnaRecomendaciones();
        configurarColumnaTratamientos();
    }
    
    /**
     * Cierra sesión y regresa al login.
     */
    @FXML
    private void cerrarSesion() {
        try {
            // Cargar la vista del login
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("login.fxml"));
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
            e.printStackTrace();
        }
    }

    /**
     * Agrega los botones para ver datos a la columna recomendaciones
     */
    private void configurarColumnaRecomendaciones() {

        colRecomendaciones.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Ver");

            {
                btn.setOnAction((ActionEvent event) -> {
                    
                    Animal animal = getTableView().getItems().get(getIndex());

                    
                    if (animal.getRecomendacionesCuidado().isEmpty()) {
                        mostrarAlerta("Vacío", "No hay recomendaciones para mostrar", Alert.AlertType.INFORMATION);
                        return;
                    } else {
                        
                        try {//abre la ventana de recomendaciones del animal cuyo boton ver se le de clic
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/listaRecomendaciones.fxml"));
                            Parent vista = loader.load();

                            // Obtener controller
                            ListaRecomendacionesController controller = loader.getController();

                            // Pasar datos //Parsear de List a Observable
                            controller.setListaRecomendaciones(FXCollections.observableList(animal.getRecomendacionesCuidado()));

                            Stage stage = new Stage();
                            stage.setScene(new Scene(vista));
                            stage.show();

                            System.out.println("Abrir recomendaciones de " + animal.getNombreCientifico());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    /**
     * Agrega los botones para ver datos a la columna tratamientos
     */
    private void configurarColumnaTratamientos() {

        colTratamientos.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Ver");

            {
                btn.setOnAction(event -> {
                    Animal animal = getTableView().getItems().get(getIndex());

                    if (animal.getTratamientos().isEmpty()) {
                        mostrarAlerta("Vacío", "No hay tratamientos para mostrar.", Alert.AlertType.INFORMATION);
                        return;
                    } else {

                        try {//abre la ventana de tratamientos del animal cuyo boton ver se le de clic
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/listaTratamientos.fxml"));
                            Parent vista = loader.load();

                            // Obtener controller
                            ListaTratamientosController controller = loader.getController();

                            // Pasar datos //Parsear de List a Observable
                            controller.setListaTratamientos(FXCollections.observableList(animal.getTratamientos()));

                            Stage stage = new Stage();
                            stage.setScene(new Scene(vista));
                            stage.show();

                            System.out.println("Abrir tratamientos de " + animal.getNombreCientifico());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }
    
    /**
     * Carga de datos a la tabla.
     * si la lista es null, lo llamo el admin
     * si la lista no es null lo llamo un cuidador
     */
    private void cargarDatos() {
        ObservableList<Animal> lista = FXCollections.observableArrayList();

        try {
            animalDao = new AnimalDAOImpl();
            if (listaIdAnimales == null) {
                lista.addAll(animalDao.obtenerTodosAnimales());
                
            } else {
                lista.addAll(animalDao.obtenerAnimales(listaIdAnimales));
            }
            if (lista == null || lista.isEmpty()) {
                mostrarAlerta("Vacio", "No hay elementos para mostrar ", Alert.AlertType.INFORMATION);
                return;
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
        }

        tblAnimales.setItems(lista);
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
