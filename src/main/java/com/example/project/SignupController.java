package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class SignupController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField name, std_id, mail, phone;
    @FXML
    private PasswordField password;
    @FXML
    private Label signupMsg;
    @FXML
    public void loginClicked(ActionEvent e) {
        try {
            Parent signup = FXMLLoader.load(getClass().getResource("login_page.fxml"));
            rootPane.getChildren().setAll(signup);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }
    @FXML
    public void signupClick(ActionEvent e) {
        if (!name.getText().isBlank() && !std_id.getText().isBlank() && !mail.getText().isBlank() && !phone.getText().isBlank() && !password.getText().isBlank()) {
            validateSignup();
        } else {
            signupMsg.setText("pls enter credentials");
        }
    }
    public void validateSignup(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        if (std_id.getText().length() != 9 || password.getText().length() != 6) {
            signupMsg.setText("provide correct credentials");
        }
        else{
            String exist = "select count(1) from user_info where std_id = '" + std_id.getText() + "';";
            try{
                Statement statement= connectDB.createStatement();
                ResultSet queryRes= statement.executeQuery(exist);
                while(queryRes.next()){
                    if(queryRes.getInt(1)==1){
                        signupMsg.setText("Account with this ID already exists");
                        return;
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
                e.getCause();
            }
            String query = "INSERT INTO user_info (std_id, name, mail, phone, password) VALUES (?, ?, ?, ?, ?)";
            try{
                PreparedStatement preparedStatement = connectDB.prepareStatement(query);
                String pw= hashPassword(password.getText());
                preparedStatement.setInt(1, Integer.parseInt(std_id.getText()));
                preparedStatement.setString(2, name.getText());
                preparedStatement.setString(3, mail.getText());
                preparedStatement.setString(4, phone.getText());
                preparedStatement.setString(5, pw);

                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Data inserted successfully!");
                    try {
                        Parent login = FXMLLoader.load(getClass().getResource("login_page.fxml"));
                        rootPane.getChildren().setAll(login);
                    } catch (IOException ev) {
                        ev.printStackTrace();
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
    }
    @FXML
    void backClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("front.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}