package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Dao.HabitatDAO;
import Modelo.Habitat;
import Modelo.Impl.HabitatDAOImpl;
import java.net.URL;
import java.util.ArrayList; // Importación para blindar listas mutables
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
import javafx.scene.control.TableRow; // Necesario para una eliminación segura
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EdicionHabitatsController implements Initializable {

    @FXML private AnchorPane pnlPrincipal;
    @FXML private Button btnCerrar;
    @FXML private TableView<Habitat> tblHabitats;
    @FXML private TableColumn<Habitat, Integer> colId;
    @FXML private TableColumn<Habitat, String> colNombre;
    @FXML private TableColumn<Habitat, String> colTipo;
    @FXML private TableColumn<Habitat, String> colClima;
    @FXML private TableColumn<Habitat, String> colNivelLimpieza;
    @FXML private TableColumn<Habitat, Integer> colCapacidad;
    @FXML private TableColumn<Habitat, Void> colBotonEliminar;
    @FXML private Spinner<Integer> spnIdHabitat;
    @FXML private Button btnAgregar;

    private HabitatDAO habitatDao;
    private List<Integer> listaidHabitats;

    /**
     * Permite asignar la lista de habitats que sera mostrada en la tabla.
     * @param listaHabitats lista de ids de habitats proveniente del Intendente o Administrador
     */
    /**
     * Recibe la referencia exacta de la lista del padre.
     * Al no crear un 'new ArrayList', ambas ventanas comparten la misma memoria.
     */
    public void setListaIdHabitats(List<Integer> listaHabitats) {
        if (listaHabitats == null) {
            this.listaidHabitats = new ArrayList<>();
        } else {
            // Guardamos la referencia DIRECTA
            this.listaidHabitats = listaHabitats; 
        }
        cargarDatos();
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
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        spnIdHabitat.setValueFactory(valueFactory);

        // Configuracion boton eliminar
        configurarColumnaEliminar();
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
     */
    @FXML
    private void agregarHabitat(ActionEvent event) {
        int idAgregar = spnIdHabitat.getValue();

        if (listaidHabitats != null && !listaidHabitats.isEmpty()) {
            for (int idAux : listaidHabitats) {
                if (idAgregar == idAux) {
                    mostrarAlerta("Existente", "El hábitat ya se encuentra agregado en la lista actual.", Alert.AlertType.INFORMATION);
                    return;
                }
            }
        }
        
        try {
            habitatDao = new HabitatDAOImpl();
            Habitat habitatAux = habitatDao.obtenerHabitat(idAgregar);
            
            if (habitatAux != null) {
                this.listaidHabitats.add(idAgregar);
                mostrarAlerta("Éxito", "Hábitat agregado correctamente", Alert.AlertType.INFORMATION);
                cargarDatos();
                return;
            }
            mostrarAlerta("Fallo", "No se encontró ningún hábitat con el ID especificado.", Alert.AlertType.WARNING);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al validar el hábitat: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Configura la columna de eliminacion de forma segura mediante TableRow.
     */
    private void configurarColumnaEliminar() {
        colBotonEliminar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEliminarFila = new Button("Eliminar");

            {
                btnEliminarFila.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");

                btnEliminarFila.setOnAction(event -> {
                    // Corrección: Uso de TableRow en lugar de getIndex() para evitar bugs al ordenar columnas
                    TableRow<Habitat> fila = getTableRow();
                    if (fila == null || fila.getItem() == null) return;
                    
                    Habitat habitatSeleccionado = fila.getItem();
                    
                    // Se elimina tanto de la lista lógica de IDs como visualmente de la tabla
                    listaidHabitats.remove(Integer.valueOf(habitatSeleccionado.getId()));
                    getTableView().getItems().remove(habitatSeleccionado);

                    System.out.println("Eliminado hábitat de la lista temporal: " + habitatSeleccionado.getNombre());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnEliminarFila);
            }
        });
    }

    /**
     * Carga la lista de habitats en la tabla de manera controlada.
     */
    private void cargarDatos() {
    ObservableList<Habitat> listaHabitats = FXCollections.observableArrayList();
    
    // CONTROL 1: Verificar si la lista de IDs llega desde la ventana anterior
    if (listaidHabitats == null) {
        System.out.println("[DEBUG-HABITATS] ¡ALERTA! 'listaidHabitats' es NULL.");
        tblHabitats.setItems(listaHabitats);
        return;
    }
    
    System.out.println("[DEBUG-HABITATS] IDs recibidos para buscar: " + listaidHabitats.toString());
    
    if (listaidHabitats.isEmpty()) {
        System.out.println("[DEBUG-HABITATS] La lista de IDs está vacía ([]). Nada que buscar.");
        tblHabitats.setItems(listaHabitats);
        return;
    }
    
    try {
        habitatDao = new HabitatDAOImpl();
        
        // CONTROL 2: Ver qué responde exactamente el DAO
        List<Habitat> consultados = habitatDao.obtenerHabitats(listaidHabitats);
        
        if (consultados == null) {
            System.out.println("[DEBUG-HABITATS] El método 'obtenerHabitats' devolvió un NULL puro.");
        } else {
            System.out.println("[DEBUG-HABITATS] Cantidad de Hábitats devueltos por la BD: " + consultados.size());
            for (Habitat h : consultados) {
                System.out.println("   -> Hábitat encontrado: ID=" + h.getId() + ", Nombre=" + h.getNombre());
            }
            listaHabitats.addAll(consultados);
        }
        
    } catch (Exception e) {
        System.out.println("[DEBUG-HABITATS] ¡Excepción atrapada al consultar la BD!");
        e.printStackTrace();
        this.mostrarAlerta("Error", "Error al obtener datos de los hábitats desde el repositorio", Alert.AlertType.ERROR);
    }
    
    tblHabitats.setItems(listaHabitats);
    System.out.println("[DEBUG-HABITATS] tblHabitats.setItems() ejecutado. Tamaño final en UI: " + tblHabitats.getItems().size());
}
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}