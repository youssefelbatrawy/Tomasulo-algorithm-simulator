
package com.tomasulo.gui;

import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tomasulo.utils.InstructionParser;

public class PreloadScreen2 extends VBox {
	
    private SimulatorUI parent;
    private VBox registerInputs, floatingRegisterInputs;
    private Button nextButton;

    public PreloadScreen2(SimulatorUI parent) {
        this.parent = parent;
        this.setSpacing(10);

        registerInputs = new VBox();
        floatingRegisterInputs = new VBox();
        nextButton = new Button("Next");
        nextButton.setOnAction(_ -> handleNext());

        this.getChildren().addAll(
            new Label("Destination Registers Preloading"),
            new Label("Integer Registers:"),
            registerInputs,
            new Label("Floating-Point Registers:"),
            floatingRegisterInputs,
            nextButton
        );
    }

    public void initialize(String filePath) {
        registerInputs.getChildren().clear();
        floatingRegisterInputs.getChildren().clear();

        try {
            List<String> destinations = Arrays.asList(InstructionParser.extractDestinations(filePath));
            for (String reg : destinations) {
                HBox regRow = createRegisterRow(reg);
                if (reg.startsWith("R")) {
                    registerInputs.getChildren().add(regRow);
                } else if (reg.startsWith("F")) {
                    floatingRegisterInputs.getChildren().add(regRow);
                }
            }
        } catch (Exception e) {
            showAlert("Error extracting destinations: " + e.getMessage());
        }
    }

    private HBox createRegisterRow(String regName) {
        HBox row = new HBox(10);
        Label regLabel = new Label(regName);
        TextField valueField = new TextField();
        valueField.setPromptText("Value");

        row.getChildren().addAll(regLabel, valueField);
        return row;
    }
    
    private void handleNext() {
        try {
        	
        	// Pre-load Integer Registers
            String[] entryNames = new String[registerInputs.getChildren().size()];
            String[] qIs = new String[registerInputs.getChildren().size()];
            String[] values = new String[registerInputs.getChildren().size()];

            for (int i = 0; i < registerInputs.getChildren().size(); i++) {
                HBox row = (HBox) registerInputs.getChildren().get(i);
                entryNames[i] = ((Label) row.getChildren().get(0)).getText();
                TextField valueField = (TextField) row.getChildren().get(1);
                values[i] = valueField.getText();
                if (values[i].isEmpty()) {
                    values[i] = null;
                    qIs[i] = null;
                } else {
                    qIs[i] = "0";
                }
            }
            
            //Pre-load Floating-Point Registers
            String[] fentryNames = new String[floatingRegisterInputs.getChildren().size()];
            String[] fqIs = new String[floatingRegisterInputs.getChildren().size()];
            String[] fvalues = new String[floatingRegisterInputs.getChildren().size()];

            for (int i = 0; i < floatingRegisterInputs.getChildren().size(); i++) {
                HBox row = (HBox) floatingRegisterInputs.getChildren().get(i);
                fentryNames[i] = ((Label) row.getChildren().get(0)).getText();
                TextField valueField = (TextField) row.getChildren().get(1);
                fvalues[i] = valueField.getText();
                if (fvalues[i].isEmpty()) {
                    fvalues[i] = null;
                    fqIs[i] = null;
                } else {
                    fqIs[i] = "0";
                }
            }

            parent.setPreloadData(entryNames, qIs, values, fentryNames, fqIs, fvalues);

            List<String> destinationRegisters = new ArrayList<>();
            destinationRegisters.addAll(Arrays.asList(entryNames));
            destinationRegisters.addAll(Arrays.asList(fentryNames));

            parent.preloadScreen3.initialize(parent.getFilePath(), destinationRegisters);
            parent.showScreen(parent.preloadScreen3);
        } catch (Exception e) {
            showAlert("Error processing register data: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
