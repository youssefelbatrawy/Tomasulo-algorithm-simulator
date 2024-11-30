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
		for(int i = 0; i < this.loadBuffers.length; i++) {
			this.loadBuffers[i] = new LoadBuffer("L" + (i+1));
		}
		
		this.storeBuffers = new StoreBuffer[storeBuffer];
		for(int i = 0; i < this.storeBuffers.length; i++) {
			this.storeBuffers[i] = new StoreBuffer("S" + (i+1));
		}
		
		this.adderReservationStations = new ReservationStation[adderReservationStations];
		for(int i = 0; i < this.storeBuffers.length; i++) {
			this.adderReservationStations[i] = new ReservationStation("A" + (i+1));
		}
		
		this.multiplierReservationStations = new ReservationStation[multiplierReservationStations];
		for(int i = 0; i < this.storeBuffers.length; i++) {
			this.multiplierReservationStations[i] = new ReservationStation("M" + (i+1));
		}
	}
	
	public Object deqeueInstruction() {
		return this.instructions.poll();
	}
	
	
	
	
	
}