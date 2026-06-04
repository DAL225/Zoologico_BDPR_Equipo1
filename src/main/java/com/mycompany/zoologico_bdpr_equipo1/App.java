package com.mycompany.zoologico_bdpr_equipo1;

import Modelo.Animal;
import Modelo.Impl.AnimalDAOImpl;
import Modelo.Impl.BaseDAOMongo;
import Modelo.Impl.BaseDAOOracle;
import Modelo.Impl.EmpleadoDAOImpl;
import com.mongodb.client.FindIterable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import org.bson.Document;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, Exception {
//        try {
//            EmpleadoDAOImpl dao = new EmpleadoDAOImpl();
//
//            System.out.println("Conexión exitosa");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            BaseDAOMongo DAO = new BaseDAOMongo();
            AnimalDAOImpl dao = new AnimalDAOImpl();
            System.out.println("Ahora la lista");
            List<Animal> ñ = dao.obtenerTodosAnimales();
            for (Animal a : ñ){
                System.out.println(a.toString2());
            }
        } catch (Exception e){
            System.out.println("El coso ya existe wey");
            System.out.println(e.getMessage());
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