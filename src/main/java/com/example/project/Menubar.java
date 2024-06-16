package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class Menubar  {
    @FXML
    private Label menu;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label menuback;

    @FXML
    private VBox slider;
    @FXML
    private Button breakfast, snacks, lunch, dinner;


    @FXML
    public void breakfastClick(ActionEvent e) throws IOException {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(LocalTime.of(7, 0)) && currentTime.isBefore(LocalTime.of(11, 0))) {
            HelloApplication.Meal="breakfast";
            FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("browse.fxml"));
            Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 775, 706);
            stage.setScene(scene);
            stage.show();
        }
        else{
            showToast("It's not breakfast time");
        }
    }

    @FXML
    public void snacksClick(ActionEvent e) throws IOException {
        HelloApplication.Meal="snacks";
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("browse.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    public void lunchClick(ActionEvent e) throws IOException {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(LocalTime.of(11, 0)) && currentTime.isBefore(LocalTime.of(16, 0)))  {
            HelloApplication.Meal="lunch";
            FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("browse.fxml"));
            Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
            //Scene scene=new Scene(fxmlLoader.load());
            Scene scene = new Scene(fxmlLoader.load(), 775, 706);
            stage.setScene(scene);
            stage.setTitle("WestShop !!");
            stage.show();

        }
        else
            //System.out.println("lunch time over");
            //showAlert("Lunch Time is Over");
        //if(currentTime.isBefore(LocalTime.of(11, 0)))
            showToast("It's not lunch time");
    }

    @FXML
    public void dinnerClick(ActionEvent e) throws IOException {
        LocalTime currentTime = LocalTime.now();
        System.out.println(currentTime);
        if (currentTime.isAfter(LocalTime.of(19, 0)) && currentTime.isBefore(LocalTime.of(23, 59))) {
            HelloApplication.Meal="dinner";
            FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("browse.fxml"));
            Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 775, 706);
            stage.setScene(scene);
            stage.show();
        }
        else{
            //System.out.println("dinner time over");
            //showAlert("Dinner Time is Over");
            showToast("Dinner Time is Over");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Meal Time Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showToast(String message) {
        Toast.show(message);
    }

    @FXML
    public void backClick(){
        try {
            Parent login = FXMLLoader.load(getClass().getResource("First.fxml"));
            rootPane.getChildren().setAll(login);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }
}