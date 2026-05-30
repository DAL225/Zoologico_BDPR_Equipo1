/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Dao.AnimalDAO;
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

public class RegistrarAnimalController implements Initializable {

    @FXML
    private AnchorPane pnlPrincipal;

    @FXML
    private TextField txtNombreCientifico;

    @FXML
    private Button btnRegistrarAnimal;

    @FXML
    private ChoiceBox<String> chkEspecie;

    @FXML
    private TextField txtNombreComun;

    @FXML
    private Spinner<Integer> spnIdHabitat;

    @FXML
    private ChoiceBox<String> chkSexo;

    @FXML
    private TextField txtEstadoSalud;

    @FXML
    private Button btnRecomendaciones;

    @FXML
    private Button btnTratamientos;
    
    private ObservableList<String> listaRecomendaciones;
    private ObservableList<String> listaTratamientos;
    
    private AnimalDAO animalDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaRecomendaciones = FXCollections.observableArrayList();
        listaTratamientos = FXCollections.observableArrayList();
        
        // Catálogo de sexos
        chkSexo.setItems(FXCollections.observableArrayList("Macho","Hembra"));

        // Catálogo de especies (ejemplo)
        chkEspecie.setItems(FXCollections.observableArrayList(
                        "Mamífero",
                        "Ave",
                        "Reptil",
                        "Anfibio",
                        "Pez")
        );

        // Spinner para el Id del hábitat
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        spnIdHabitat.setValueFactory(valueFactory);
    }

    /**
     * Registra un nuevo animal.
     */
    @FXML
    private void registrarAnimal(ActionEvent event) {

        String nombreCientifico = txtNombreCientifico.getText().trim();
        String nombreComun = txtNombreComun.getText().trim();
        String especie = chkEspecie.getValue();
        String sexo = chkSexo.getValue();
        String estadoSalud = txtEstadoSalud.getText().trim();
        Integer idHabitat = spnIdHabitat.getValue();

        //Verifica los campos obligatorios
        if (nombreCientifico.isBlank() || especie == null) {
            mostrarAlerta("Campos incompletos", "Debe completar todos los campos obligatorios", Alert.AlertType.WARNING);
            return;
        }

        // TODO:
        // Crear objeto Animal
        // Insertar en BD mediante AnimalDAO
        // Asociar recomendaciones
        // Asociar tratamientos
        
//        try {
//            almacenDao = new ProductoAlmacenDAOImpl();
//
//            String marca = txtMarca.getText().trim();
//            String tipo = selecTipo.getValue(); 
//            int stock = spnStock.getValueFactory().getValue();
//            String proveedor = txtProveedor.getText().trim();
//
//            if (!longitudCaracteresAgregarValida()){
//                return;
//            }
//
//            ProductoAlmacen producto = new ProductoAlmacen(marca, tipo, stock, proveedor);
//
//            if (almacenDao.agregarProductoAlmacen(producto)) {
//                mostrarAlerta("Exito","Elemento registrado correctamente.",Alert.AlertType.INFORMATION);
//                limpiarCamposAgregar();
//                return;
//            }
//
//            mostrarAlerta("Fracaso", "El elemento no se pudo registrar", Alert.AlertType.INFORMATION);
//
//        } catch (Exception e) {
//            mostrarAlerta("Error ", e.getMessage(), Alert.AlertType.ERROR);
//            if (e.getMessage() != null && e.getMessage().equals("Error de acceso a la BD, intente mas tarde")){
//                limpiarCamposAgregar();
//            }
//        }

        
    }

    /**
     * Gestiona las recomendaciones de cuidado.
     */
    @FXML
    private void agregarRecomendacion(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionRecomendaciones.fxml"));
        Parent vista = loader.load();

        // Obtener controller
        EdicionRecomendacionesController controller = loader.getController();

        // Pasar datos
        controller.setListaRecomendaciones(listaRecomendaciones);

        Stage stage = new Stage();
        stage.setScene(new Scene(vista));
        stage.show();
        
        System.out.println("Abrir edición de recomendaciones");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gestiona los tratamientos veterinarios.
     */
    @FXML
    private void agregarTratamiento(ActionEvent event) {

        try{
           FXMLLoader loader = new FXMLLoader(getClass().getResource("edicionTratamientos.fxml"));
        Parent vista = loader.load();

        // Obtener controller
        EdicionTratamientosController controller = loader.getController();

        // Pasar datos
        controller.setListaTratamientos(listaTratamientos);

        Stage stage = new Stage();
        stage.setScene(new Scene(vista));
        stage.show();
        
        System.out.println("Abrir edición de tratamientos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Limpia los campos de informacion ingresada por el usuario.
     */
    private void limpiarCamposAgregar() {
        // Limpiar campos de texto
        txtNombreCientifico.clear();
        txtNombreComun.clear();
        txtEstadoSalud.clear();

        // Resetear los selectores (ComboBox/ChoiceBox) a ningún valor seleccionado
        chkEspecie.setValue(null);
        chkSexo.setValue(null);

        // Resetear el Spinner al valor mínimo
        spnIdHabitat.getValueFactory().setValue(1);

        // Limpieza de las listas
        if (listaRecomendaciones != null) {
            listaRecomendaciones.clear();
        }
        if (listaTratamientos != null) {
            listaTratamientos.clear();
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