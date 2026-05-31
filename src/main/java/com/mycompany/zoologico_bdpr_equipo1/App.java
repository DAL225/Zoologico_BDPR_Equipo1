package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Impl.BaseDAOOracle;
import Modelo.Impl.EmpleadoDAOImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            EmpleadoDAOImpl dao = new EmpleadoDAOImpl();

            System.out.println("Conexión exitosa");

        } catch (Exception e) {
            e.printStackTrace();
        }
        scene = new Scene(loadFXML("admin"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/scenes/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}