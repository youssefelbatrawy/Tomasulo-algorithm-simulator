package com.tomasulo.gui;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

import com.tomasulo.predefintions.Operations;
import com.tomasulo.simulator.Instruction;
import com.tomasulo.simulator.LoopInstruction;
import com.tomasulo.utils.InstructionParser;

public class LatencyScreen5 extends VBox {

    private SimulatorUI parent;
    private VBox operationInputs;
    private Button nextButton;

    public LatencyScreen5(SimulatorUI parent) {
        this.parent = parent;
        this.setSpacing(10);

        operationInputs = new VBox();
        nextButton = new Button("Start Simulation");
        this.getChildren().addAll(
            new Label("Operation Latencies"),
            operationInputs,
            nextButton
        );
    }

    public void initialize(String filePath) {

    	AtomicReference<Map<Operations, Integer>> operationLatenciesRef = new AtomicReference<>(new HashMap<>());
    	try {
    	    operationLatenciesRef.set(InstructionParser.extractOperations(filePath));
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    operationInputs.getChildren().add(new Label("Failed to load operations. Please check the file path."));
    	}

    	Map<Operations, Integer> operationLatencies = operationLatenciesRef.get();

    	operationInputs.getChildren().clear();
    	for (Map.Entry<Operations, Integer> entry : operationLatencies.entrySet()) {
    	    HBox row = new HBox(10);

    	    TextField latencyField = new TextField(String.valueOf(entry.getValue()));
    	    latencyField.textProperty().addListener((_, oldValue, newValue) -> {
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
    	nextButton.setOnAction(_ -> handleNext(operationLatenciesRef.get()));
    }

    private void handleNext(Map<Operations, Integer> operationLatencies) {
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