/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Dao.AnimalDAO;
import Modelo.Dao.HabitatDAO;
import Modelo.Habitat;
import Modelo.Impl.AnimalDAOImpl;
import Modelo.Impl.HabitatDAOImpl;
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

public class EliminarHabitatController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private AnchorPane subPnlCamposElim;

    @FXML
    private Spinner<Integer> spnIdHabitat;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TextArea txtDatos;

    private Habitat habitatActual;

    private HabitatDAO habitatDao;
    
    private AnimalDAO animalDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        spnIdHabitat.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1)
        );

        //subPnlCamposElim.setVisible(false);
        txtDatos.setEditable(false);
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

            txtDatos.setText(habitatActual.toString2());
            txtDatos.setEditable(false);

            subPnlCamposElim.setVisible(true);

        } else {

            mostrarAlerta("Error","No existe un habitat con ese ID",Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {

        if (habitatActual == null) {

            mostrarAlerta("Error","Debe buscar un habitat primero",Alert.AlertType.WARNING);
            return;
        }

        try {

            if (habitatDao.eliminarHabitat(habitatActual.getId())) {
                animalDao = new AnimalDAOImpl();
                animalDao.desasignarHabitat(habitatActual.getId());

                mostrarAlerta("Éxito","Habitat eliminado correctamente",Alert.AlertType.INFORMATION);

                limpiarCamposEliminar();
                return;
            }
            mostrarAlerta("Error","No se pudo eliminar el habitat",Alert.AlertType.ERROR);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error",e.getMessage(),Alert.AlertType.ERROR);
        }
    }

    private void limpiarCamposEliminar() {
        habitatActual = null;
        txtDatos.clear();
        spnIdHabitat.getValueFactory().setValue(1);
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