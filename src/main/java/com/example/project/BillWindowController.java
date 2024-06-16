package com.example.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class BillWindowController {
    @FXML
    private Button closeButton;
    @FXML
    private Label billContentLabel;
    public void setBillContent(String content) {
        billContentLabel.setText(content);
    }

    @FXML
    private void handleCloseButtonAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
