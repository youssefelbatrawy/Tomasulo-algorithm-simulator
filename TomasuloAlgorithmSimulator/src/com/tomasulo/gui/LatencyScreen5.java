
package com.tomasulo.gui;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.Queue;

import com.tomasulo.predefintions.Operations;
import com.tomasulo.simulator.Instruction;
import com.tomasulo.simulator.LoopInstruction;
import com.tomasulo.utils.InstructionParser;

public class LatencyScreen5 extends VBox {

	private SimulatorUI parent;
    private VBox operationInputs;
    private Button nextButton;
    private Map<Operations, Integer> operationLatencies;

    public LatencyScreen5(SimulatorUI parent) {
        this.parent = parent;
        this.setSpacing(10);

        operationInputs = new VBox();
        nextButton = new Button("Start Simulation");
        nextButton.setOnAction(e -> handleNext());

        this.getChildren().addAll(
            new Label("Operation Latencies"),
            operationInputs,
            nextButton
        );
    }

    public void initialize(String filePath) {
        try {
            operationLatencies = InstructionParser.extractOperations(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            operationLatencies = null;
        }

        operationInputs.getChildren().clear();

        if (operationLatencies != null) {
            for (Map.Entry<Operations, Integer> entry : operationLatencies.entrySet()) {
                HBox row = new HBox(10);
                TextField latencyField = new TextField(String.valueOf(entry.getValue()));

                latencyField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.trim().isEmpty()) {
                        operationLatencies.put(entry.getKey(), 1);
                    } else {
                        try {
                            int latency = Integer.parseInt(newValue.trim());
                            operationLatencies.put(entry.getKey(), latency);
                        } catch (NumberFormatException ex) {
                            latencyField.setText(oldValue);
                        }
                    }
                });

                row.getChildren().addAll(new Label(entry.getKey().toString()), latencyField);
                operationInputs.getChildren().add(row);
            }
        } else {
            operationInputs.getChildren().add(new Label("Failed to load operations. Please check the file path."));
        }
    }

    private void handleNext() {
        if (operationLatencies == null) {
            System.err.println("Error: operationLatencies is null. Ensure initialize() was called successfully.");
            return;
        }

        ArrayList<Object> instructions = parent.getInstructions();
        for (Object obj : instructions) {
            if (obj instanceof Instruction) {
                Instruction instruction = (Instruction) obj;
                instruction.setLatency(operationLatencies.getOrDefault(instruction.getOperation(), 1));
            } else if (obj instanceof LoopInstruction) {
                LoopInstruction loopInstruction = (LoopInstruction) obj;
                Queue<Instruction> loopInstructions = loopInstruction.getInstructions();
                for (Instruction instruction : loopInstructions) {
                    instruction.setLatency(operationLatencies.getOrDefault(instruction.getOperation(), 1));
                }
            }
        }
        
        
        parent.setInstructions(instructions);
        parent.launchSimulation();
    }
}