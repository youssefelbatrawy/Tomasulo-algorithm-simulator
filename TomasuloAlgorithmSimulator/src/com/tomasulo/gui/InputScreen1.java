package com.tomasulo.gui;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

import com.tomasulo.utils.InstructionParser;

public class InputScreen1 extends VBox {
	private SimulatorUI parent;
	private TextField filePathField;
	private Button loadButton, nextButton;

	public InputScreen1(SimulatorUI parent) {
		this.parent = parent;
		this.setSpacing(10);

		filePathField = new TextField();
		filePathField.setPromptText("Enter or select instruction file path...");

		loadButton = new Button("Load File");
		loadButton.setOnAction(_ -> handleFileLoad());

		nextButton = new Button("Next");
		nextButton.setOnAction(_ -> handleNext());
		nextButton.setDisable(true);

		this.getChildren().addAll(new Label("Instruction Input"), filePathField, loadButton, nextButton);
	}

	private void handleFileLoad() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
																										
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			filePathField.setText(file.getAbsolutePath());
			nextButton.setDisable(false);
		}
	}

	private void handleNext() {
		try {
			String filePath = filePathField.getText().replace("\\", "\\\\");
			parent.setFilePath(filePath);
			ArrayList<Object> instructions = InstructionParser.parseInstructions(filePath);
			parent.setInstructions(instructions);
			parent.preloadScreen2.initialize(parent.getFilePath());
			parent.showScreen(parent.preloadScreen2);
		} catch (Exception e) {
			showAlert("Error loading instructions: " + e.getMessage());
		}
	}
	
	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
		alert.showAndWait();
	}
}