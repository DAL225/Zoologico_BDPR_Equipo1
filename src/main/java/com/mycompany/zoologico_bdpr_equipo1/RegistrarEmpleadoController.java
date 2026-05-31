/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Habitat;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RegistrarEmpleadoController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtPassword;

    @FXML
    private ChoiceBox<String> chkTipoEmpleado;

    @FXML
    private Button btnTurnos;

    @FXML
    private Button btnRegistrarEmpleado;

    @FXML
    private StackPane stckPane;

    @FXML
    private AnchorPane subPnlVeterinario;

    @FXML
    private AnchorPane subPnlCuidador;

    @FXML
    private AnchorPane subPnlIntendente;
    
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

        chkTipoEmpleado.setItems(
                FXCollections.observableArrayList(
                        "Administrador",
                        "Veterinario",
                        "Cuidador",
                        "Intendente"
                )
        );

        chkTipoEmpleado.getSelectionModel().selectedItemProperty().addListener(
                (obs, valorAnterior, nuevoValor) -> actualizarPanel(nuevoValor)
        );

        chkTipoEmpleado.getSelectionModel().selectFirst();
    }

    /**
     * Cambia el subPanel mostrado según el tipo de empleado.
     */
    private void actualizarPanel(String tipoEmpleado) {

        subPnlVeterinario.setVisible(false);
        subPnlCuidador.setVisible(false);
        subPnlIntendente.setVisible(false);

        if (tipoEmpleado == null) {
            return;
        }

        switch (tipoEmpleado) {

            case "Administrador":
                stckPane.setVisible(false);
                break;
            case "Veterinario":
                subPnlCuidador.setVisible(false);
                subPnlIntendente.setVisible(false);
                subPnlVeterinario.setVisible(true);
                break;

            case "Cuidador":
                subPnlIntendente.setVisible(false);
                subPnlVeterinario.setVisible(false);
                subPnlCuidador.setVisible(true);
                break;

            case "Intendente":
                subPnlVeterinario.setVisible(false);
                subPnlCuidador.setVisible(false);
                subPnlIntendente.setVisible(true);
                break;
        }
    }

    /**
     * Abre la ventana de edición de turnos.
     */
    @FXML
    private void agregarTurnos(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionTurnos.fxml"));
        Parent vista = loader.load();

        // Obtener controller
        EdicionTurnosController controller = loader.getController();

        // Pasar datos
        controller.setListaTurnos(listaTurnos);

        Stage stage = new Stage();
        stage.setScene(new Scene(vista));
        stage.show();
        
        System.out.println("Agregar turnos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de edición de animales.
     */
    @FXML
    private void agregarAnimales(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionAnimales.fxml"));
        Parent vista = loader.load();

        // Obtener controller
        EdicionAnimalesController controller = loader.getController();

        // Pasar datos
        controller.setListaIdAnimales(listaIdAnimales);

        Stage stage = new Stage();
        stage.setScene(new Scene(vista));
        stage.show();
        
        System.out.println("Agregar animales");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de edición de especialidades.
     */
    @FXML
    private void agregarEspecialidades(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionRecomendaciones.fxml"));
        Parent vista = loader.load();

        // Obtener controller
        EdicionEspecialidadesController controller = loader.getController();

        // Pasar datos
        controller.setListaEspecialidades(listaEspecialidades);

        Stage stage = new Stage();
        stage.setScene(new Scene(vista));
        stage.show();
        
        System.out.println("Agregar especialidades");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana de edición de hábitats.
     */
    @FXML
    private void agregarHabitats(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionAnimales.fxml"));
        Parent vista = loader.load();

        // Obtener controller
        EdicionHabitatsController controller = loader.getController();

        // Pasar datos
        controller.setListaIdHabitats(listaIdHabitats);

        Stage stage = new Stage();
        stage.setScene(new Scene(vista));
        stage.show();
        
        System.out.println("Agregar habitats");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra el empleado.
     */
    @FXML
    private void registrarEmpleado(ActionEvent event) {

        String nombre = txtNombre.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();
        String tipoEmpleado = chkTipoEmpleado.getValue();

        if (nombre.isEmpty()
                || usuario.isEmpty()
                || password.isEmpty()
                || tipoEmpleado == null) {
            mostrarAlerta("Campos incompletos", "Todos los campos son obligatorios.", Alert.AlertType.WARNING);
        }
        

        // TODO:
        // Crear objeto correspondiente:
        // Veterinario, Cuidador o Intendente
        // y registrarlo mediante DAO.

        mostrarAlerta("Registro", "Empleado registrado correctamente.", Alert.AlertType.INFORMATION);
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