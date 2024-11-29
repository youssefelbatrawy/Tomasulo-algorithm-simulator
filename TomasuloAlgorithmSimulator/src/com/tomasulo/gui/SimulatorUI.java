package com.tomasulo.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SimulatorUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Input and Output panels
        InputPanel inputPanel = new InputPanel();
        OutputPanel outputPanel = new OutputPanel();

        root.setLeft(inputPanel);
        root.setCenter(outputPanel);

        // Cycle control buttons
        HBox cycleControls = new HBox(10);
        Button nextCycleButton = new Button("Next Cycle");
        Button runButton = new Button("Run");
        Button resetButton = new Button("Reset");

        cycleControls.getChildren().addAll(nextCycleButton, runButton, resetButton);
        root.setBottom(cycleControls);

        // Connect buttons to backend (placeholder logic)
        nextCycleButton.setOnAction(e -> outputPanel.appendLog("Next Cycle triggered."));
        runButton.setOnAction(e -> outputPanel.appendLog("Run triggered."));
        resetButton.setOnAction(e -> outputPanel.appendLog("Reset triggered."));

        // Set up the scene
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Tomasulo Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
