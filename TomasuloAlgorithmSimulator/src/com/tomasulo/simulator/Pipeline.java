package com.tomasulo.simulator;

import java.util.Queue;
import java.util.LinkedList;

public class Pipeline {
	
	Queue<Instruction> instructions;
	RegisterFile registerFile;
	LoadBuffer[] loadBuffers;
	StoreBuffer[] storeBuffers;
	ReservationStation[] adderReservationStations;
	ReservationStation[] multiplierReservationStations;
	
	public Pipeline(Instruction[] instructions, String[] fps, String[] qI, String[] values, int loadBuffer, int storeBuffer, int adderReservationStations, int multiplierReservationStations) {
		this.instructions = new LinkedList<>();
		for(int i = 0; i < instructions.length; i++) {
			this.instructions.add(instructions[i]);
		}
		
		this.registerFile = new RegisterFile(fps, qI, values);
		this.loadBuffers = new LoadBuffer[loadBuffer];
		this.storeBuffers = new StoreBuffer[storeBuffer];
		this.adderReservationStations = new ReservationStation[adderReservationStations];
		this.multiplierReservationStations = new ReservationStation[multiplierReservationStations];
	}
}