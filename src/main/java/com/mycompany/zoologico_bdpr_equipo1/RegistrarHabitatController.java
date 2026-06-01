/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Dao.HabitatDAO;
import Modelo.Dao.TurnoDAO;
import Modelo.Habitat;
import Modelo.Impl.HabitatDAOImpl;
import Modelo.Turno;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class RegistrarHabitatController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private TextField txtNombre;

    @FXML
    private ChoiceBox<String> chkTipo;

    @FXML
    private TextField txtClima;

    @FXML
    private ChoiceBox<String> chkNivelLimpieza;

    @FXML
    private Spinner<Integer> spnCapacidad;

    @FXML
    private Button btnRegistrarHabitat;
    
    private HabitatDAO habitatDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Tipos de hábitat
        chkTipo.getItems().addAll(
                "Terrestre",
                "Acuatico",
                "Aereo",
                "Mixto"
        );

        // Niveles de limpieza
        chkNivelLimpieza.getItems().addAll(
                "Alto",
                "Medio",
                "Bajo"
        );

        // Spinner de capacidad
        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000,1);
        spnCapacidad.setValueFactory(valueFactory);
    }

    @FXML
    private void registrarHabitat(ActionEvent event) {

        // Recuperar datos
        String nombre = txtNombre.getText().trim();
        String tipo = chkTipo.getValue();
        String clima = txtClima.getText().trim();
        String nivelLimpieza = chkNivelLimpieza.getValue();
        Integer capacidad = spnCapacidad.getValue();

        
        
        //Verifica los campos obligatorios
        if (nombre.isBlank() || tipo == null) {
            mostrarAlerta("Campos incompletos", "Debe completar todos los campos obligatorios", Alert.AlertType.WARNING);
            return;
        }

        try{
            habitatDao = new HabitatDAOImpl();
            int iDDisponible = habitatDao.obtenerIdDisponible();
            
            Habitat habitatAux = new Habitat();
            habitatAux.setId(iDDisponible);
            habitatAux.setNombre(nombre);
            habitatAux.setTipo(tipo);
            habitatAux.setClima(clima);
            habitatAux.setNivelLimpieza(nivelLimpieza);
            habitatAux.setCapacidadAnimales(capacidad);
            if (habitatDao.agregarHabitat(habitatAux)) {
                mostrarAlerta("Registro", "Elemento registrado correctamente.", Alert.AlertType.INFORMATION);
                this.limpiarCampos();
                return;
            }
            mostrarAlerta("Aviso", "Fracaso al registrar", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.INFORMATION);
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
    
    /**
     * Limpia los campos de texto, desmarca los ChoiceBox y reinicia el Spinner
     * a su valor inicial.
     */
    private void limpiarCampos() {
        // Limpiar TextFields
        txtNombre.clear();
        txtClima.clear();

        // Restablecer ChoiceBox (quita la selección actual)
        chkTipo.getSelectionModel().clearSelection();
        chkNivelLimpieza.getSelectionModel().clearSelection();

        // Reiniciar Spinner al valor mínimo inicial (1)
        if (spnCapacidad.getValueFactory() != null) {
            spnCapacidad.getValueFactory().setValue(1);
        }
    }
}
