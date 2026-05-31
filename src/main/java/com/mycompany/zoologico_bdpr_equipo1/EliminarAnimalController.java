/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import Modelo.Impl.AnimalDAOImpl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class EliminarAnimalController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private AnchorPane subPnlCamposElim;

    @FXML
    private Spinner<Integer> spnIdAnimal;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextArea txtDatos;

    private Animal animalActual;

    private AnimalDAO animalDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        spnIdAnimal.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );

        subPnlCamposElim.setVisible(false);
        txtDatos.setEditable(false);
    }

    @FXML
    private void buscarAnimal(ActionEvent event) {

        int id = spnIdAnimal.getValue();

        try {

            animalDao = new AnimalDAOImpl();
            animalActual = animalDao.obtenerAnimal(id);

        } catch (Exception e) {

            mostrarAlerta("Error","Error al cargar los datos",Alert.AlertType.ERROR);
            return;
        }

        if (animalActual != null) {

            txtDatos.setText(animalActual.toString2());
            txtDatos.setEditable(false);

            subPnlCamposElim.setVisible(true);

        } else {

            mostrarAlerta(
                    "Error",
                    "No existe un animal con ese ID",
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {

        if (animalActual == null) {

            mostrarAlerta(
                    "Error",
                    "Debe buscar un animal primero",
                    Alert.AlertType.WARNING
            );
            return;
        }

        try {

            if (animalDao.eliminarAnimal(animalActual.getId())) {

                mostrarAlerta("Éxito","Animal eliminado correctamente",Alert.AlertType.INFORMATION);

                limpiarCamposEliminar();
                return;
            }

            mostrarAlerta("Error","No se pudo eliminar el animal",Alert.AlertType.ERROR);

        } catch (Exception e) {

            mostrarAlerta("Error",e.getMessage(),Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposEliminar() {
        animalActual = null;
        txtDatos.clear();
        spnIdAnimal.getValueFactory().setValue(1);
        subPnlCamposElim.setVisible(false);
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