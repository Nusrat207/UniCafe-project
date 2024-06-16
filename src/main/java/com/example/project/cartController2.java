package com.example.project;

import com.example.project.browseMenu.CartListener;
import com.example.project.browseMenu.MyListener;
//import com.example.project.itemsController.cartitems;
import com.example.project.itemsController.CartItems;
import com.example.project.model.food;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class cartController2 implements Initializable {
    @FXML
    private Label deliever;

    @FXML
    private ChoiceBox<String> CB;

    private List<food> foods = new ArrayList<>();

    private MyListener myListener;
    @FXML
    private GridPane grid;


    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label totalAmountLabel;

    private int calculateTotalAmount() {
        int totalAmount = 0;
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String query = "SELECT SUM(price) AS total FROM cart_info WHERE shop = ? AND mealType = ?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, HelloApplication.Shop);
            preparedStatement.setString(2, HelloApplication.Meal);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalAmount = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return totalAmount;
    }

    private List<food> getData() {
        List<food> foods = new ArrayList<>();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String queryy = "select * from cart_info where shop='" + HelloApplication.Shop + "' and mealType='" + HelloApplication.Meal + "' and std_id='" + HelloApplication.std_id +"';";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryRes = statement.executeQuery(queryy);

            while (queryRes.next()) {
                food Food;
                Food = new food();
                Food.setName(queryRes.getString("name"));
                Food.setPrice((int) Double.parseDouble(queryRes.getString("price")));
                Food.setImgSrc(queryRes.getString("image"));
                Food.setColor(queryRes.getString("color"));
                Food.setShop(queryRes.getString("shop"));
                Food.setMealType(queryRes.getString("mealType"));
                foods.add(Food);
                HelloApplication.CartItems.add(Food);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return foods;
    }

    private String[] delivery_locs = {"Male South Hall", "Male North Hall", "Female Hall"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        HelloApplication.CartItems.clear();
        CB.getItems().addAll(delivery_locs);
        CB.setOnAction(this::getLocation);

        //foods=HelloApplication.CartItems;
        foods.addAll(getData());

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < foods.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/project/cartitems.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CartItems itemsController = fxmlLoader.getController();

                itemsController.setData(foods.get(i), i, myListener,this);

                if (column == 1) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);

                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);


                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));

                totalAmountLabel.setText("Total Amount: " + calculateTotalAmount() + " Tk");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getLocation(
            ActionEvent event) {

        String myLoc = CB.getValue();
        System.out.println(myLoc);
        // deliever.setText(myLoc);
    }


    @FXML
    public void backClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(cartController.class.getResource("browse.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onRemoveClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(cartController.class.getResource("browse.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        stage.setScene(scene);
        stage.show();
    }

    public void removeFromDatabase(food Food, int index) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "DELETE FROM cart_info WHERE name = ? AND shop = ? AND mealType = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, Food.getName());
            preparedStatement.setString(2, HelloApplication.Shop);
            preparedStatement.setString(3, HelloApplication.Meal);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {

                System.out.println("Item removed from the database: " + Food.getName());
                grid.getChildren().remove(index);

                int newTotalAmount = calculateTotalAmount();
                totalAmountLabel.setText("Total Amount: " + newTotalAmount);
            } else {

                System.out.println("Item not found in the database: " + Food.getName());
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        } finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}