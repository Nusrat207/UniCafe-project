package com.example.project;

import com.example.project.model.food;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class OrderItem {
    @FXML
    private Label food;
    @FXML
    private Label meal;
    @FXML
    private Label price;
    @FXML
    private Label qty;
    @FXML
    private Label shop;
@FXML
private AnchorPane root;
    @FXML
    private Label time;
    public void setData(food item) {

        food.setText(item.getName());
        meal.setText(item.getMealType());
        price.setText(String.valueOf(item.getPrice()));
        qty.setText(String.valueOf(item.getServingSize()));
        shop.setText(item.getShop());
        time.setText(item.getTime());
    }
}
