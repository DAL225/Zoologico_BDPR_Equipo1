/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Dao.HabitatDAO;
import Modelo.Habitat;
import Modelo.Impl.HabitatDAOImpl;
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

public class ModificarHabitatController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private AnchorPane subPnlCamposMod;

    @FXML
    private StackPane stckPane;

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
    private Spinner<Integer> spnIdHabitat;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnModificarDatos;

    private Habitat habitatActual;

    private HabitatDAO habitatDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        chkTipo.getItems().addAll(
                "Terrestre",
                "Acuatico",
                "Aereo",
                "Mixto"
        );

        chkNivelLimpieza.getItems().addAll(
                "Bajo",
                "Medio",
                "Alto"
        );

        spnIdHabitat.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));
        spnCapacidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));

        subPnlCamposMod.setVisible(false);
    }

    @FXML
    private void buscarHabitat(ActionEvent event) {

        int id = spnIdHabitat.getValue();

        try {
            habitatDao = new HabitatDAOImpl();
            habitatActual = habitatDao.obtenerHabitat(id);

        } catch (Exception e) {
            mostrarAlerta("Error","Error al cargar los datos",Alert.AlertType.ERROR);
            return;
        }

        if (habitatActual != null) {

            txtNombre.setText(habitatActual.getNombre());
            chkTipo.setValue(habitatActual.getTipo());
            txtClima.setText(habitatActual.getClima());
            chkNivelLimpieza.setValue(habitatActual.getNivelLimpieza());

            spnCapacidad.getValueFactory().setValue(habitatActual.getCapacidadAnimales());

            subPnlCamposMod.setVisible(true);

        } else {

            mostrarAlerta("Error","No existe un hábitat con ese ID",Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void modificarDatos(ActionEvent event) {

        if (txtNombre.getText().isBlank() || chkTipo.getValue() == null) {

            mostrarAlerta("Campos incompletos","Debe completar todos los campos obligatorios",Alert.AlertType.WARNING);
            return;
        }

        habitatActual.setNombre(txtNombre.getText().trim());
        habitatActual.setTipo(chkTipo.getValue());
        habitatActual.setClima(txtClima.getText().trim());
        habitatActual.setNivelLimpieza(chkNivelLimpieza.getValue());
        habitatActual.setCapacidadAnimales(spnCapacidad.getValue());

        try {

            if (habitatDao.modificarDatos(habitatActual)) {

                mostrarAlerta("Éxito","Hábitat modificado correctamente",Alert.AlertType.INFORMATION);

                limpiarCamposModificar();
                return;
            }

            mostrarAlerta("Fracaso","No se pudo modificar el hábitat",Alert.AlertType.INFORMATION);

        } catch (Exception e) {

            mostrarAlerta("Error",e.getMessage(),Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposModificar() {

        txtNombre.clear();
        txtClima.clear();

        chkTipo.setValue(null);
        chkNivelLimpieza.setValue(null);

        spnCapacidad.getValueFactory().setValue(1);

        habitatActual = null;

        subPnlCamposMod.setDisable(true);
        subPnlCamposMod.setVisible(false);
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