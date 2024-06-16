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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.project.SignupController.hashPassword;

public class ProfileEdit {
    @FXML
    private PasswordField currentPw, newPw;
    @FXML
    private TextField new_mail, new_phone;
    @FXML
    private Label msg;
    @FXML
    private AnchorPane rootPane;

    @FXML
    public void backClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("myprofile_new.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void saveClick(){
        if(currentPw.getText().isBlank()){
            msg.setText("Enter current password");
            return;
        }
        if(!currentPw.getText().isBlank() && (!new_mail.getText().isBlank() || !new_phone.getText().isBlank() || !newPw.getText().isBlank())){
            String std_id = String.valueOf(GlobalData.getInstance().getId());

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String verifyLogin = "select * from user_info where std_id = '" + std_id + "'";
            try{
                Statement statement= connectDB.createStatement();
                ResultSet queryRes= statement.executeQuery(verifyLogin);

                while(queryRes.next()){
                    String hashedPassword = queryRes.getString("password");
                    String enteredPassword = hashPassword(currentPw.getText());

                    if(!(hashedPassword.equals(enteredPassword))) {
                        msg.setText("Wrong pw");
                    } else {
                        String newMail = new_mail.getText();
                        String newPhone = new_phone.getText();
                        String newPassword = newPw.getText();
                        String sql = "UPDATE user_info SET";

                        boolean fieldsUpdated = false;

                        if (!newMail.isEmpty()) {
                            sql += " mail = '" + newMail + "'";
                            fieldsUpdated = true;
                        }

                        if (!newPhone.isEmpty()) {
                            if (fieldsUpdated) {
                                sql += ",";
                            }
                            sql += " phone = '" + newPhone + "'";
                            fieldsUpdated = true;
                        }

                        if (!newPassword.isEmpty()) {
                            if (fieldsUpdated) {
                                sql += ",";
                            }
                            String pw= hashPassword(newPw.getText());
                            sql += " password = '" + pw + "'";
                            fieldsUpdated = true;
                        }
                        sql += " WHERE std_id = " + std_id;

                        PreparedStatement p=null;
                        p =connectDB.prepareStatement(sql);
                        p.execute();

                        System.out.println("updated");
                        try {
                            Parent signup = FXMLLoader.load(getClass().getResource("myprofile_new.fxml"));
                            rootPane.getChildren().setAll(signup);
                        } catch (IOException ev) {
                            ev.printStackTrace();
                        }
                    }
                }

            } catch(Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
    }
}
