package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Administrador;
import Modelo.Cuidador;
import Modelo.Dao.EmpleadoDAO;
import Modelo.Empleado;
import Modelo.Impl.EmpleadoDAOImpl;
import Modelo.Intendente;
import Modelo.Veterinario;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller encargado del login del sistema.
 * Redirige a la vista correspondiente según el rol del usuario.
 */
public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Button btnLogin;
    
    private EmpleadoDAO empleadoDao;

    /**
     * Evento del botón Login.
     * Valida credenciales y abre la ventana
     * correspondiente.
     */
    @FXML
    private void iniciarSesion(ActionEvent event) {

        try {
            empleadoDao = new EmpleadoDAOImpl();
            
            String usuario = txtUsuario.getText();
            String pass = txtPassword.getText();

            Empleado empleado = empleadoDao.validCredentials(usuario, pass);

            if (empleado == null) {
                mostrarAlerta("Error", "Credenciales incorrectas", Alert.AlertType.ERROR);
                return;
            }

            Stage stageActual = (Stage) txtUsuario.getScene().getWindow();

            if (empleado instanceof Administrador) {
                Administrador admin = (Administrador) empleado;

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/scenes/admin.fxml"));

                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Administrador");
                stage.setScene(new Scene(root));
                stage.show();

                stageActual.close();

            } else if (empleado instanceof Cuidador) {
                Cuidador cuidador = (Cuidador) empleado;

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/scenes/cuidador.fxml"));

                Parent root = loader.load();

                CuidadorController controller = loader.getController();
                controller.setListaIdAnimales(cuidador.getIdsAnimales());

                Stage stage = new Stage();
                stage.setTitle("Cuidador");
                stage.setScene(new Scene(root));
                stage.show();

                stageActual.close();

            } else if (empleado instanceof Veterinario) {
                Veterinario vet = (Veterinario) empleado;

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/scenes/veterinario.fxml"));

                Parent root = loader.load();

                VeterinarioController controller = loader.getController();
                controller.setListaIdAnimales(vet.getIdsAnimales());

                Stage stage = new Stage();
                stage.setTitle("Veterinario");
                stage.setScene(new Scene(root));
                stage.show();

                stageActual.close();

            } else if (empleado instanceof Intendente) {
                Intendente intendente = (Intendente) empleado;

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/scenes/intendente.fxml"));

                Parent root = loader.load();

                IntendenteController controller = loader.getController();
                controller.setIdEmpleado(intendente.getId());

                Stage stage = new Stage();
                stage.setTitle("Intendente");
                stage.setScene(new Scene(root));
                stage.show();

                stageActual.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra alertas.
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
