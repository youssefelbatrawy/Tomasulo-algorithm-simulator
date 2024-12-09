
package com.tomasulo.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

import com.tomasulo.memory.Cache;
//import com.tomasulo.memory.Memory;
import com.tomasulo.predefintions.MemorySize;
import com.tomasulo.simulator.Pipeline;
//import com.tomasulo.simulator.SimulationLogic;
import com.tomasulo.simulator.SimulationLogic;

public class SimulatorUI extends Application {

    protected StackPane root;
    protected InputScreen1 inputScreen1;
    protected PreloadScreen2 preloadScreen2;
    protected PreloadScreen3 preloadScreen3;
    protected BufferScreen4 bufferScreen4;
    protected LatencyScreen5 latencyScreen5;
    protected MemoryCacheInputPanel memoryCacheInputPanel;
    protected OutputPanel finalScreen;

    private String filePath;
    private ArrayList<Object> instructions;
    private String[] entryNames, qIs, values, fentryNames, fqIs, fvalues;
    private String[] additionalEntryNames, additionalQIs, additionalValues, additionalFEntryNames, additionalFQIs, additionalFValues;
    private int loadBuffer, storeBuffer, adderStations, multiplierStations;
    private int memorySize, cacheSize, blockSize;
    private MemorySize wordSize;

    @Override
    public void start(Stage primaryStage) {
        root = new StackPane();

        inputScreen1 = new InputScreen1(this);
        preloadScreen2 = new PreloadScreen2(this);
        preloadScreen3 = new PreloadScreen3(this);
        bufferScreen4 = new BufferScreen4(this);
        latencyScreen5 = new LatencyScreen5(this);
        memoryCacheInputPanel = new MemoryCacheInputPanel(this);
        finalScreen = new OutputPanel();

        root.getChildren().addAll(inputScreen1, preloadScreen2, preloadScreen3, bufferScreen4, latencyScreen5, memoryCacheInputPanel, finalScreen);
        showScreen(inputScreen1);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Tomasulo Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public ArrayList<Object> getInstructions() {
		return instructions;
	}
	
    public void setInstructions(ArrayList<Object> instructions) {
        this.instructions = instructions;
    }

	public void showScreen(Pane screen) {
        root.getChildren().forEach(child -> child.setVisible(false));
        screen.setVisible(true);
    }

    public void setPreloadData(String[] entryNames, String[] qIs, String[] values, String[] fentryNames, String[] fqIs, String[] fvalues) {
        this.entryNames = entryNames;
        this.qIs = qIs;
        this.values = values;
        this.fentryNames = fentryNames;
        this.fqIs = fqIs;
        this.fvalues = fvalues;
    }

    public void setAdditionalPreloadData(String[] additionalEntryNames, String[] additionalQIs, String[] additionalValues, String[] additionalFEntryNames, String[] additionalFQIs, String[] additionalFValues) {
        this.additionalEntryNames = additionalEntryNames;
        this.additionalQIs = additionalQIs;
        this.additionalValues = additionalValues;
        this.additionalFEntryNames = additionalFEntryNames;
        this.additionalFQIs = additionalFQIs;
        this.additionalFValues = additionalFValues;
    }

    public void setBufferData(int loadBuffer, int storeBuffer, int adderStations, int multiplierStations) {
        this.loadBuffer = loadBuffer;
        this.storeBuffer = storeBuffer;
        this.adderStations = adderStations;
        this.multiplierStations = multiplierStations;
    }
    
	public void addAdditionalPreloadData(Pipeline pipeline) {
        for (int i = 0; i < additionalEntryNames.length; i++) {
        	if (additionalValues[i] != null) {
                pipeline.getRegisterFile().addRegFileEntry(additionalEntryNames[i], additionalQIs[i], additionalValues[i]);
        	}
        }
        for (int i = 0; i < additionalFEntryNames.length; i++) {
        	if (additionalFValues[i] != null) {
        		pipeline.getFloatingPointRegisterFile().addRegFileEntry(additionalFEntryNames[i], additionalFQIs[i], additionalFValues[i]);
        	}
        }
	}
	
	public void setMemoryCacheData(int memorySize, MemorySize wordSize, int cacheSize, int blockSize) {
		this.memorySize = memorySize;
		this.wordSize = wordSize;
		this.cacheSize = cacheSize;
		this.blockSize = blockSize;
	}

    public void launchSimulation() {
    	
        Pipeline pipeline = new Pipeline(instructions, entryNames, qIs, values, fentryNames, fqIs, fvalues, loadBuffer, storeBuffer, adderStations, multiplierStations);
        addAdditionalPreloadData(pipeline);
        
//        Memory memory = new Memory(memorySize, wordSize);
//
//        Cache cache = new Cache(memory, cacheSize, blockSize);
//        
//        SimulationLogic.start(pipeline, memory, cache);
        
        showScreen(finalScreen);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
