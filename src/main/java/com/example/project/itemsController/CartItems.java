package com.example.project.itemsController;

import com.example.project.DatabaseConnection;
import com.example.project.HelloApplication;
import com.example.project.browseMenu.MyListener;
import com.example.project.cartController;
import com.example.project.cartController2;
import com.example.project.model.food;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class CartItems {
    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;
    @FXML
    private Label quantity;


    private food Food;
    private MyListener myListener;

    private cartController carty;
    private cartController2 carty2;
    private int index;

    @FXML
    void click(Event event) {
        myListener.onClickListener(Food, index);

    }
    public void setData(food Food, int index, MyListener myListener, cartController carty) {
        this.Food = Food;
        this.index = index;
        this.myListener = myListener;
        this.carty = carty;
        nameLabel.setText(Food.getName());
        priceLabel.setText(HelloApplication.CURRENCY + Food.getPrice());
        quantity.setText("x " + Food.getServingSize());
        Image image = new Image(getClass().getResourceAsStream(Food.getImgSrc()));
        img.setImage(image);
    }

    public void setData(food Food, int index, MyListener myListener, cartController2 carty) {
        this.Food = Food;
        this.index = index;
        this.myListener = myListener;
        this.carty2 = carty;
        nameLabel.setText(Food.getName());
        priceLabel.setText(HelloApplication.CURRENCY + Food.getPrice());
        Image image = new Image(getClass().getResourceAsStream(Food.getImgSrc()));
        img.setImage(image);
    }


    @FXML
    public void onRemoveClick(ActionEvent event) throws IOException {

        Node source = (Node) event.getSource();
        Node anchorPane = source.getParent();
        if (anchorPane != null) {
            Node gridPane = anchorPane.getParent();
            if (gridPane instanceof GridPane) {
                Integer rowIndex = GridPane.getRowIndex(anchorPane);
                Integer colIndex = GridPane.getColumnIndex(anchorPane);
                if (rowIndex != null && colIndex != null) {
                    System.out.println("Row: " + rowIndex + ", Column: " + colIndex);

                    int columnCount = 1;
                    int i= rowIndex * columnCount + colIndex;
                    System.out.println(i);

                    String query = "DELETE FROM cart_info WHERE name = ? AND shop = ? AND mealType = ? AND std_id = ? AND timestamp = ?";

                    DatabaseConnection connectNow = new DatabaseConnection();
                    Connection connectDB = connectNow.getConnection();
                    try {
                        PreparedStatement preparedStatement = connectDB.prepareStatement(query);
                        preparedStatement.setString(1, Food.getName());
                        preparedStatement.setString(2, HelloApplication.Shop);
                        preparedStatement.setString(3, HelloApplication.Meal);
                        preparedStatement.setInt(4, HelloApplication.std_id);
                        preparedStatement.setString(5, Food.getTime());
                        int rows = preparedStatement.executeUpdate();
                        System.out.println(rows + " rows removing done");

                        HelloApplication.removed++;

                        System.out.println(HelloApplication.removed);

                            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cart.fxml"));
                            Scene scene = new Scene(fxmlLoader.load(), 775, 706);
                            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                            //stage.setTitle("cart2");
                            stage.setScene(scene);
                            stage.show();


                        } catch(Exception e){
                            e.getCause();
                         }



                } else {
                    System.out.println("AnchorPane is not inside a GridPane cell.");
                }
            } else {
                System.out.println("AnchorPane is not inside a GridPane.");
            }
        } else {
            System.out.println("Button is not inside an AnchorPane.");
        }


    }
}