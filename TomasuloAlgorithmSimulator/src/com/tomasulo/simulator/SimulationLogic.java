package com.tomasulo.simulator;

import java.io.IOException;

import com.tomasulo.gui.InputPanel;
import com.tomasulo.predefintions.Operations;
import com.tomasulo.utils.InstructionParser;
import com.tomasulo.utils.Issuer;

public class SimulationLogic {
	
	private Pipeline pipeline;

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
		
		Object instructionObj;
		while(!pipeline.instructions.isEmpty()) {
			
			instructionObj = pipeline.deqeueInstruction();
			
			if(instructionObj instanceof Instruction) {
				Instruction instruction = (Instruction) instructionObj;
				
				Issuer.issuerBrain(pipeline, instruction);
				
			} else if(instructionObj instanceof LoopInstruction) {
				LoopInstruction loopInstruction = (LoopInstruction) instructionObj;
			}
		}
		
	}
}
