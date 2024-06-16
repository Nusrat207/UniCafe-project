package com.example.project.itemsController;

import com.example.project.HelloApplication;
import com.example.project.browseMenu.MyListener;
import com.example.project.cartController;
import com.example.project.model.food;

import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class items {
    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    private food Food;
    private MyListener myListener;

    private cartController carty;
    private int index;

    public void setData(food Food,int index,  MyListener myListener) {
        this.Food = Food;
        this.index = index;
        this.myListener = myListener;

        nameLabel.setText(Food.getName());
        priceLabel.setText(  HelloApplication.CURRENCY + Food.getPrice());
        Image image = new Image(getClass().getResourceAsStream(Food.getImgSrc()));
        img.setImage(image);
    }

    @FXML
    void click(Event event) {
        myListener.onClickListener(Food, index);

    }
}