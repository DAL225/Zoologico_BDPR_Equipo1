module com.mycompany.zoologico_bdpr_equipo1 {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.oracle.database.jdbc;

    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;

    opens com.mycompany.zoologico_bdpr_equipo1 to javafx.fxml;
    opens Modelo to javafx.base;

    exports com.mycompany.zoologico_bdpr_equipo1;
}