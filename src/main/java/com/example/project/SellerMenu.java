package com.example.project;

import com.example.project.model.food;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SellerMenu {
    @FXML
    private Button back;

    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    void backClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sellerFront.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
        List<food>foods = new ArrayList<>();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String queryy= "select * from food_info where shop='" + HelloApplication.sellerShop + "';";
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
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        List<food>itemList =foods;

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        GridPane gridPane = new GridPane();
        scrollPane.setContent(gridPane);
        int columnCount = 3;
        int rowCount = (int) Math.ceil((double) itemList.size() / columnCount);

        gridPane.setHgap(5);
        gridPane.setVgap(10);

        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < columnCount; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHgrow(Priority.ALWAYS);
            columnConstraints.setPercentWidth(100.0 / columnCount);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < rowCount; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.ALWAYS);
            rowConstraints.setPercentHeight(100.0 / rowCount);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < itemList.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menuItem.fxml"));
                Node itemNode = loader.load();
                MenuItem controller = loader.getController();

                controller.setData(itemList.get(i));


                int row = i / columnCount;
                int column = i % columnCount;

                gridPane.add(itemNode, column, row);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
