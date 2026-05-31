/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import java.net.URL;
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
public class ListaAnimalesController {

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
    private Button btnCerrar;
    
    private AnimalDAO animalDao;

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
     * Cierra la ventana.
     */
    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
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
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("listaRecomendaciones.fxml"));
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
                        mostrarAlerta("Vacío", "No hay animales para mostrar.", Alert.AlertType.INFORMATION);
                        return;
                    } else {

                        try {//abre la ventana de tratamientos del animal cuyo boton ver se le de clic
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("listaTratamientos.fxml"));
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
     * Carga los datos en la tabla.
     */
    private void cargarDatos() {

//        if (listaAnimales == null || listaAnimales.isEmpty()) {
//            mostrarAlerta("Vacío", "No hay animales para mostrar.", Alert.AlertType.INFORMATION);
//            return;
//        }
//
//        tblAnimales.setItems(listaAnimales);
    }
    
//    /**
//     * Carga de datos a la tabla.
//     */
//    private void cargarDatos() {
//        ObservableList<Animal> lista = FXCollections.observableArrayList();
//        
//        try{
//            animalDao = new EmpleadoDAOImpl();
//            
//            lista = FXCollections.observableArrayList(empleadoDao.obtenerEmpleados());
//            
//            if (lista == null || lista.isEmpty()){
//                mostrarAlerta("Vacio", "No hay elementos para mostrar ", Alert.AlertType.INFORMATION);
//                forzarCierre();
//                return;
//            }
//        } catch (Exception e) {
//        mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
//        forzarCierre();
//    }
//        
//        tblEmpleados.setItems(lista);
//        
//    }

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
