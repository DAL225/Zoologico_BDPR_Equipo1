/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Cuidador;
import Modelo.Dao.AnimalDAO;
import Modelo.Dao.EmpleadoDAO;
import Modelo.Empleado;
import Modelo.Impl.AnimalDAOImpl;
import Modelo.Impl.EmpleadoDAOImpl;
import Modelo.Veterinario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ListaEmpleadosController implements Initializable {

    @FXML
    private Button btnCerrar;

    @FXML
    private TableView<Empleado> tblEmpleados;

    @FXML
    private TableColumn<Empleado, Integer> colId;

    @FXML
    private TableColumn<Empleado, String> colNombre;

    @FXML
    private TableColumn<Empleado, String> colUsuario;

    @FXML
    private TableColumn<Empleado, String> colPassword;

    @FXML
    private TableColumn<Empleado, Void> colTurnos;

    @FXML
    private TableColumn<Empleado, String> colTipo;

    @FXML
    private TableColumn<Empleado, Void> colEspecialidades;

    @FXML
    private TableColumn<Empleado, Void> colAnimalesAsignados;

    @FXML
    private TableColumn<Empleado, Void> colHabitatsAsignados;

    EmpleadoDAO empleadoDao;
    
    AnimalDAO animalDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        configurarColumnaTurnos();
        configurarColumnaAnimales();
        configurarColumnaEspecialidades();
        configurarColumnaHabitats();

        cargarDatos();
    }

    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    private void configurarColumnaTurnos() {

        colTurnos.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Ver"); {
                btn.setOnAction(event -> {

                    Empleado empleado = getTableView().getItems().get(getIndex());

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("listaTurnos.fxml"));
                        Parent vista = loader.load();

                        // Obtener controller
                        ListaTurnosController controller = loader.getController();

                        // Pasar datos
                        controller.setListaTurnos(FXCollections.observableList(empleado.getTurnos()));

                        Stage stage = new Stage();
                        stage.setScene(new Scene(vista));
                        stage.show();

                        System.out.println("Turnos de " + empleado.getUsuario());
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

    private void configurarColumnaAnimales() {

        colAnimalesAsignados.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Ver");

            {
                btn.setOnAction(event -> {

                    Empleado empleado = getTableView().getItems().get(getIndex());

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("listaAnimales.fxml"));
                        Parent vista = loader.load();

                        // Obtener controller
                        ListaAnimalesController controller = loader.getController();
                        animalDao = new AnimalDAOImpl();

                        ObservableList<Animal> listaAnimalesAux = FXCollections.observableArrayList();
//                        if (empleado instanceof Cuidador cuidador) {
//                            //Por cada idAlmacenado buscara el animal y lo guardara,
//                            //para luego con los datos mostrarlos en la interfaz/tabla
//                            for(int id : cuidador.getIdsAnimales()){
//                                Animal animalAux = animalDao.obtenerAnimal(id);
//                                listaAnimalesAux.add(animalAux);
//                            }
//                            controller.setListaAnimales(listaAnimalesAux);
//
//                        } else if (empleado instanceof Veterinario veterinario) {
//                            //Por cada idAlmacenado buscara el animal y lo guardara,
//                            //para luego con los datos mostrarlos en la interfaz/tabla
//                            for(int id : veterinario.getIdsAnimales()){
//                                Animal animalAux = animalDao.obtenerAnimal(id);
//                                listaAnimalesAux.add(animalAux);
//                            }
//                            controller.setListaAnimales(listaAnimalesAux);
//
//                        }

                        Stage stage = new Stage();
                        stage.setScene(new Scene(vista));
                        stage.show();

                        System.out.println("Turnos de " + empleado.getUsuario());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }

                Empleado empleado = getTableView().getItems().get(getIndex());

                String tipo = empleado.getTipo();
                //Verifica el tipo, para mostrar o no el boton
                if (tipo.equals("Veterinario") || tipo.equals("Cuidador")) {
                    setGraphic(btn);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private void configurarColumnaEspecialidades() {

        colEspecialidades.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Ver");

            {
                btn.setOnAction(event -> {

                    Empleado empleado =
                            getTableView().getItems().get(getIndex());

                    System.out.println(
                            "Especialidades de "
                            + empleado.getNombre());

                    // Abrir ListaEspecialidades
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                Empleado empleado =
                        getTableView().getItems().get(getIndex());

                if (empleado.getTipo().equals("Veterinario")) {
                    setGraphic(btn);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private void configurarColumnaHabitats() {

        colHabitatsAsignados.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Ver");

            {
                btn.setOnAction(event -> {

                    Empleado empleado =
                            getTableView().getItems().get(getIndex());

                    System.out.println(
                            "Hábitats de "
                            + empleado.getNombre());

                    // Abrir ListaHabitats
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                Empleado empleado =
                        getTableView().getItems().get(getIndex());

                if (empleado.getTipo().equals("Intendente")) {
                    setGraphic(btn);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    /**
     * Cargar datos a la tabla
     */
    private void cargarDatos() {
        ObservableList<Empleado> lista = FXCollections.observableArrayList();

        try {
//            empleadoDao = new EmpleadoDAOImpl();
//
//            lista = FXCollections.observableArrayList(empleadoDao.obtenerEmpleados());
//
//            if (lista == null || lista.isEmpty()) {
//                mostrarAlerta("Vacio", "No hay empleados para mostrar ", Alert.AlertType.INFORMATION);
//                return;
//            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
        }
        
        tblEmpleados.setItems(lista);
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