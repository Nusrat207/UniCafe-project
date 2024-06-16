package com.example.project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class Toast {

    public static void show(String message) {
        Stage toastStage = new Stage();
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label(message);
        label.setTextFill(Color.web("#185e54"));
        label.setStyle("-fx-background-color: rgba(210, 243, 235, 0.75); -fx-padding: 20px; -fx-font-size: 23px;");

        StackPane root = new StackPane(label);
        root.setStyle("-fx-background-color: #ffffff;");

        root.setAlignment(Pos.BOTTOM_CENTER);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.setAlwaysOnTop(true);

        Timeline fadeInTimeline = new Timeline();
        KeyValue fadeInKeyValue = new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1);
        KeyFrame fadeInKeyFrame = new KeyFrame(Duration.millis(200), fadeInKeyValue);
        fadeInTimeline.getKeyFrames().add(fadeInKeyFrame);
        fadeInTimeline.setOnFinished(event -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Timeline fadeOutTimeline = new Timeline();
                KeyValue fadeOutKeyValue = new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0);
                KeyFrame fadeOutKeyFrame = new KeyFrame(Duration.millis(200), fadeOutKeyValue);
                fadeOutTimeline.getKeyFrames().add(fadeOutKeyFrame);
                fadeOutTimeline.setOnFinished(evt -> toastStage.close());
                fadeOutTimeline.play();
            }).start();
        });

        toastStage.show();
        fadeInTimeline.play();
    }


    public static void show2(String message){
        Stage toastStage = new Stage();
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label(message);
        label.setTextFill(Color.web("#e1f2f0"));
        //label.setTextFill(Color."WHITE");
        label.setStyle("-fx-background-color: rgba(13, 66, 58, 0.8); -fx-padding: 20px; -fx-font-size: 23px;");

        StackPane root = new StackPane(label);
        //root.setStyle("-fx-background-color: #387d71;");
        root.setStyle("-fx-background-color: #ffffff;");

        root.setAlignment(Pos.BOTTOM_CENTER);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.setAlwaysOnTop(true);

        Timeline fadeInTimeline = new Timeline();
        KeyValue fadeInKeyValue = new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1);
        KeyFrame fadeInKeyFrame = new KeyFrame(Duration.millis(200), fadeInKeyValue);
        fadeInTimeline.getKeyFrames().add(fadeInKeyFrame);
        fadeInTimeline.setOnFinished(event -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Timeline fadeOutTimeline = new Timeline();
                KeyValue fadeOutKeyValue = new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0);
                KeyFrame fadeOutKeyFrame = new KeyFrame(Duration.millis(200), fadeOutKeyValue);
                fadeOutTimeline.getKeyFrames().add(fadeOutKeyFrame);
                fadeOutTimeline.setOnFinished(evt -> toastStage.close());
                fadeOutTimeline.play();
            }).start();
        });

        toastStage.show();
        fadeInTimeline.play();
    }
}
