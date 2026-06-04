package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import Modelo.Impl.AnimalDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller para la interfaz del Cuidador.
 * Muestra los animales asignados y permite consultar sus recomendaciones y tratamientos.
 * * @author amiss
 */
public class CuidadorController implements Initializable { 

    @FXML private TableView<Animal> tblAnimales;
    @FXML private TableColumn<Animal, Integer> colId;
    @FXML private TableColumn<Animal, String> colNombreCientifico;
    @FXML private TableColumn<Animal, String> colEspecie;
    @FXML private TableColumn<Animal, Integer> colIdHabitat;
    @FXML private TableColumn<Animal, String> colNombreComun;
    @FXML private TableColumn<Animal, Integer> colEdad;
    @FXML private TableColumn<Animal, String> colSexo;
    @FXML private TableColumn<Animal, String> colEstadoSalud;
    @FXML private TableColumn<Animal, Void> colRecomendaciones;
    @FXML private TableColumn<Animal, Void> colTratamientos;
    @FXML private Button btnCerrarSesion;
    
    private AnimalDAO animalDao;
    private List<Integer> listaIdAnimales;

    /**
     * Permite asignar la lista de la cual cargara los datos de animales
     * @param listaIdAnimales lista de la cual se cargaran datos en la tabla
     */
    public void setListaIdAnimales(List<Integer> listaIdAnimales) {
        this.listaIdAnimales = listaIdAnimales;
        cargarDatos();
    }

    @Override 
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

    /**
     * Agrega los botones para ver datos a la columna recomendaciones
     */
    private void configurarColumnaRecomendaciones() {
        colRecomendaciones.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Ver");

            {
                btn.setOnAction((ActionEvent event) -> {
                    // CORRECCIÓN 2: Uso de TableRow en lugar de getIndex() para evitar desalineación de filas
                    TableRow<Animal> fila = getTableRow();
                    if (fila == null || fila.getItem() == null) return;
                    
                    Animal animal = fila.getItem();

                    if (animal.getRecomendacionesCuidado() == null || animal.getRecomendacionesCuidado().isEmpty()) {
                        mostrarAlerta("Vacío", "No hay recomendaciones para mostrar", Alert.AlertType.INFORMATION);
                        return;
                    } 
                    
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/listaRecomendaciones.fxml"));
                        Parent vista = loader.load();

                        ListaRecomendacionesController controller = loader.getController();
                        controller.setListaRecomendaciones(FXCollections.observableList(animal.getRecomendacionesCuidado()));

                        Stage stage = new Stage();
                        stage.setScene(new Scene(vista));
                        stage.setTitle("Recomendaciones de Cuidado");
                        stage.show();

                        System.out.println("Abrir recomendaciones de " + animal.getNombreCientifico());
                    } catch (Exception e) {
                        e.printStackTrace();
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

    /**
     * Agrega los botones para ver datos a la columna tratamientos
     */
    private void configurarColumnaTratamientos() {
        colTratamientos.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Ver");

            {
                btn.setOnAction(event -> {
                    // CORRECCIÓN 2: Uso de TableRow para un rastreo preciso del objeto en la fila
                    TableRow<Animal> fila = getTableRow();
                    if (fila == null || fila.getItem() == null) return;

                    Animal animal = fila.getItem();

                    if (animal.getTratamientos() == null || animal.getTratamientos().isEmpty()) {
                        mostrarAlerta("Vacío", "No hay tratamientos para mostrar.", Alert.AlertType.INFORMATION);
                        return;
                    } 

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/listaTratamientos.fxml"));
                        Parent vista = loader.load();

                        ListaTratamientosController controller = loader.getController();
                        controller.setListaTratamientos(FXCollections.observableList(animal.getTratamientos()));

                        Stage stage = new Stage();
                        stage.setScene(new Scene(vista));
                        stage.setTitle("Tratamientos del Animal");
                        stage.show();

                        System.out.println("Abrir tratamientos de " + animal.getNombreCientifico());
                    } catch (Exception e) {
                        e.printStackTrace();
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
    
    /**
     * Carga de datos a la tabla según las asignaciones del cuidador autenticado.
     */
    private void cargarDatos() {
        ObservableList<Animal> lista = FXCollections.observableArrayList();

        try {
            animalDao = new AnimalDAOImpl();
            
            // Verificación defensiva antes de ir a consultar la BD
            if (listaIdAnimales == null || listaIdAnimales.isEmpty()) {
                mostrarAlerta("Sin asignaciones", "No tienes animales asignados actualmente.", Alert.AlertType.INFORMATION);
                tblAnimales.setItems(lista);
                return;
            } 
            
            lista.addAll(animalDao.obtenerAnimales(listaIdAnimales));

            if (lista.isEmpty()) {
                mostrarAlerta("Vacío", "No hay elementos para mostrar.", Alert.AlertType.INFORMATION);
                tblAnimales.setItems(lista);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar los datos de los animales asignados", Alert.AlertType.ERROR);
        }

        tblAnimales.setItems(lista);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}