package com.example.project;

import com.example.project.model.food;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class MenuItem {
    @FXML
    private ImageView imgView;

    @FXML
    private Label meal;

    @FXML
    private Label name;

    @FXML
    private Label price;

   /* public void setData(food item){
        name.setText(item.getName());
        meal.setText(item.getMealType());
        price.setText(String.valueOf(item.getPrice()) + "TK");

        Image image = new Image(getClass().getResourceAsStream(item.getImgSrc()));
      //  System.out.println(item.getImgSrc());
        imgView.setImage(image);


    } */

    public void setData(food item) {
        name.setText(item.getName());
        meal.setText(item.getMealType());
        price.setText(String.valueOf(item.getPrice()) + "TK");

       Image image = loadImageWithRetry(item.getImgSrc(), 5, 200);
        if (image != null) {
            imgView.setImage(image);
        } else {
           // System.out.println("Failed to load image: " + item.getImgSrc());
        }

    }

    private Image loadImageWithRetry(String imagePath, int maxRetries, int delay) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                if (image != null && image.getWidth() > 0) {
                    return image;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return null;
    }


}
