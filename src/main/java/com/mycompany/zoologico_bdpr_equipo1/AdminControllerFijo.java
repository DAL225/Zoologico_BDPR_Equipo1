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
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author amiss
 */
public class AdminControllerFijo {

    @FXML
    private ChoiceBox<String> opcionesMenu;
    @FXML
    private AnchorPane subPnl;

    // Esta variable nos ayudará a saber qué botón lateral está activo
    private String seccionActiva = "";

    @FXML
    private void initialize() { // Simplificado para JavaFX moderno
        // Escuchamos cuando el usuario seleccione algo en el ChoiceBox
        opcionesMenu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cambiarVistaContenedor(newValue);
            }
        });
    }
    
    @FXML
    public void cerrarSesion() throws IOException {
        App.setRoot("login");
    }

    /**
     * Clic en el botón Empleados, cambia los valores del choice Box
     */
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

    /**
     * Clic en el botón Turnos, cambia los valores del choice Box
     */
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

    /**
     * Clic en el botón Habitats, cambia los valores del choice Box
     */
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

    /**
     * Clic en el botón Animales, cambia los valores del choice Box
     */
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
     * Cambia la vista del subPnl, según la opción del choiceBox.
     */
    private void cambiarVistaContenedor(String opcionSeleccionada) {
        String archivoFXML = this.fxmlCorrespondiente(opcionSeleccionada);

        // CORRECCIÓN: Si está vacío, entónces SÍ salimos del método para evitar errores.
        if (archivoFXML == null || archivoFXML.isBlank()) {
            return;
        }

        // Abre una nueva ventana para las listas de elementos
        if (archivoFXML.equals("listaEmpleados.fxml")
                || archivoFXML.equals("listaTurnos.fxml")
                || archivoFXML.equals("listaHabitats.fxml")
                || archivoFXML.equals("listaAnimales.fxml")) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/scenes/" + archivoFXML)
                );

                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Productos del Menú");
                stage.setScene(new Scene(root));
                stage.show();

                System.out.println("Mostrando " + archivoFXML);

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar la vista de la tabla", Alert.AlertType.ERROR);
            }

        } // Cambia el subPanel por la vista del formulario según la opción seleccionada
        else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/" + archivoFXML));
                Parent nuevaVista = loader.load();

                // Limpiamos el panel crema y metemos la nueva interfaz
                subPnl.getChildren().setAll(nuevaVista);
                subPnl.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error al cargar el panel: " + archivoFXML);
            }
        }
    }

    /**
     * Regresa el nombre del fxml que corresponda según la opción del choiceBox.
     */
    private String fxmlCorrespondiente(String opcionSeleccionada) {
        switch (opcionSeleccionada) {
            case "Registrar Empleado": 
                return "registrarEmpleado.fxml";
            case "Modificar Empleado": 
                return "modificarEmpleado.fxml";
            case "Eliminar Empleado": 
                return "eliminarEmpleado.fxml";
            case "Consultar Lista Empleados": 
                return "listaEmpleados.fxml";
            case "Registrar Turno": 
                return "registrarTurno.fxml";
            case "Modificar Turno": 
                return "modificarTurno.fxml";
            case "Eliminar Turno": 
                return "eliminarTurno.fxml";
            case "Consultar Lista Turnos": 
                return "listaTurnos.fxml";
            case "Registrar Habitat": 
                return "registrarHabitat.fxml";
            case "Modificar Habitat": 
                return "modificarHabitat.fxml";
            case "Eliminar Habitat": 
                return "eliminarHabitat.fxml";
            case "Consultar Lista Habitats": 
                return "listaHabitats.fxml";
            case "Registrar Animal": 
                return "registrarAnimal.fxml";
            case "Modificar Animal": 
                return "modificarAnimal.fxml";
            case "Eliminar Animal": 
                return "eliminarAnimal.fxml";
            case "Consultar Lista Animales": 
                return "listaAnimales.fxml";
            default: return "";
        }
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