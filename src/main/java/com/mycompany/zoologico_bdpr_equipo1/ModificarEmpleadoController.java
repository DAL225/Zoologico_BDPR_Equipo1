/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Empleado;
import Modelo.Veterinario;
import Modelo.Cuidador;
import Modelo.Intendente;
import Modelo.Dao.EmpleadoDAO;
import Modelo.Impl.EmpleadoDAOImpl;
import Modelo.Turno;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controller para modificar empleados.
 * Usa setVisible para controlar subpaneles según tipo de empleado.
 */
public class ModificarEmpleadoController implements Initializable {

    @FXML private AnchorPane subPnlCamposMod;

    @FXML private TextField txtNombre;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtPassword;

    @FXML private Button btnTurnos;
    @FXML private Button btnModificarDatos;

    @FXML private StackPane stckPane;

    @FXML private AnchorPane subPnlVeterinario;
    @FXML private AnchorPane subPnlCuidador;
    @FXML private AnchorPane subPnlIntendente;

    @FXML private Spinner<Integer> spnIdEmpleado;
    @FXML private Button btnBuscar;

    private Empleado empleadoActual;
    private EmpleadoDAO empleadoDao;

    // mismas estructuras que registrar
    private ObservableList<Turno> listaTurnos;
    private List<Integer> listaIdAnimales;
    private List<Integer> listaIdHabitats;
    private ObservableList<String> listaEspecialidades;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        listaTurnos = FXCollections.observableArrayList();
        listaIdAnimales = new ArrayList<>();
        listaIdHabitats = new ArrayList<>();
        listaEspecialidades = FXCollections.observableArrayList();

        spnIdEmpleado.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );

        // ocultar todo al inicio (IMPORTANTE)
        subPnlCamposMod.setVisible(false);
        subPnlVeterinario.setVisible(false);
        subPnlCuidador.setVisible(false);
        subPnlIntendente.setVisible(false);
    }

    /**
     * BUSCAR EMPLEADO
     */
    @FXML
    private void buscarEmpleado(ActionEvent event) {

        int id = spnIdEmpleado.getValue();

        try {
            empleadoDao = new EmpleadoDAOImpl();

            empleadoActual = empleadoDao.obtenerEmpleado(id);

            if (empleadoActual == null) {
                mostrarAlerta("Error", "Empleado no encontrado", Alert.AlertType.ERROR);
                return;
            }

            // mostrar panel base
            subPnlCamposMod.setVisible(true);

            // cargar datos
            txtNombre.setText(empleadoActual.getNombre());
            txtUsuario.setText(empleadoActual.getUsuario());
            txtPassword.setText(empleadoActual.getPassword());

            // ocultar todos los subpaneles
            subPnlVeterinario.setVisible(false);
            subPnlCuidador.setVisible(false);
            subPnlIntendente.setVisible(false);

            // mostrar según tipo (igual que registrar)
            if (empleadoActual instanceof Veterinario) {
                Veterinario vet = (Veterinario) empleadoActual;
                subPnlVeterinario.setVisible(true);

                listaIdAnimales.clear();
                listaIdAnimales.addAll(vet.getIdsAnimales());

            } else if (empleadoActual instanceof Cuidador) {
                Cuidador cui = (Cuidador) empleadoActual;

                subPnlCuidador.setVisible(true);

                listaIdAnimales.clear();
                listaIdAnimales.addAll(cui.getIdsAnimales());

            } else if (empleadoActual instanceof Intendente) {
                Intendente intd = (Intendente) empleadoActual;
                
                subPnlIntendente.setVisible(true);

                listaIdHabitats.clear();
                listaIdHabitats.addAll(intd.getIdsHabitats());
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al buscar empleado", Alert.AlertType.ERROR);
        }
    }

    /**
     * MODIFICAR DATOS GENERALES
     */
    @FXML
    private void modificarDatos(ActionEvent event) {

        try {
            empleadoDao = new EmpleadoDAOImpl();

            empleadoActual.setNombre(txtNombre.getText().trim());
            empleadoActual.setUsuario(txtUsuario.getText().trim());
            empleadoActual.setPassword(txtPassword.getText().trim());

            // actualizar estructuras también
            if (empleadoActual instanceof Veterinario) {
                Veterinario vet = (Veterinario) empleadoActual;
                vet.setIdsAnimales(listaIdAnimales);
                vet.setEspecialidades(listaEspecialidades);
            }

            if (empleadoActual instanceof Cuidador) {
                Cuidador cui = (Cuidador) empleadoActual;
                cui.setIdsAnimales(listaIdAnimales);
            }

            if (empleadoActual instanceof Intendente) {
                Intendente intd = (Intendente) empleadoActual;
                intd.setIdsHabitats(listaIdHabitats);
            }

            if (empleadoDao.modificarDatos(empleadoActual)) {
                mostrarAlerta("Éxito", "Empleado modificado correctamente",
                        Alert.AlertType.INFORMATION);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo modificar", Alert.AlertType.ERROR);
        }
    }

    /**
     * TURNOS (igual estilo registrar)
     */
    @FXML
    private void modificarTurnos(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionTurnos.fxml"));
            Parent vista = loader.load();

            EdicionTurnosController controller = loader.getController();
            controller.setListaTurnos(listaTurnos);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ANIMALES
     */
    @FXML
    private void modificarAnimales(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionAnimales.fxml"));
            Parent vista = loader.load();

            EdicionAnimalesController controller = loader.getController();
            controller.setListaIdAnimales(listaIdAnimales);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ESPECIALIDADES (Veterinario)
     */
    @FXML
    private void modificarEspecialidades(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionEspecialidades.fxml"));
            Parent vista = loader.load();

            EdicionEspecialidadesController controller = loader.getController();
            controller.setListaEspecialidades(listaEspecialidades);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HÁBITATS (Intendente)
     */
    @FXML
    private void modificarHabitats(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionHabitats.fxml"));
            Parent vista = loader.load();

            EdicionHabitatsController controller = loader.getController();
            controller.setListaIdHabitats(listaIdHabitats);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra una alerta personalizada.
     *
     * @param titulo titulo de la alerta
     * @param mensaje contenido de la alerta
     * @param tipo tipo de alerta
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}