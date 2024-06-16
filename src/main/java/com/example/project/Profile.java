

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
import java.util.ResourceBundle;

import static com.example.project.SignupController.hashPassword;

public class Profile {
    @FXML
    private Label name, id, mail, number;
    @FXML
    private AnchorPane rootPane;

    public void initialize(){


        String i= String.valueOf(GlobalData.getInstance().getId());
        String n="",m="",p="";
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String info = "select * from user_info where std_id = '" + i + "'";
        try{

            Statement statement= connectDB.createStatement();
            ResultSet queryRes= statement.executeQuery(info);
            while(queryRes.next()){
                n = queryRes.getString("name");
                m = queryRes.getString("mail");
                p = queryRes.getString("phone");
            }
            GlobalData.getInstance().setName(n);
            GlobalData.getInstance().setMail(m);
            GlobalData.getInstance().setPhone(p);
        } catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        name.setText(n);
        id.setText(i);
        mail.setText(m);
        number.setText(p);
    }

    @FXML
    public void onEditProfileClicked(){
        try {
            Parent signup = FXMLLoader.load(getClass().getResource("editProfile.fxml"));
            rootPane.getChildren().setAll(signup);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }
    @FXML
    public void backClick(){
        try {
            Parent signup = FXMLLoader.load(getClass().getResource("First.fxml"));
            rootPane.getChildren().setAll(signup);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }

}