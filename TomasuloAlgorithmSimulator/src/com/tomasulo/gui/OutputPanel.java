package com.tomasulo.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class OutputPanel extends VBox {

    private TableView<String> multipliersTable;
    private TableView<String> addersTable;
    private TableView<String> loadBufferTable;
    private TableView<String> storeBufferTable;
    private TableView<String> cacheTable;
    private TableView<String> memoryTable;

    public OutputPanel() {
        // Enable filling width in the vertical box
        this.setFillWidth(true);
        this.setSpacing(10);
        this.setPadding(new Insets(10));

        // Create tables
        multipliersTable = createTable("Multipliers Reservation Stations");
        addersTable = createTable("Adders Reservation Stations");
        loadBufferTable = createTable("Load Buffer");
        storeBufferTable = createTable("Store Buffer");
        cacheTable = createTable("Cache Content");
        memoryTable = createTable("Memory Content");

        // Create sections
        VBox multipliersSection = createSection("Multipliers Reservation Stations", multipliersTable);
        VBox addersSection = createSection("Adders Reservation Stations", addersTable);
        VBox loadSection = createSection("Load Buffer", loadBufferTable);
        VBox storeSection = createSection("Store Buffer", storeBufferTable);
        VBox cacheSection = createSection("Cache", cacheTable);
        VBox memorySection = createSection("Memory", memoryTable);

        // Create horizontal groups for reservation stations and load/store buffers
        HBox reservationStationsSection = new HBox(10, multipliersSection, addersSection);
        HBox loadStoreBuffersSection = new HBox(10, loadSection, storeSection);

        // Make sure the horizontal boxes fill the entire width
        reservationStationsSection.setFillHeight(true);
        loadStoreBuffersSection.setFillHeight(true);

        // Allow these horizontal boxes to grow
        HBox.setHgrow(multipliersSection, Priority.ALWAYS);
        HBox.setHgrow(addersSection, Priority.ALWAYS);
        HBox.setHgrow(loadSection, Priority.ALWAYS);
        HBox.setHgrow(storeSection, Priority.ALWAYS);

        // Ensure each section can grow
        multipliersSection.setMaxWidth(Double.MAX_VALUE);
        addersSection.setMaxWidth(Double.MAX_VALUE);
        loadSection.setMaxWidth(Double.MAX_VALUE);
        storeSection.setMaxWidth(Double.MAX_VALUE);
        cacheSection.setMaxWidth(Double.MAX_VALUE);
        memorySection.setMaxWidth(Double.MAX_VALUE);

        // Add main components to the panel
        this.getChildren().addAll(
            reservationStationsSection,
            loadStoreBuffersSection,
            cacheSection,
            memorySection
        );

        // Allow these sections to expand vertically if desired
        VBox.setVgrow(reservationStationsSection, Priority.ALWAYS);
        VBox.setVgrow(loadStoreBuffersSection, Priority.ALWAYS);
        VBox.setVgrow(cacheSection, Priority.ALWAYS);
        VBox.setVgrow(memorySection, Priority.ALWAYS);
    }

    private VBox createSection(String title, Control control) {
        VBox section = new VBox(5);
        section.setFillWidth(true);
        Label label = new Label(title);
        section.getChildren().addAll(label, control);
        // Allow the control to expand
        if (control instanceof TableView) {
            ((TableView<?>)control).setMaxWidth(Double.MAX_VALUE);
        }
        return section;
    }

    private TableView<String> createTable(String placeholderText) {
        TableView<String> table = new TableView<>();
        table.setPlaceholder(new Text(placeholderText + " will appear here."));
        table.setPrefHeight(200);
        table.setMaxWidth(Double.MAX_VALUE);
        // Optional: If you have columns, this helps columns expand evenly
        // table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    // Update methods (implementation depends on your data model)
    public void updateMultipliersTable(/* data */) {
        // Logic to update multipliers reservation table
    }

    public void updateAddersTable(/* data */) {
        // Logic to update adders reservation table
    }

    public void updateLoadBufferTable(/* data */) {
        // Logic to update load buffer table
    }

    public void updateStoreBufferTable(/* data */) {
        // Logic to update store buffer table
    }

    public void updateCacheTable(/* data */) {
        // Logic to update cache table
    }

    public void updateMemoryTable(/* data */) {
        // Logic to update memory table
    }
}