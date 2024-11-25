package com.tomasulo.main;

import javafx.application.Application;
import javafx.stage.Stage;

import com.tomasulo.gui.SimulatorUI;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SimulatorUI app = new SimulatorUI();
        app.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}