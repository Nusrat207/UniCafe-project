module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires pdfbox.app;


    opens com.example.project to javafx.fxml;
    exports com.example.project;

    opens com.example.project.browseMenu to javafx.fxml;
   // exports com.example.project;
    exports com.example.project.browseMenu;
    exports com.example.project.itemsController;

    opens com.example.project.itemsController to javafx.fxml;
}