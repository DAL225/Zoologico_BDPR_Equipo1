module com.mycompany.zoologico_bdpr_equipo1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.zoologico_bdpr_equipo1 to javafx.fxml;
    exports com.mycompany.zoologico_bdpr_equipo1;
}
