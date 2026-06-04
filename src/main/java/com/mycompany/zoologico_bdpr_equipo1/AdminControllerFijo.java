package com.mycompany.zoologico_bdpr_equipo1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author amiss
 */
public class AdminControllerFijo {

    @FXML private ChoiceBox<String> opcionesMenu;
    @FXML private AnchorPane subPnl;
    @FXML private Button btnCerrarSesion;

    private String seccionActiva = "";

    @FXML
    private void initialize() {
        opcionesMenu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cambiarVistaContenedor(newValue);
            }
        });
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
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicEmpleados() {
        seccionActiva = "Empleados";
        opcionesMenu.setItems(FXCollections.observableArrayList(
                "Registrar Empleado",
                "Modificar Empleado",
                "Eliminar Empleado",
                "Consultar Lista Empleados"
        ));
    }

    @FXML
    private void clicTurnos() {
        seccionActiva = "Turnos";
        opcionesMenu.setItems(FXCollections.observableArrayList(
                "Registrar Turno",
                "Modificar Turno",
                "Eliminar Turno",
                "Consultar Lista Turnos"
        ));
    }

    @FXML
    private void clicHabitats() {
        seccionActiva = "Habitats";
        opcionesMenu.setItems(FXCollections.observableArrayList(
                "Registrar Habitat",
                "Modificar Habitat",
                "Eliminar Habitat",
                "Consultar Lista Habitats"
        ));
    }

    @FXML
    private void clicAnimales() {
        seccionActiva = "Animales";
        opcionesMenu.setItems(FXCollections.observableArrayList(
                "Registrar Animal",
                "Modificar Animal",
                "Eliminar Animal",
                "Consultar Lista Animales"
        ));
    }

    /**
     * Cambia la vista del subPnl o abre ventanas independientes según la opción del choiceBox.
     */
    private void cambiarVistaContenedor(String opcionSeleccionada) {
        String archivoFXML = this.fxmlCorrespondiente(opcionSeleccionada);

        if (archivoFXML == null || archivoFXML.isBlank()) {
            return;
        }

        // --- CASO 1: Vistas globales que se abren en ventanas independientes (Stages) ---
        if (archivoFXML.equals("listaTurnos.fxml") 
                || archivoFXML.equals("listaHabitats.fxml") 
                || archivoFXML.equals("listaAnimales.fxml") 
                || archivoFXML.equals("listaEmpleados.fxml")) {
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/" + archivoFXML));
                Parent root = loader.load();
                Stage stage = new Stage();

                // CONFIGURACIÓN ESPECÍFICA SI ES LA LISTA DE ANIMALES GENERAL
                if (archivoFXML.equals("listaAnimales.fxml")) {
                    ListaAnimalesController controller = loader.getController();
                    // Pasamos null para indicarle que cargue absolutamente todos los animales del zoo
                    controller.setListaIdAnimales(null);
                    stage.setTitle("Lista General de Animales");
                } 
                
                else if (archivoFXML.equals("listaEmpleados.fxml")) {
                    stage.setTitle("Lista General de Empleados");
                } 
                else if (archivoFXML.equals("listaTurnos.fxml")) {
                    stage.setTitle("Lista de Turnos");
                } 
                else {
                    ListaHabitatsController controller = loader.getController();
                    // Pasamos null para indicarle que cargue absolutamente todos los animales del zoo
                    controller.setListaHabitats(null);
                    stage.setTitle("Lista General de Animales");
                    stage.setTitle("Lista de Hábitats");
                }

                stage.setScene(new Scene(root));
                stage.show();
                System.out.println("Mostrando ventana independiente: " + archivoFXML);

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar la vista de la tabla flotante", Alert.AlertType.ERROR);
            }
        } 
        // --- CASO 2: Formularios que se incrustan dentro del panel crema (subPnl) ---
        else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/" + archivoFXML));
                Parent nuevaVista = loader.load();

                subPnl.getChildren().setAll(nuevaVista);
                subPnl.setVisible(true);
                System.out.println("Incrustando formulario en panel: " + archivoFXML);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al cargar el panel interno: " + archivoFXML);
            }
        }
    }

    private String fxmlCorrespondiente(String opcionSeleccionada) {
        switch (opcionSeleccionada) {
            case "Registrar Empleado":       return "registrarEmpleado.fxml";
            case "Modificar Empleado":       return "modificarEmpleado.fxml";
            case "Eliminar Empleado":         return "eliminarEmpleado.fxml";
            case "Consultar Lista Empleados": return "listaEmpleados.fxml";
            case "Registrar Turno":          return "registrarTurno.fxml";
            case "Modificar Turno":          return "modificarTurno.fxml";
            case "Eliminar Turno":           return "eliminarTurno.fxml";
            case "Consultar Lista Turnos":    return "listaTurnos.fxml";
            case "Registrar Habitat":        return "registrarHabitat.fxml";
            case "Modificar Habitat":        return "modificarHabitat.fxml";
            case "Eliminar Habitat":         return "eliminarHabitat.fxml";
            case "Consultar Lista Habitats":  return "listaHabitats.fxml";
            case "Registrar Animal":         return "registrarAnimal.fxml";
            case "Modificar Animal":         return "modificarAnimal.fxml";
            case "Eliminar Animal":          return "eliminarAnimal.fxml";
            case "Consultar Lista Animales":  return "listaAnimales.fxml";
            default: return "";
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}