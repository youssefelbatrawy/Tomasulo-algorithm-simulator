package com.tomasulo.gui;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class BufferScreen4 extends VBox {
    private SimulatorUI parent;
    private TextField loadBufferField, storeBufferField, adderStationsField, multiplierStationsField;
    private Button nextButton;

    public BufferScreen4(SimulatorUI parent) {
        this.parent = parent;
        this.setSpacing(10);

        loadBufferField = new TextField();
        loadBufferField.setPromptText("Load Buffer Size");
        storeBufferField = new TextField();
        storeBufferField.setPromptText("Store Buffer Size");
        adderStationsField = new TextField();
        adderStationsField.setPromptText("Adder Reservation Stations");
        multiplierStationsField = new TextField();
        multiplierStationsField.setPromptText("Multiplier Reservation Stations");

        nextButton = new Button("Next");
        nextButton.setOnAction(_ -> handleNext());

        this.getChildren().addAll(new Label("Buffer and Reservation Station Sizes"), loadBufferField, storeBufferField, adderStationsField, multiplierStationsField, nextButton);
    }

    private void handleNext() {
        try {
            int loadBuffer = Integer.parseInt(loadBufferField.getText());
            int storeBuffer = Integer.parseInt(storeBufferField.getText());
            int adderStations = Integer.parseInt(adderStationsField.getText());
            int multiplierStations = Integer.parseInt(multiplierStationsField.getText());

            parent.setBufferData(loadBuffer, storeBuffer, adderStations, multiplierStations);
            parent.latencyScreen5.initialize(parent.getFilePath());
            parent.showScreen(parent.latencyScreen5);
        } catch (NumberFormatException e) {
            showAlert("Please enter valid integers for all fields.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}