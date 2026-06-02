package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Dao.AnimalDAO;
import Modelo.Impl.AnimalDAOImpl;
import com.mycompany.zoologico_bdpr_equipo1.EdicionRecomendacionesController;
import com.mycompany.zoologico_bdpr_equipo1.EdicionTratamientosController;
import java.net.URL;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
public class ModificarAnimalController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private AnchorPane subPnlCamposMod;

    @FXML
    private TextField txtNombreCientifico;

    @FXML
    private ChoiceBox<String> chkEspecie;

    @FXML
    private Spinner<Integer> spnIdHabitat;

    @FXML
    private TextField txtNombreComun;

    @FXML
    private ChoiceBox<String> chkSexo;

    @FXML
    private TextField txtEstadoSalud;

    @FXML
    private Button btnModificarDatos;

    @FXML
    private Button btnRecomendaciones;

    @FXML
    private Button btnTratamientos;

    @FXML
    private Spinner<Integer> spnIdAnimal;

    @FXML
    private Button btnBuscar;

    private Animal animalActual;
    
    private AnimalDAO animalDao;
    
    private ObservableList<String> recomendacionesCuidado;
    private ObservableList<String> tratamientos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recomendacionesCuidado  = FXCollections.observableArrayList();
        tratamientos  = FXCollections.observableArrayList();

        chkEspecie.getItems().addAll(
                "Mamifero",
                "Ave",
                "Reptil",
                "Anfibio",
                "Pez"
        );

        chkSexo.getItems().addAll("Macho","Hembra");
        spnIdAnimal.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));
        spnIdHabitat.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1));

        subPnlCamposMod.setVisible(false);
    }

    @FXML
    private void buscarAnimal(ActionEvent event) {

        int id = spnIdAnimal.getValue();
        
        try{
            animalDao = new AnimalDAOImpl();
            animalActual = animalDao.obtenerAnimal(id);
        } catch(Exception e){
            mostrarAlerta("Error", "Error al cargar los datos", Alert.AlertType.ERROR);
        }

        
        if (animalActual != null) {

            txtNombreCientifico.setText(animalActual.getNombreCientifico());
            chkEspecie.setValue(animalActual.getEspecie());
            spnIdHabitat.getValueFactory().setValue(animalActual.getIdHabitat());
            txtNombreComun.setText(animalActual.getNombreComun());
            chkSexo.setValue(animalActual.getSexo());
            txtEstadoSalud.setText(animalActual.getEstadoSalud());

            subPnlCamposMod.setVisible(true);

        } else {
            mostrarAlerta("Error","No existe un animal con ese ID",Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void modificarDatos(ActionEvent event) {

        //Verifica los campos obligatorios
        if (txtNombreCientifico.getText().isBlank() || chkEspecie.getValue() == null) {
            mostrarAlerta("Campos incompletos", "Debe completar todos los campos obligatorios", Alert.AlertType.WARNING);
            return;
        }

        animalActual.setNombreCientifico(txtNombreCientifico.getText().trim());
        animalActual.setEspecie(chkEspecie.getValue());
        animalActual.setIdHabitat(spnIdHabitat.getValue());
        animalActual.setNombreComun(txtNombreComun.getText().trim());
        animalActual.setSexo(chkSexo.getValue());
        animalActual.setEstadoSalud(txtEstadoSalud.getText().trim());
        animalActual.setRecomendacionesCuidado(this.recomendacionesCuidado);
        animalActual.setTratamientos(this.tratamientos);

        try {
            if (animalDao.modificarDatos(animalActual)) { 
                mostrarAlerta("Éxito", "Elemento modificado correctamente", Alert.AlertType.INFORMATION);
                limpiarCamposModificar();
                return;
            }

            mostrarAlerta("Fracaso", "El elemento no se pudo modificar", Alert.AlertType.INFORMATION);

        }catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
        }

        
        mostrarAlerta("Exito","Datos modificados correctamente",Alert.AlertType.INFORMATION);
    }

    @FXML
    private void modificarRecomendaciones(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/edicionRecomendaciones.fxml"));
        Parent vista = loader.load();

        // Obtener controller
        EdicionRecomendacionesController controller = loader.getController();
        
        this.recomendacionesCuidado.setAll(animalActual.getRecomendacionesCuidado());

        // Pasar datos
        controller.setListaRecomendaciones(recomendacionesCuidado);

        Stage stage = new Stage();
        stage.setScene(new Scene(vista));
        stage.show();
        
        System.out.println("Abrir edición de recomendaciones");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarTratamientos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/edicionTratamientos.fxml"));
            Parent vista = loader.load();

            // Obtener controller
            EdicionTratamientosController controller = loader.getController();

            this.tratamientos.setAll(animalActual.getTratamientos());

            // Pasar datos
            controller.setListaTratamientos(tratamientos);

            Stage stage = new Stage();
            stage.setScene(new Scene(vista));
            stage.show();

            System.out.println("Abrir edición de tratamientos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiarCamposModificar() {
        this.txtNombreCientifico.clear();
        this.txtNombreComun.clear();
        this.txtEstadoSalud.clear();

        this.chkEspecie.setValue(null);
        this.chkSexo.setValue(null);

        this.spnIdHabitat.getValueFactory().setValue(1);
        animalActual = null;
        
        if (tratamientos != null) tratamientos.clear();
        if (recomendacionesCuidado != null) recomendacionesCuidado.clear();

        this.subPnlCamposMod.setVisible(false);
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
