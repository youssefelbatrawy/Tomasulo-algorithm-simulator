package com.tomasulo.simulator;

import java.util.Queue;

import com.tomasulo.predefintions.Operations;

import java.util.ArrayList;
import java.util.LinkedList;

/*
 * Print the Queue
 * 		System.out.println("Queue: " + queue);
*/
/*
 * Remove an element (FIFO order)
 * 		Instruction instruction = queue.poll();
*/
/*
 * Peek at the front of the queue without removing
 * 		Person frontPerson = queue.peek();
*/

public class Pipeline {
	
	Queue<Object> instructions = new LinkedList<>();
	RegisterFile registerFile;
	LoadBuffer[] loadBuffers;
	StoreBuffer[] storeBuffers;
	ReservationStation[] adderReservationStations;
	ReservationStation[] multiplierReservationStations;
	
	public Pipeline(ArrayList<Object> instructions, String[] fps, String[] qI, String[] values, int loadBuffer, int storeBuffer, int adderReservationStations, int multiplierReservationStations) {
		for(int i = 0; i < instructions.size(); i++) {
			this.instructions.add(instructions.get(i));
		}
		
		this.registerFile = new RegisterFile(fps, qI, values);
		this.loadBuffers = new LoadBuffer[loadBuffer];
		this.storeBuffers = new StoreBuffer[storeBuffer];
		this.adderReservationStations = new ReservationStation[adderReservationStations];
		this.multiplierReservationStations = new ReservationStation[multiplierReservationStations];
	}
	
//	// Adds new instruction in the instruction queue
//	public void addNewInstruction(Operations operation, String destination, String source1, String source2) {
//		this.instructions.add(new Instruction(operation, destination, source1, source2));
//	}
}