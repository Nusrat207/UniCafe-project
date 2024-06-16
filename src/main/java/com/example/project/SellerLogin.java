package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

import static com.example.project.SignupController.hashPassword;

public class SellerLogin {
    @FXML
    private Button login;
    @FXML
    private Label loginMsg;
    @FXML
    private PasswordField pw;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField sellerID;

    @FXML
    public void loginClick(ActionEvent e) {
        loginMsg.setText("");
        if (!sellerID.getText().isBlank() && !pw.getText().isBlank()) {
            //validateLogin();

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String verifyLogin = "select * from seller where id ='" + sellerID.getText() + "';";

            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryRes = statement.executeQuery(verifyLogin);

                if (queryRes.next()) {
                    String password = queryRes.getString("password");
                    String enteredPassword = pw.getText();
                    if(Objects.equals(password, enteredPassword)){
                        HelloApplication.sellerShop=queryRes.getString("shop");

                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sellerFront.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
                        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }
                    else{
                        loginMsg.setText("incorrect password");
                    }
                } else{
                    loginMsg.setText("incorrect ID");
                }
            }catch (Exception ev) {
                ev.getCause();
            }

        } else {
            loginMsg.setText("pls enter credentials");
        }
    }

    @FXML
    void backClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("front.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
