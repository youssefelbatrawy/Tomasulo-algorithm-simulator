package com.tomasulo.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.tomasulo.predefintions.MemorySize;

public class MemoryCacheInputPanel extends VBox {
    private SimulatorUI parent;
    private TextField memorySizeField;
    private ComboBox<MemorySize> wordSizeComboBox;
    private TextField cacheSizeField;
    private TextField blockSizeField;
    private Button nextButton;

    public MemoryCacheInputPanel(SimulatorUI parent) {
        this.parent = parent;
        this.setSpacing(10);
        this.setPadding(new Insets(10));

        memorySizeField = new TextField();
        wordSizeComboBox = new ComboBox<>();
        wordSizeComboBox.getItems().addAll(MemorySize.values());
        cacheSizeField = new TextField();
        blockSizeField = new TextField();
        nextButton = new Button("Next");

        VBox memorySection = new VBox(10);
        memorySection.getChildren().addAll(
            new Label("Memory Size (int):"), memorySizeField,
            new Label("Memory Word Size (enum):"), wordSizeComboBox
        );
        TitledPane memoryPane = new TitledPane("Memory", memorySection);

        VBox cacheSection = new VBox(10);
        cacheSection.getChildren().addAll(
            new Label("Cache Size (int):"), cacheSizeField,
            new Label("Cache Block Size (int):"), blockSizeField
        );
        TitledPane cachePane = new TitledPane("Cache", cacheSection);

        this.getChildren().addAll(memoryPane, cachePane, nextButton);

        nextButton.setOnAction(_ -> handleNext());
    }

    private void handleNext() {
        int memorySize = Integer.parseInt(memorySizeField.getText());
        MemorySize wordSize = wordSizeComboBox.getValue();
        int cacheSize = Integer.parseInt(cacheSizeField.getText());
        int blockSize = Integer.parseInt(blockSizeField.getText());

        parent.setMemoryCacheData(memorySize, wordSize, cacheSize, blockSize);
        parent.launchSimulation();
    }
}