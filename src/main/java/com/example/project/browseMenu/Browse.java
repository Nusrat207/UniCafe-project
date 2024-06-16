/*package com.example.project.browseMenu;

import com.example.project.DatabaseConnection;
import com.example.project.GlobalData;
import com.example.project.HelloApplication;
import com.example.project.firstpageController;
import com.example.project.model.food;
import com.example.project.itemsController.items;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.project.SignupController.hashPassword;

public class Browse implements Initializable {

    @FXML
    private VBox chosenFood;

    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;

    @FXML
    private Label foodPrice;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private List<food> foods = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    private List<food> getData() {
        List<food>foods = new ArrayList<>();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
       // String query = "select * from food_info";
        String queryy= "select * from food_info where shop='" + HelloApplication.Shop + "' and mealType='" + HelloApplication.Meal + "';";
        //System.out.println(queryy);
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
                foods.add(Food);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }




        return foods;
    }

//    private void setChosenFood(food Food) {
//        foodLabel.setText(Food.getName());
//        foodPrice.setText(HelloApplication.CURRENCY + Food.getPrice());
//        image = new Image(getClass().getResourceAsStream(Food.getImgSrc()));
//        foodImg.setImage(image);
//        chosenFood.setStyle("-fx-background-color: #" + Food.getColor() + ";\n" +
//                "    -fx-background-radius: 30;");
//    }


    private void setChosenFood(food Food) {
        foodLabel.setText(Food.getName());
        foodPrice.setText(HelloApplication.CURRENCY + Food.getPrice());
        //image = new Image(getClass().getClassLoader().getResourceAsStream(Food.getImgSrc()));
        //Image image = new Image(getClass().getResourceAsStream("/com/example/project/img/burger.jpg"));
        image = new Image(getClass().getResourceAsStream(Food.getImgSrc()));
        foodImg.setImage(image);
        chosenFood.setStyle("-fx-background-color: #" + Food.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foods.addAll(getData());
        if (foods.size() > 0) {
            setChosenFood(foods.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(food Food) {
                    setChosenFood(Food);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < foods.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/project/items.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                items itemsController = fxmlLoader.getController();
                itemsController.setData(foods.get(i),myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("menubar.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        //Scene scene=new Scene(fxmlLoader.load());
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        stage.setScene(scene);
        //stage.setTitle("WestShop !!");
        stage.show();
    }

} */
package com.example.project.browseMenu;

import com.example.project.*;
import com.example.project.model.food;
import com.example.project.itemsController.items;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.Timestamp;
import static com.example.project.SignupController.hashPassword;

public class Browse implements Initializable {

    @FXML
    private VBox chosenFood;

    @FXML
    private ImageView foodImg;

    @FXML
    private Label foodLabel;

    @FXML
    private Label foodPrice;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private List<food> foods = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    private CartListener cartListener;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ComboBox<Integer> servingComboBox;
    @FXML
    private Label mealName;
    @FXML
    private TextField searchText;
    private int selectedIndex = 0;

    private int selectedServing;

    private List<food> getData() {
        List<food>foods = new ArrayList<>();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String queryy= "select * from food_info where shop='" + HelloApplication.Shop + "' and mealType='" + HelloApplication.Meal + "';";
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

        return foods;
    }



    private void setChosenFood(food Food) {
        foodLabel.setText(Food.getName());
        foodPrice.setText(HelloApplication.CURRENCY + Food.getPrice());
        //image = new Image(getClass().getClassLoader().getResourceAsStream(Food.getImgSrc()));
        //Image image = new Image(getClass().getResourceAsStream("/com/example/project/img/burger.jpg"));
        image = new Image(getClass().getResourceAsStream(Food.getImgSrc()));
        foodImg.setImage(image);
        chosenFood.setStyle("-fx-background-color: #" + Food.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String str=HelloApplication.Meal;
        String meal= str.substring(0, 1).toUpperCase() + str.substring(1);

        mealName.setText(meal + " Menu");

        foods.addAll(getData());
        servingComboBox.getItems().addAll(1, 2, 3,4,5);
        if (foods.size() > 0) {
            setChosenFood(foods.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(food Food, int index) {
                    setChosenFood(Food);
                    updateSelectedIndex(index);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < foods.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/project/items.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                items itemsController = fxmlLoader.getController();
                itemsController.setData(foods.get(i),i, myListener);

                if (column == 2) {
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

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("menubar.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        stage.setScene(scene);
        stage.show();
    }


    private void updateSelectedIndex(int index) {
        selectedIndex = index;
    }
    public void addingTocart(food Food) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(currentTimeMillis);

            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String query = "INSERT INTO cart_info (shop, name, price, image, color, mealType, servingSize, std_id, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setString(1, Food.getShop());
            statement.setString(2, Food.getName());
            statement.setDouble(3, Food.getPrice() * selectedServing);
            statement.setString(4, Food.getImgSrc());
            statement.setString(5, Food.getColor());
            statement.setString(6, Food.getMealType());
            statement.setInt(7, selectedServing);
            statement.setInt(8, HelloApplication.std_id);
            statement.setString(9, String.valueOf(timestamp));
            statement.executeUpdate();
            System.out.println("Item added to cart successfully!");

            showToast("Added to cart!");

            HelloApplication.CartItems.add(Food);
            connectDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addtocart(ActionEvent e) throws IOException {
        if (selectedIndex != -1 && selectedIndex < foods.size()) {
            food selectedFood = foods.get(selectedIndex);
            Integer selectedServingValue = servingComboBox.getValue();
            if (selectedServingValue != null) {
                selectedServing = selectedServingValue.intValue();
            } else {
                System.out.println("No serving size selected.");
            }

            addingTocart(selectedFood);
        } else {
            System.out.println("No item selected.");
        }
    }

    @FXML
    public void myprofile(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Browse.class.getResource("myprofile_new.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        stage.setScene(scene);
        stage.show();
        System.out.println("hoynaakas.");
    }

    @FXML
    public void mycartClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("cart.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void searchClick(){
        if(!searchText.getText().isBlank()){
            String searchWord= searchText.getText().toLowerCase();
            searchWord = searchWord.substring(0, 1).toUpperCase() + searchWord.substring(1);
            System.out.println(searchWord);

            List<food> searchItems = new ArrayList<>();
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String queryy = "SELECT * FROM food_info WHERE name LIKE ? AND shop = ? AND mealType = ?";
            try {
                PreparedStatement pstmt = connectDB.prepareStatement(queryy);
                String searchType = "%" + searchWord + "%";
                pstmt.setString(1, searchType);
                pstmt.setString(2, HelloApplication.Shop);
                pstmt.setString(3, HelloApplication.Meal);
                ResultSet queryRes = pstmt.executeQuery();

                while (queryRes.next()) {
                    food item = new food();
                    item.setName(queryRes.getString("name"));
                    item.setPrice(Integer.parseInt(queryRes.getString("price")));
                    item.setImgSrc(queryRes.getString("image"));
                    item.setColor(queryRes.getString("color"));
                    item.setShop(queryRes.getString("shop"));
                    item.setMealType(queryRes.getString("mealType"));
                   searchItems.add(item);
                }

                loadItems(searchItems);
            } catch (Exception ex) {
                ex.printStackTrace();
                ex.getCause();
            }
        }
    }

    public void loadItems(List <food>foods) {
        //foods.addAll(getData());
        grid.getChildren().clear();
        servingComboBox.getItems().addAll(1, 2, 3,4,5);
        if (foods.size() > 0) {
            setChosenFood(foods.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(food Food, int index) {
                    setChosenFood(Food);
                    updateSelectedIndex(index);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < foods.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/example/project/items.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                items itemsController = fxmlLoader.getController();
                itemsController.setData(foods.get(i),i, myListener);

                if (column == 2) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void homeClick(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(firstpageController.class.getResource("First.fxml"));
        Stage stage=(Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 775, 706);
        stage.setScene(scene);
        stage.show();
    }


    private void showToast(String message) {
        Toast.show2(message);
    }

}