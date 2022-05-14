module org.arvik.applisttask {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires MaterialFX;
    //requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires VirtualizedFX;

    opens org.arvik.applisttask to javafx.fxml;
    opens org.arvik.applisttask.app.controller to javafx.fxml;
    exports org.arvik.applisttask;
    exports org.arvik.applisttask.app.controller;
    exports org.arvik.applisttask.modele;
}