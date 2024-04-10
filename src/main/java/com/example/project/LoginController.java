package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.example.project.SignupController.hashPassword;

public class LoginController {
    @FXML
    private Button LoginButton, CancelButton, front_signin, front_signup;
    private ImageView brandimgV, lockimgV;
    @FXML
    private TextField idText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Label loginMsg;
    @FXML
    private AnchorPane rootPane;

    public void frontsigninClick(){
        try {
            Parent login = FXMLLoader.load(getClass().getResource("login_page.fxml"));
            rootPane.getChildren().setAll(login);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }
    public void frontSignupClick(){
        try {
            Parent signup = FXMLLoader.load(getClass().getResource("signup.fxml"));
            rootPane.getChildren().setAll(signup);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }

    @FXML
    public void loginClick(ActionEvent e){
        if(!idText.getText().isBlank() && !passwordText.getText().isBlank()){
            validateLogin();
        } else{
            loginMsg.setText("pls enter credentials");
        }
    }
    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "select * from user_info where std_id = '" + idText.getText() + "'";
        try{
            Statement statement= connectDB.createStatement();
            ResultSet queryRes= statement.executeQuery(verifyLogin);

            while(queryRes.next()){
                String hashedPassword = queryRes.getString("password");
                String enteredPassword = hashPassword(passwordText.getText());

                if (hashedPassword.equals(enteredPassword)) {
                    //loginMsg.setText("Congrats, login successful!");
                    try {
                        Parent login = FXMLLoader.load(getClass().getResource("firstpage.fxml"));
                        rootPane.getChildren().setAll(login);
                    } catch (IOException ev) {
                        ev.printStackTrace();
                    }
                    String name = queryRes.getString("name");
                    String mail = queryRes.getString("mail");
                    String phone = queryRes.getString("phone");

                    GlobalData.getInstance().setName(name);
                    GlobalData.getInstance().setId(Integer.parseInt(idText.getText()));
                    GlobalData.getInstance().setMail(mail);
                    GlobalData.getInstance().setPhone(phone);

                } else {
                     loginMsg.setText("Wrong credentials");
                }
            }

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void signupClick(ActionEvent e){
       /* try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            Scene scene = new Scene(root, 422, 706);
            newStage.setTitle("Hello!");
            newStage.setScene(scene);
            Stage initialStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            initialStage.close();
            newStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        } */
        try {
            Parent signup = FXMLLoader.load(getClass().getResource("signup.fxml"));
            rootPane.getChildren().setAll(signup);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }


    /*
    @FXML
    public void cancelClick(ActionEvent e){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("@c21cdd2b452985be6147fa9d8142252b.jpg" );
        Image brandImg=new Image(brandingFile.toURI().toString());
        brandimgV.setImage(brandImg);

        File lockFile = new File("@istockphoto-936681148-612x612.jpg" );
        Image lockImg=new Image(lockFile.toURI().toString());
        lockimgV.setImage(lockImg);
    } */
}
//dekhte paina