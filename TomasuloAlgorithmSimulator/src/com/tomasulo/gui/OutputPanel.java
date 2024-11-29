package com.tomasulo.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class OutputPanel extends VBox {

    private TableView<String> reservationTable;
    private TableView<String> registerTable;
    private TableView<String> cacheTable;
    private TextArea logArea;

    public OutputPanel() {
        // Set layout properties
        this.setSpacing(10);
        this.setPadding(new Insets(10));

        // Simulation tables
        reservationTable = createTable("Reservation Stations");
        registerTable = createTable("Register File");
        cacheTable = createTable("Cache Content");

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPromptText("Cycle-by-cycle logs will appear here...");
        logArea.setPrefHeight(150);

        // Add components
        this.getChildren().addAll(
                createSection("Reservation Stations", reservationTable),
                createSection("Register File", registerTable),
                createSection("Cache Content", cacheTable),
                createSection("Execution Logs", logArea)
        );
    }

    private VBox createSection(String title, Control control) {
        VBox section = new VBox();
        section.setSpacing(5);
        Label label = new Label(title);
        section.getChildren().addAll(label, control);
        return section;
    }

    private TableView<String> createTable(String placeholderText) {
        TableView<String> table = new TableView<>();
        table.setPlaceholder(new Text(placeholderText + " will appear here."));
        table.setPrefHeight(200);
        return table;
    }

    public void updateReservationTable(/* data */) {
        // Logic to update reservation table
    }

    public void updateRegisterTable(/* data */) {
        // Logic to update register table
    }

    public void updateCacheTable(/* data */) {
        // Logic to update cache table
    }

    public void appendLog(String logMessage) {
        logArea.appendText(logMessage + "\n");
    }
}