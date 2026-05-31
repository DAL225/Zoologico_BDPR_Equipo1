package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import Modelo.Impl.AnimalDAOImpl;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EdicionAnimalesController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private Button btnCerrar;

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
    private TableColumn<Animal, Void> colBotonEliminar;

    @FXML
    private Spinner<Integer> spnIdAnimal;

    @FXML
    private Button btnAgregar;

    private AnimalDAO animalDao;
            
    private List<Integer> listaIdAnimales;

    /**
     * Permite asignar la lista de la cual cargara los datos de animales
     * public porque se accede desde la ventana padre del empleado a modificar o agregar
     * @param listaAnimales lista de la cual se cargaran datos en la tabla
     */
    public void setListaIdAnimales(List<Integer> listaIdAnimales) {
        this.listaIdAnimales = listaIdAnimales;
        cargarDatos();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización de componentes, configuración de columnas y carga de datos.
        // Configuración de los CellValueFactory para enlazar la clase Animal con las columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCientifico.setCellValueFactory(new PropertyValueFactory<>("nombreCientifico"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colIdHabitat.setCellValueFactory(new PropertyValueFactory<>("idHabitat"));
        colNombreComun.setCellValueFactory(new PropertyValueFactory<>("nombreComun"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colEstadoSalud.setCellValueFactory(new PropertyValueFactory<>("estadoSalud"));

        // Inicialización del Spinner. Configura un rango de 1 a 1000
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        spnIdAnimal.setValueFactory(valueFactory);

        // 3. Configuración de la columna con el Botón Eliminar
        configurarColumnaEliminar();

        // método para cargar los datos desde la base de datos
        cargarDatos();
        
        
    }

    // Método cerrar ventana
    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void agregarAnimal(ActionEvent event) {
        
        // 1. Validar duplicados/numero
        // 2. Buscar el animal desde DAO
        // 3. Agregarlo a la lista
    }

    /**
     * Configura a cada fila con el boton eliminar.
     * Se encarga de eliminar esa fila de la listaAnimales que apunta a la principal
     * del stage modificar/agregar
     */
    private void configurarColumnaEliminar() {
        colBotonEliminar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEliminarFila = new Button("Eliminar");

            {
                btnEliminarFila.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");

                btnEliminarFila.setOnAction(event -> {

                    Animal animalSeleccionado
                            = getTableView().getItems().get(getIndex());

                    //se elimina de la lista de ids, y de la tabla
                    listaIdAnimales.remove(Integer.valueOf(animalSeleccionado.getId()));
                    getTableView().getItems().remove(animalSeleccionado);

                    System.out.println("Eliminar a: "
                            + animalSeleccionado.getNombreComun());
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
     * Carga de datos a la tabla.
     */
    private void cargarDatos() {
        ObservableList<Animal> listaAnimales = FXCollections.observableArrayList();

        if (listaIdAnimales == null || listaIdAnimales.isEmpty()) {
            mostrarAlerta("Vacio", "No hay animales para mostrar", Alert.AlertType.INFORMATION);
            return;
        }
        try {
            animalDao = new AnimalDAOImpl();
            listaAnimales.addAll(animalDao.obtenerAnimales(listaIdAnimales));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.mostrarAlerta("Error", "Error al obtener datos", Alert.AlertType.NONE);
        }

        tblAnimales.setItems(listaAnimales);
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
