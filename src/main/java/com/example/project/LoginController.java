package com.example.project;

import com.example.project.model.food;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    @FXML
    private Circle circleInside;

    public void frontsigninClick() {
        try {
            Parent login = FXMLLoader.load(getClass().getResource("login_page.fxml"));
            rootPane.getChildren().setAll(login);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }

    public void frontSignupClick() {
        try {
            Parent signup = FXMLLoader.load(getClass().getResource("signup.fxml"));
            rootPane.getChildren().setAll(signup);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }

    @FXML
    public void loginClick(ActionEvent e) {
        if (!idText.getText().isBlank() && !passwordText.getText().isBlank()) {
            validateLogin();
        } else {
            loginMsg.setText("pls enter credentials");
        }
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "select * from user_info where std_id = '" + idText.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryRes = statement.executeQuery(verifyLogin);

            while (queryRes.next()) {
                String hashedPassword = queryRes.getString("password");
                String enteredPassword = hashPassword(passwordText.getText());

                if (hashedPassword.equals(enteredPassword)) {


                    String name = queryRes.getString("name");
                    String mail = queryRes.getString("mail");
                    String phone = queryRes.getString("phone");

                    GlobalData.getInstance().setName(name);
                    GlobalData.getInstance().setId(Integer.parseInt(idText.getText()));
                    GlobalData.getInstance().setMail(mail);
                    GlobalData.getInstance().setPhone(phone);


                    HelloApplication.Name=name;

                    HelloApplication.std_id= Integer.parseInt(idText.getText());

                    HelloApplication.CartItems.clear();
                    String fetchCart= "select * from cart_info where std_id='" + HelloApplication.std_id + "';";
                    try{
                        Statement statement1 = connectDB.createStatement();
                        ResultSet res = statement1.executeQuery(fetchCart);
                        while(res.next()){
                            food Food;
                            Food = new food();
                            Food.setName(res.getString("name"));
                            Food.setPrice((int) Double.parseDouble(res.getString("price")));
                            Food.setImgSrc(res.getString("image"));
                            Food.setColor(res.getString("color"));
                            Food.setShop(res.getString("shop"));
                            Food.setMealType(res.getString("mealType"));
                            Food.setServingSize(Integer.parseInt(res.getString("servingSize")));
                            HelloApplication.CartItems.add(Food);
                        }
                    } catch(Exception e){
                        e.getCause();
                    }


                    try {
                        Parent login = FXMLLoader.load(getClass().getResource("First.fxml"));
                        rootPane.getChildren().setAll(login);
                    } catch (IOException ev) {
                        ev.printStackTrace();
                    }

                } else {
                    loginMsg.setText("Wrong credentials");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
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

    @FXML
    void sellerLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sellerLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signupClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
