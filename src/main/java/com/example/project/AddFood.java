package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddFood {
    @FXML
    private ChoiceBox<String> CB;
    @FXML
    private Button add;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button uploadButton;
    private FileChooser fileChooser;
    private String[] meals= {"breakfast", "lunch", "dinner", "snacks"};
    private int imgUp=0;
    private String fileName="";

    public void initialize() {
        CB.getItems().addAll(meals);

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        uploadButton.setOnAction(e -> {
            Stage stage = (Stage) uploadButton.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
                System.out.println("Image uploaded to ImageView");
                imgUp=1;
                fileName = selectedFile.getName();

             /*   File saveDir = new File("C:/Users/Asus/IdeaProjects/project/src/main/resources/com/example/project/img");
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                File savedFile = new File(saveDir, selectedFile.getName());
                try {
                    Files.copy(selectedFile.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Image saved");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } */
                File saveDir = new File("C:/Users/Asus/IdeaProjects/project/src/main/resources/com/example/project/img");
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                File savedFile = new File(saveDir, selectedFile.getName());

                if (savedFile.exists()) {
                    //System.out.println("Image already exists, not saving.");
                } else {
                    try {
                        Files.copy(selectedFile.toPath(), savedFile.toPath());
                        //System.out.println("Image saved");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    public void addClick(ActionEvent e) throws SQLException, IOException {
        String meal = CB.getValue();
        if(!name.getText().isBlank() && !price.getText().isBlank() && meal!=null && imgUp==1){

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String query = "INSERT INTO food_info (shop, name, price, image, color, mealType) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setString(1, HelloApplication.sellerShop);
            statement.setString(2, name.getText());
            statement.setString(3, price.getText());
            String img="/com/example/project/img/" + fileName;
            statement.setString(4, img);
            String color="A7745B";
            statement.setString(5,color);
            statement.setString(6, meal);
            statement.executeUpdate();
            connectDB.close();

            showToast("Food added to Menu");

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sellerFront.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 775, 706);
            Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void backClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sellerFront.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    private void showToast(String message) {
        Toast.show2(message);
    }
}
