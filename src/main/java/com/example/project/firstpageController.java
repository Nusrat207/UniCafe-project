package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class firstpageController{

    @FXML
    private Label Menu, MenuBack, name_label, mealTime;
    @FXML
    private AnchorPane slider, rootPane;


    public void initialize(){

        String name = HelloApplication.Name;
        String[] words = name.trim().split("\\s+");
        name_label.setText("Hello, " + words[0]);

        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(LocalTime.of(7, 0)) && currentTime.isBefore(LocalTime.of(11, 0))){
            mealTime.setText("It's Breakfast Time now!");
        }
        else if(currentTime.isAfter(LocalTime.of(11, 0)) && currentTime.isBefore(LocalTime.of(16, 0))){
            mealTime.setText("It's Lunch Time now!~");
        }
        else if(currentTime.isAfter(LocalTime.of(19, 0)) && currentTime.isBefore(LocalTime.of(23, 59))){
            mealTime.setText("It's Dinner Time now!");
        }
    }

    @FXML
    public void onEastShopClicked(ActionEvent e) throws IOException
    {
        HelloApplication.Shop="east";
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("menubar.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void expenseClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("expense.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onWestShopClicked(ActionEvent e) throws IOException
    {
        HelloApplication.Shop="west";
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("menubar.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void myProfileClick(){
        try {
            Parent signup = FXMLLoader.load(getClass().getResource("myprofile_new.fxml"));
            rootPane.getChildren().setAll(signup);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }

    @FXML
    public void logoutClick(){
        HelloApplication.Name="";

        GlobalData.getInstance().setName(null);
        GlobalData.getInstance().setId(0);
        GlobalData.getInstance().setMail(null);
        GlobalData.getInstance().setPhone(null);

        showToast("Logged out");

        try {
            Parent signup = FXMLLoader.load(getClass().getResource("front.fxml"));
            rootPane.getChildren().setAll(signup);
        } catch (IOException ev) {
            ev.printStackTrace();
        }

    }

    @FXML
    public void orderHistory(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("history.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void showToast(String message) {
        Toast.show(message);
    }


}

