package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController {
//nuha1234567
    @FXML
    private Button LoginButton, CancelButton;
    @FXML
    private TextField usernameText, passwordText;
    @FXML
    private Label loginMsg;
    @FXML
    private ImageView brandimgV, lockimgV;

   @FXML
   public void cancelClick(ActionEvent e){
       Stage stage = (Stage) CancelButton.getScene().getWindow();
       stage.close();
   }
   @FXML
   public void loginClick(ActionEvent e){

       if(!usernameText.getText().isBlank() && !passwordText.getText().isBlank()){
           validateLogin();
       } else{
           loginMsg.setText("pls enter credentials");
       }
   }

   //@Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File brandingFile = new File("@c21cdd2b452985be6147fa9d8142252b.jpg" );
        Image brandImg=new Image(brandingFile.toURI().toString());
        brandimgV.setImage(brandImg);

        File lockFile = new File("@istockphoto-936681148-612x612.jpg" );
        Image lockImg=new Image(lockFile.toURI().toString());
        lockimgV.setImage(lockImg);
    }

    public void validateLogin(){
       DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        //System.out.println(usernameText.getText() );
        //System.out.println(passwordText.getText());
        String verifyLogin = "select count(1) from user_account where username = '" + usernameText.getText() + "' AND password= '" + passwordText.getText() + "'";
        try{
            Statement statement= connectDB.createStatement();
            ResultSet queryRes= statement.executeQuery(verifyLogin);

            while(queryRes.next()){
                if( queryRes.getInt(1)==1  ){
                    loginMsg.setText("congrats");
                } else{
                    loginMsg.setText("invalid");

                }
            }

        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    //smkaskhgfg

}
//dekhte paina