module org.app.bp.gestioncaisse {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;
    requires java.sql;
    requires itextpdf;
    requires javafx.graphics;
    requires java.desktop;

    opens org.app.bp to javafx.fxml, javafx.graphics, java.base;
    opens org.app.bp.utils to javafx.fxml, javafx.graphics, java.base;
    opens org.app.bp.controller to javafx.fxml, javafx.base;
    opens org.app.bp.models to javafx.base;

    exports org.app.bp;
}
