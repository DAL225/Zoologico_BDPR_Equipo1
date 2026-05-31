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

public class EdicionHabitatsController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

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

    @FXML
    private TableColumn<Habitat, Void> colBotonEliminar;

    @FXML
    private Spinner<Integer> spnIdHabitat;

    @FXML
    private Button btnAgregar;

    private HabitatDAO habitatDao;

    private List<Integer> listaidHabitats;

    /**
     * Permite asignar la lista de habitats que sera mostrada en la tabla.
     * Public porque se accede desde la ventana principal.
     *
     * @param listaHabitats lista observable de habitats
     */
    public void setListaIdHabitats(List<Integer> listaHabitats) {
        this.listaidHabitats = listaHabitats;
        this.cargarDatos();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Configuracion de columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colClima.setCellValueFactory(new PropertyValueFactory<>("clima"));
        colNivelLimpieza.setCellValueFactory(new PropertyValueFactory<>("nivelLimpieza"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadAnimales"));

        // Configuracion Spinner
        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);

        spnIdHabitat.setValueFactory(valueFactory);

        // Configuracion boton eliminar
        configurarColumnaEliminar();

        // Carga de datos
        cargarDatos();
    }

    /**
     * Cierra la ventana actual.
     */
    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    /**
     * Agrega un habitat a la lista utilizando el id seleccionado.
     *
     * @param event evento del boton
     */
    @FXML
    private void agregarHabitat(ActionEvent event) {

        int idHabitat = spnIdHabitat.getValue();

        System.out.println("Agregar habitat con id: " + idHabitat);

        // Aqui :
        // 2. Validar duplicados for int en listaids
        // 1. Buscar el habitat desde DAO
        // 3. Agregarlo a la lista
        
    }

    /**
     * Configura la columna de eliminacion.
     * Cada fila tendra un boton para eliminar el habitat correspondiente.
     */
    private void configurarColumnaEliminar() {

        colBotonEliminar.setCellFactory(param -> new TableCell<>() {

            private final Button btnEliminarFila = new Button("Eliminar");

            {

                btnEliminarFila.setStyle(
                        "-fx-background-color: #ff4d4d; "
                        + "-fx-text-fill: white;"
                );

                btnEliminarFila.setOnAction(event -> {

                    Habitat habitatSeleccionado
                            = getTableView().getItems().get(getIndex());
                    //se elimina de la lista de ids, y de la tabla
                    listaidHabitats.remove(Integer.valueOf(habitatSeleccionado.getId()));
                    getTableView().getItems().remove(habitatSeleccionado);

                    System.out.println(
                            "Eliminar habitat: "
                            + habitatSeleccionado.getNombre()
                    );
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
     * Carga la lista de habitats en la tabla.
     */
    private void cargarDatos() {
        ObservableList<Habitat> listaHabitats = FXCollections.observableArrayList();
        
        if (listaidHabitats == null || listaidHabitats.isEmpty()) {
            mostrarAlerta("Vacio", "No hay habitats para mostrar", Alert.AlertType.INFORMATION);
            return;
        }
        try{
            habitatDao = new HabitatDAOImpl();
            listaHabitats.addAll(habitatDao.obtenerHabitats(listaidHabitats));
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            this.mostrarAlerta("Error", "Error al obtener datos", Alert.AlertType.NONE);
        }
        
        tblHabitats.setItems(listaHabitats);
    }

    /**
     * Muestra una alerta personalizada.
     *
     * @param titulo titulo de la alerta
     * @param mensaje contenido de la alerta
     * @param tipo tipo de alerta
     */
    private void mostrarAlerta(String titulo, String mensaje,Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}