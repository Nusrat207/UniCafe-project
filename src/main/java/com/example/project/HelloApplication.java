package com.example.project;

import com.example.project.model.food;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    public static final String CURRENCY = "Tk. ";
    public static String Name="";
    public static int std_id=0;
    public static String Shop="";
    public static String Meal="";
    public static int removed=0;
    public static String sellerShop="";
    public static List<food> CartItems = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("front.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 775, 705);

        stage.setTitle("UNICAFE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}