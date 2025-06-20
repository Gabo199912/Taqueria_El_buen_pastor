module org.escuela.taqueria {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;

    opens org.escuela.taqueria.Controlador to javafx.fxml;
    opens org.escuela.taqueria.Modelo to javafx.base;
    opens org.escuela.taqueria.Estilos to javafx.fxml;

    opens org.escuela.taqueria to javafx.fxml;

    exports org.escuela.taqueria;
}