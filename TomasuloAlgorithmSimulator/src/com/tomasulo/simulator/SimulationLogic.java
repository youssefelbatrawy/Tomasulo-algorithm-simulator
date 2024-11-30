package com.tomasulo.simulator;

import java.io.IOException;

import com.tomasulo.gui.InputPanel;
import com.tomasulo.utils.InstructionParser;

public class SimulationLogic {
	
	public void start(String filePath, int loadBuffer, int storeBuffer, int adderReservationStations, int multiplierReservationStations) {
		
		try {
			Pipeline pipeline = new Pipeline(InstructionParser.parseInstructions(filePath), 
					InstructionParser.extractDestinations(filePath), InputPanel.getRegisterFileQ_isArray(), 
					InputPanel.getRegisterFileValuesArray(), InputPanel.getLoadBufferSize(), 
					InputPanel.getStoreBufferSize(), InputPanel.getAddersReservationStationSize(), 
					InputPanel.getMultipliersReservationStationSize());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
	}
}
