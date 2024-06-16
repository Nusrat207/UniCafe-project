package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Expense {
    int todaySpending=0;
    @FXML
    private Label today;
    @FXML
    private LineChart<String, Number> lineChart;
    int[] monthlySpending = new int[12];
    public void initialize()
    {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        if (connectDB != null) {
            try {
                int currentYear = LocalDate.now().getYear();
                String stdId = String.valueOf(HelloApplication.std_id);

                String todaySql = "SELECT SUM(price) FROM order_info WHERE DATE(time) = CURDATE() AND std_id = ?";

                String sql = "SELECT SUM(price), MONTH(time) FROM order_info WHERE YEAR(time) = ? AND std_id = ? GROUP BY MONTH(time)";

                try (PreparedStatement statement = connectDB.prepareStatement(sql)) {
                    statement.setInt(1, currentYear);
                    statement.setString(2, stdId);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int month = resultSet.getInt(2) - 1;
                            int spending = resultSet.getInt(1);
                            monthlySpending[month] = spending;
                        }
                    }
                }

                for (int i = 0; i < 12; i++) {
                    System.out.println("Month " + (i + 1) + ": " + monthlySpending[i]);
                }
                try (PreparedStatement statement = connectDB.prepareStatement(todaySql)) {
                    statement.setString(1, stdId);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            todaySpending = resultSet.getInt(1);
                        }
                    }
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

            today.setText(String.valueOf(todaySpending) + " Tk");

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Monthly Spending");
            String[] months = {"January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"};

            for (int i = 0; i < 12; i++) {
                series.getData().add(new XYChart.Data<>(months[i], monthlySpending[i]));
            }
            lineChart.getData().add(series);
        }
        else {
            System.out.println("Failed to connect to the database.");
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




