package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import Modelo.Impl.AnimalDAOImpl;
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
 * @author amiss
 */
public class VeterinarioController implements Initializable { // SOLUCIÓN 1: Implementar Initializable

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
    private ObservableList<String> tratamientos;

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
        tratamientos = FXCollections.observableArrayList();

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
            e.printStackTrace();
        }
    }

    /**
     * Agrega los botones para editar datos a la columna recomendaciones
     */
    private void configurarColumnaRecomendaciones() {
        colRecomendaciones.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Editar");

            {
                btn.setOnAction((ActionEvent event) -> {
                    // Uso de TableRow para evitar desfaces al ordenar o hacer scroll
                    TableRow<Animal> fila = getTableRow();
                    if (fila == null || fila.getItem() == null) return;
                    
                    Animal animalActual = fila.getItem();

                     
                    
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/edicionRecomendacionesByVeterinario.fxml"));
                        Parent vista = loader.load();

                        EdicionRecomendacionesVetController controller = loader.getController();
                        controller.setIdAnimal(animalActual.getId());

                        Stage stage = new Stage();
                        stage.setScene(new Scene(vista));
                        stage.show();

                        System.out.println("Abrir edición de recomendaciones de " + animalActual.getNombreCientifico());
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
     * Agrega los botones para editar datos a la columna tratamientos
     */
    private void configurarColumnaTratamientos() {
        colTratamientos.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Editar");

            {
                btn.setOnAction(event -> {
                    // Uso de TableRow para evitar desfaces al ordenar o hacer scroll
                    TableRow<Animal> fila = getTableRow();
                    if (fila == null || fila.getItem() == null) return;

                    Animal animalActual = fila.getItem();

                     

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/edicionTratamientosByVeterinario.fxml"));
                        Parent vista = loader.load();

                        EdicionTratamientosVetController controller = loader.getController();
                        controller.setIdAnimal(animalActual.getId());

                        Stage stage = new Stage();
                        stage.setScene(new Scene(vista));
                        stage.show();

                        System.out.println("Abrir edición de tratamientos de " + animalActual.getNombreCientifico());
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
     * Carga de datos a la tabla de acuerdo a los permisos del Veterinario asignado.
     */
    private void cargarDatos() {
        ObservableList<Animal> lista = FXCollections.observableArrayList();

        try {
            animalDao = new AnimalDAOImpl();
            
            // si la lista viene vacía de la BD mongo
            if (listaIdAnimales == null || listaIdAnimales.isEmpty()) {
                mostrarAlerta("Sin asignaciones", "Usted no cuenta con animales asignados a su cargo actualmente.", Alert.AlertType.INFORMATION);
                tblAnimales.setItems(lista);
                return;
            } 
            
            lista.addAll(animalDao.obtenerAnimales(listaIdAnimales));

            if (lista.isEmpty()) {
                mostrarAlerta("Vacío", "No se encontraron los datos de los animales asignados.", Alert.AlertType.INFORMATION);
                tblAnimales.setItems(lista);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar los datos en el panel del especialista", Alert.AlertType.ERROR);
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