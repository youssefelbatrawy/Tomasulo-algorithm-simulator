package com.tomasulo.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;

public class InputPanel extends VBox {

    private TextField latencyField;
    private TextField reservationStationSizeField;
    private TextField cacheSizeField;
    private TextField blockSizeField;

    private TextField loadBufferField;
    private TextField storeBufferField;
    private TextField addBufferField;
    private TextField multiplyBufferField;
    private TextField divideBufferField;

    private FileChooser fileChooser;
    private Button loadFileButton;
    private Button submitButton;
    private TextArea instructionArea;

    public InputPanel() {
        // Set layout properties
        this.setSpacing(10);
        this.setPadding(new Insets(10));

        // Instruction input
        instructionArea = new TextArea();
        instructionArea.setPromptText("Enter MIPS instructions or load a file...");
        instructionArea.setPrefHeight(200);
        this.getChildren().add(createSection("Instructions", instructionArea));

        // File chooser
        fileChooser = new FileChooser();
        loadFileButton = new Button("Load Instructions File");
        loadFileButton.setOnAction(e -> handleFileLoad());
        this.getChildren().add(loadFileButton);

        // Simulation settings
        latencyField = createTextField("Enter instruction latencies (comma-separated):");
        reservationStationSizeField = createTextField("Reservation Station Size:");
        cacheSizeField = createTextField("Cache Size:");
        blockSizeField = createTextField("Block Size:");

        this.getChildren().addAll(
                createSection("Simulation Settings", latencyField),
                createSection("Simulation Settings", reservationStationSizeField),
                createSection("Simulation Settings", cacheSizeField),
                createSection("Simulation Settings", blockSizeField)
        );

        // Buffer configuration
        loadBufferField = createTextField("Number of Load Buffers:");
        storeBufferField = createTextField("Number of Store Buffers:");
        addBufferField = createTextField("Number of Add Buffers:");
        multiplyBufferField = createTextField("Number of Multiply Buffers:");
        divideBufferField = createTextField("Number of Divide Buffers:");

        this.getChildren().addAll(
                createSection("Buffer Configuration", loadBufferField),
                createSection("Buffer Configuration", storeBufferField),
                createSection("Buffer Configuration", addBufferField),
                createSection("Buffer Configuration", multiplyBufferField),
                createSection("Buffer Configuration", divideBufferField)
        );

        // Submit button
        submitButton = new Button("Submit");
        submitButton.setOnAction(e -> handleSubmit());
        this.getChildren().add(submitButton);
    }

    private VBox createSection(String title, Control control) {
        VBox section = new VBox();
        section.setSpacing(5);
        Label label = new Label(title);
        section.getChildren().addAll(label, control);
        return section;
    }

    private TextField createTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        return textField;
    }

    private void handleFileLoad() {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            instructionArea.setText("Loaded file: " + file.getAbsolutePath());
        }
    }

    private void handleSubmit() {
        if (latencyField.getText().isEmpty() ||
                reservationStationSizeField.getText().isEmpty() ||
                cacheSizeField.getText().isEmpty() ||
                blockSizeField.getText().isEmpty() ||
                loadBufferField.getText().isEmpty() ||
                storeBufferField.getText().isEmpty() ||
                addBufferField.getText().isEmpty() ||
                multiplyBufferField.getText().isEmpty() ||
                divideBufferField.getText().isEmpty()) {
            System.out.println("All fields must be filled!");
            return;
        }

        System.out.println("Submitted settings:");
        System.out.println("Instructions: " + instructionArea.getText());
        System.out.println("Latencies: " + latencyField.getText());
        System.out.println("Reservation Station Size: " + reservationStationSizeField.getText());
        System.out.println("Cache Size: " + cacheSizeField.getText());
        System.out.println("Block Size: " + blockSizeField.getText());
        System.out.println("Load Buffers: " + loadBufferField.getText());
        System.out.println("Store Buffers: " + storeBufferField.getText());
        System.out.println("Add Buffers: " + addBufferField.getText());
        System.out.println("Multiply Buffers: " + multiplyBufferField.getText());
        System.out.println("Divide Buffers: " + divideBufferField.getText());
    }
    
	public static String[] getRegisterFileQ_isArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String[] getRegisterFileValuesArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public static int getLoadBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int getStoreBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int getAddersReservationStationSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static int getMultipliersReservationStationSize() {
		// TODO Auto-generated method stub
		return 0;
	}
}