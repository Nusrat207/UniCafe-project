package com.example.project;

import com.example.project.browseMenu.CartListener;
import com.example.project.browseMenu.MyListener;

import com.example.project.itemsController.CartItems;
import com.example.project.model.food;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.stage.StageStyle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.io.FileInputStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;



public class cartController implements Initializable {

    @FXML
    private Label bkashNo;


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
        String query =
                "SELECT SUM(price) AS total FROM cart_info WHERE shop = ? AND mealType = ?";

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
                Food.setTime(queryRes.getString("timestamp"));
                Food.setServingSize(Integer.parseInt(queryRes.getString("servingSize")));
                foods.add(Food);
                HelloApplication.CartItems.add(Food);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return foods;
    }

    @FXML
    private RadioButton cod, bkash;
    private ToggleGroup paymentToggleGroup;
    @FXML
    private TextField trxid;
    private String payMethod="", trx="", total="";
    private String[] delivery_locs = {"South Hall of Residence", "North Hall of Residence", "Female hall of residence"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        HelloApplication.CartItems.clear();

        CB.getItems().addAll(delivery_locs);

        foods.addAll(getData());

        if (foods.isEmpty()) {
            Label emptyCartLabel = new Label("Your Cart is Empty!");
            emptyCartLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
            grid.add(emptyCartLabel, 0, 0);
            GridPane.setColumnSpan(emptyCartLabel, 3);
            GridPane.setHalignment(emptyCartLabel, HPos.CENTER);
            GridPane.setValignment(emptyCartLabel, VPos.CENTER);
        } else {
            int column = 0;
            int row = 1;
            try {
                for (int i = 0; i < foods.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/com/example/project/cartitems.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    CartItems itemsController = fxmlLoader.getController();

                    itemsController.setData(foods.get(i), i, myListener, this);

                    if (column == 1) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column++, row);

                    GridPane.setMargin(anchorPane, new Insets(5));

                    total = String.valueOf(calculateTotalAmount());
                    totalAmountLabel.setText("Total Amount: " + total);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bkashNo.setVisible(false);
        trxid.setVisible(false);
        paymentToggleGroup = new ToggleGroup();
        cod.setToggleGroup(paymentToggleGroup);
        bkash.setToggleGroup(paymentToggleGroup);
        cod.setOnAction(event -> handleRadioButtonSelection());

        bkash.setOnAction(event -> handleRadioButtonSelection());

    }
    private void handleRadioButtonSelection() {
        RadioButton selectedRadioButton = (RadioButton) paymentToggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            String selectedPaymentMethod = selectedRadioButton.getText();
            payMethod= selectedPaymentMethod;
            System.out.println("Selected payment method: " + selectedPaymentMethod);
            if (bkash.isSelected()) {
                trxid.setVisible(true);
                bkashNo.setVisible(true);
            } else {
                trxid.setVisible(false);
                bkashNo.setVisible(false);
            }
        }
    }

    public void getLocation(ActionEvent event) {
        String myLoc = CB.getValue();
        System.out.println(myLoc);
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
    public void orderClick(ActionEvent event){
        try {
            trx= trxid.getText();
            if(payMethod=="BKash" && trx==""){
                showToast("enter Transaction ID of money transfer");
                return;
            }
            String selectedLocation = CB.getValue();
            if (selectedLocation != null) {
                System.out.println("Selected location: " + selectedLocation);
            }
            else {
                showToast("select delivery location");
                return;
            }

            String shopName=HelloApplication.Shop;
            String mealTime=HelloApplication.Meal;

            StringBuilder billContent = new StringBuilder();
            billContent.append("Shop: ").append(shopName).append("\n");
            billContent.append("Meal: ").append(mealTime).append("\n");
            billContent.append("Delivery Location: ").append(selectedLocation).append("\n");
            billContent.append("Payment Method: ").append(payMethod).append("\n");
            if (payMethod.equals("BKash")) {
                billContent.append("TRX ID: ").append(trx).append("\n");
            }
            billContent.append("Total Price: ").append(total).append(" TK\n\n");

            billContent.append("Ordered Items:\n");
            for (food item : HelloApplication.CartItems) {
                billContent.append("- ").append(item.getName()).append(", Quantity: ").append(item.getServingSize()).append(", Price: ").append(item.getPrice()).append(" TK\n");
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);


            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            String filename = "bill" + System.currentTimeMillis() + ".pdf";
            String downloadsPath = System.getProperty("user.home") + "/Downloads/";

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            PDType0Font font = PDType0Font.load(document, new FileInputStream("C:/Users/Asus/IdeaProjects/demoo/src/main/java/com/example/demoo/font.ttf"
            ));

            contentStream.setFont(font, 20);


            float headingX = (page.getMediaBox().getWidth() - font.getStringWidth("UniCafe") / 1000f) / 2;
            float headingY = page.getMediaBox().getHeight() - 40;
            contentStream.beginText();
            contentStream.newLineAtOffset(headingX, headingY);
            contentStream.showText("UniCafe");
            contentStream.endText();

            contentStream.setFont(font, 14);

            float shopNameX = (page.getMediaBox().getWidth() - font.getStringWidth("Shop: " + shopName) / 1000f) / 2;
            float mealTimeX = (page.getMediaBox().getWidth() - font.getStringWidth("Meal: " + mealTime) / 1000f) / 2;

            float shopNameY = headingY - 30;
            float mealTimeY = shopNameY - 20;
            contentStream.beginText();
            contentStream.newLineAtOffset(shopNameX, shopNameY);
            contentStream.showText("Shop: " + shopName);
            contentStream.newLine();
            contentStream.newLineAtOffset(shopNameX -mealTimeX - 5, -20);
            contentStream.showText( "Meal: "+mealTime);
            contentStream.endText();

            float timeX= (page.getMediaBox().getWidth() - font.getStringWidth(formattedDateTime) / 1000f) / 2;
            float timeY= mealTimeY-20;
            contentStream.beginText();
            contentStream.newLineAtOffset(timeX-25, timeY);
            contentStream.showText(formattedDateTime);
            contentStream.endText();

            float dashY=timeY-10;
            contentStream.beginText();
            contentStream.newLineAtOffset(50, dashY);
            contentStream.showText("----------------------------------------------------------------------------------------------------------------");
            contentStream.endText();


            contentStream.setFont(font, 13);
            float itemsY =dashY - 50;
            contentStream.beginText();
            contentStream.newLineAtOffset(50, itemsY);
            contentStream.showText("Ordered Items:");
            contentStream.newLine();
            for (food item : HelloApplication.CartItems) {
                itemsY -= 20;
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("- " + item.getName() + ",  Quantity: " + item.getServingSize() + ",  Price: " + item.getPrice() + " TK");
            }
            contentStream.endText();

            float locationY = itemsY - 50;
            float paymentY = locationY - 20;
            float trxY=0, totalY=0;
            if(Objects.equals(payMethod, "BKash")){
                trxY=paymentY - 20;
                totalY =trxY-40;
            }
            else{
                totalY=paymentY - 40;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(50, locationY);
            contentStream.showText("Delivery Location: " + selectedLocation);
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(50, paymentY);
            contentStream.showText("Payment Method: " + payMethod);
            contentStream.endText();

            if(Objects.equals(payMethod, "BKash")){
                contentStream.beginText();
                contentStream.newLineAtOffset(50, trxY);
                contentStream.showText("TRX ID: " + trx);
                contentStream.endText();
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(50, totalY);
            contentStream.showText("Total Price: " + total + " TK");

            contentStream.endText();

            contentStream.close();

            document.save(downloadsPath +filename);
            document.close();

            showToast("Receipt downloaded");
            System.out.println("PDF bill generated successfully!");


            //database e add
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            String query = "INSERT INTO order_info (std_id,food, price, img, time, shop, meal, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connectDB.prepareStatement(query);

            for(food Food:HelloApplication.CartItems){
                statement.setInt(1, HelloApplication.std_id);
                statement.setString(2, Food.getName());
                statement.setString(3, String.valueOf(Food.getPrice()));
                statement.setString(4, Food.getImgSrc());
                statement.setString(5, formattedDateTime);
                statement.setString(6, Food.getShop());
                statement.setString(7, Food.getMealType());
                statement.setString(8,String.valueOf(Food.getServingSize()));
                statement.executeUpdate();
                System.out.println("Item added to order db successfully!");
            }

            String queryy = "DELETE FROM cart_info WHERE shop = ? AND mealType = ? AND std_id = ?";
            PreparedStatement preparedStatement = connectDB.prepareStatement(queryy);

                preparedStatement.setString(1, HelloApplication.Shop);
                preparedStatement.setString(2, HelloApplication.Meal);
                preparedStatement.setInt(3, HelloApplication.std_id);


            int rows = preparedStatement.executeUpdate();
            //System.out.println(rows + " rows removing done");


            connectDB.close();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cart.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 775, 706);
            Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            //stage.setTitle("cart2");
            stage.setScene(scene);
            stage.show();


            fxmlLoader = new FXMLLoader(getClass().getResource("BillWindow.fxml"));
            Parent root = fxmlLoader.load();
            BillWindowController controller = fxmlLoader.getController();
            controller.setBillContent(billContent.toString());

            HelloApplication.CartItems.clear();

            stage = new Stage();
            stage.setTitle("Bill");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, 400, 500));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            e.printStackTrace();
        } finally {
            try {
                connectDB.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showToast(String message) {
        Toast.show2(message);
    }

}