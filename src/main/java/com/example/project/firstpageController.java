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
import java.util.ResourceBundle;

public class firstpageController implements Initializable {

    @FXML
    private Label Menu, MenuBack;
    @FXML
    private AnchorPane slider, rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });
    }//

    @FXML
    public void onEastShopClicked(ActionEvent e) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("menubar.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("EastShop !!");
        stage.show();
    }

    @FXML
    public void onWestShopClicked(ActionEvent e) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("menubar.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("WestShop !!");
        stage.show();
    }

    @FXML
    public void myProfileClick(){
        try {
            Parent signup = FXMLLoader.load(getClass().getResource("myprofile.fxml"));
            rootPane.getChildren().setAll(signup);
        } catch (IOException ev) {
            ev.printStackTrace();
        }
    }

}