/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Administrador;
import Modelo.Animal;
import Modelo.Cuidador;
import Modelo.Dao.AdministradorDAO;
import Modelo.Dao.CuidadorDAO;
import Modelo.Dao.IntendenteDAO;
import Modelo.Dao.VeterinarioDAO;
import Modelo.Habitat;
import Modelo.Impl.AdministradorDAOImpl;
import Modelo.Impl.CuidadorDAOImpl;
import Modelo.Impl.IntendenteDAOImpl;
import Modelo.Impl.VeterinarioDAOImpl;
import Modelo.Intendente;
import Modelo.Turno;
import Modelo.Veterinario;
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
    
    private AdministradorDAO adminDao;
    private CuidadorDAO cuidadorDao;
    private VeterinarioDAO veterinarioDao;
    private IntendenteDAO intendenteDao;
    

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
                stckPane.setVisible(true);
                subPnlCuidador.setVisible(false);
                subPnlIntendente.setVisible(false);
                subPnlVeterinario.setVisible(true);
                break;

            case "Cuidador":
                stckPane.setVisible(true);
                subPnlIntendente.setVisible(false);
                subPnlVeterinario.setVisible(false);
                subPnlCuidador.setVisible(true);
                break;

            case "Intendente":
                stckPane.setVisible(true);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/edicionTurnos.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/edicionAnimales.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/edicionRecomendaciones.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/edicionAnimales.fxml"));
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
        
        try{
            switch(tipoEmpleado){
                case("Administrador"):
                    adminDao = new AdministradorDAOImpl();
                    
                    Administrador adminAux = new Administrador();
                    adminAux.setNombre(nombre);
                    adminAux.setUsuario(usuario);
                    adminAux.setPassword(password);
                    adminAux.setTurnos(listaTurnos);
                    
                    if(adminDao.agregarAdministrador(adminAux)){
                        mostrarAlerta("Registro", "Empleado registrado correctamente.", Alert.AlertType.INFORMATION);
                        this.limpiarCampos();
                        return;
                    }
                    mostrarAlerta("Aviso", "Fracaso al registrar", Alert.AlertType.INFORMATION);
                break;
                
                case("Veterinario"):
                    veterinarioDao = new VeterinarioDAOImpl();
                    
                    Veterinario veterinarioAux = new Veterinario();
                    veterinarioAux.setNombre(nombre);
                    veterinarioAux.setUsuario(usuario);
                    veterinarioAux.setPassword(password);
                    veterinarioAux.setTurnos(listaTurnos);
                    veterinarioAux.setEspecialidades(listaEspecialidades);
                    veterinarioAux.setIdsAnimales(listaIdAnimales);
                    
                    if(veterinarioDao.agregarVeterinario(veterinarioAux)){
                        mostrarAlerta("Registro", "Empleado registrado correctamente.", Alert.AlertType.INFORMATION);
                        this.limpiarCampos();
                        return;
                    }
                    mostrarAlerta("Aviso", "Fracaso al registrar", Alert.AlertType.INFORMATION);
                
                break;
                
                
                case("Cuidador"):
                    cuidadorDao = new CuidadorDAOImpl();
                    
                    Cuidador cuidadorAux = new Cuidador();
                    cuidadorAux.setNombre(nombre);
                    cuidadorAux.setUsuario(usuario);
                    cuidadorAux.setPassword(password);
                    cuidadorAux.setTurnos(listaTurnos);
                    cuidadorAux.setIdsAnimales(listaIdAnimales);
                    
                    if(cuidadorDao.agregarCuidador(cuidadorAux)){
                        mostrarAlerta("Registro", "Empleado registrado correctamente.", Alert.AlertType.INFORMATION);
                        this.limpiarCampos();
                        return;
                    }
                    mostrarAlerta("Aviso", "Fracaso al registrar", Alert.AlertType.INFORMATION);
                
                break;
                
                case("Intendente"):
                    intendenteDao = new IntendenteDAOImpl();
                    
                    Intendente intendenteAux = new Intendente();
                    intendenteAux.setNombre(nombre);
                    intendenteAux.setUsuario(usuario);
                    intendenteAux.setPassword(password);
                    intendenteAux.setTurnos(listaTurnos);
                    intendenteAux.setIdsHabitats(listaIdHabitats);
                    
                    if(intendenteDao.agregarIntendente(intendenteAux)){
                        mostrarAlerta("Registro", "Empleado registrado correctamente.", Alert.AlertType.INFORMATION);
                        this.limpiarCampos();
                        return;
                    }
                    mostrarAlerta("Aviso", "Fracaso al registrar", Alert.AlertType.INFORMATION);
                break;
            }
            
            
        }catch(Exception e){
            mostrarAlerta("Error", "Ocurrio un error", Alert.AlertType.INFORMATION);
            System.out.println(e.getMessage());
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
     * Limpia todos los campos de texto, restablece el ChoiceBox y vacía por
     * completo las listas de datos temporales.
     */
    private void limpiarCampos() {
        // 1. Limpiar TextFields
        txtNombre.clear();
        txtUsuario.clear();
        txtPassword.clear();

        // 2. Restablecer ChoiceBox al primer elemento ("Administrador")
        if (chkTipoEmpleado.getItems() != null && !chkTipoEmpleado.getItems().isEmpty()) {
            chkTipoEmpleado.getSelectionModel().selectFirst();
        }

        // 3. Vaciar colecciones y listas (tanto ObservableList como ArrayList)
        if (listaTurnos != null) {
            listaTurnos.clear();
        }
        if (listaIdAnimales != null) {
            listaIdAnimales.clear();
        }
        if (listaIdHabitats != null) {
            listaIdHabitats.clear();
        }
        if (listaEspecialidades != null) {
            listaEspecialidades.clear();
        }
    }
}
