package com.example.project;

import com.example.project.model.food;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scrollPane;



    public void initialize() {
        List<food> itemList = new ArrayList<>();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String queryy= "select * from order_info where std_id='" + HelloApplication.std_id + "';";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryRes = statement.executeQuery(queryy);

            while (queryRes.next()) {
                food item = new food(queryRes.getString("food"),queryRes.getInt("quantity"),queryRes.getInt("price"),queryRes.getString("shop"), queryRes.getString("meal"),queryRes.getString("time")
                );
                itemList.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        GridPane gridPane = new GridPane();
        scrollPane.setContent(gridPane);

        int columnCount = 1;
        int rowCount = 0;
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setPercentWidth(100.0 / columnCount);
        gridPane.getColumnConstraints().addAll(Collections.nCopies(columnCount, columnConstraints));

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setPercentHeight(100.0 / Math.ceil((double) itemList.size() / columnCount));
        gridPane.getRowConstraints().addAll(Collections.nCopies((int) Math.ceil((double) itemList.size() / columnCount), rowConstraints));

        for (int i = 0; i < itemList.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("orderItem.fxml"));
                Node itemNode = loader.load();
                OrderItem controller = loader.getController();

                controller.setData(itemList.get(i));

                int row = i / columnCount;
                int column = i % columnCount;

                gridPane.add(itemNode, column, row);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void backClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("First.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene=new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
