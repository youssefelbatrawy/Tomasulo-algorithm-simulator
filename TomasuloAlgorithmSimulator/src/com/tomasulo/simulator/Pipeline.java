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
	
	public Queue<Object> instructions = new LinkedList<>();
	public RegisterFile registerFile;
	public LoadBuffer[] loadBuffers;
	public StoreBuffer[] storeBuffers;
	public ReservationStation[] adderReservationStations;
	public ReservationStation[] multiplierReservationStations;
	
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
	
	
	// Checks if there is a free Load Buffer
	public boolean isThereAFreeLoadBuffer() {
		for(int i = 0; i < this.loadBuffers.length; i++) {
			if(!this.loadBuffers[i].isBusy) {
				return true;
			}
		}
		return false;
	}
	
	// Checks if there is a free Store Buffer
	public boolean isThereAFreeStoreBuffer() {
		for(int i = 0; i < this.storeBuffers.length; i++) {
			if(!this.storeBuffers[i].isBusy) {
				return true;
			}
		}
		return false;
	}
	
	// Checks if an Adder is free
	public boolean isThereAFreeAdder() {
		for(int i = 0; i < this.adderReservationStations.length; i++) {
			if(!this.adderReservationStations[i].isBusy) {
				return true;
			}
		}
		return false;
	}
	
	// Checks if a Multiplier is free
	public boolean isThereAFreeMultiplier() {
		for(int i = 0; i < this.multiplierReservationStations.length; i++) {
			if(!this.multiplierReservationStations[i].isBusy) {
				return true;
			}
		}
		return false;
	}
	
	// Returns first free Load Buffer index
	public int getFreeLoadBufferIndex() {
		for(int i = 0; i < this.loadBuffers.length; i++) {
			if(!this.loadBuffers[i].isBusy) {
				return i;
			}
		}
		return 00;
	}
	
	// Returns first free Store Buffer index
	public int getFreeStoreBufferIndex() {
		for(int i = 0; i < this.storeBuffers.length; i++) {
			if(!this.storeBuffers[i].isBusy) {
				return i;
			}
		}
		return 00;
	}
	
	// Returns first free Adder index
	public int getFreeAdderIndex() {
		for(int i = 0; i < this.adderReservationStations.length; i++) {
			if(!this.adderReservationStations[i].isBusy) {
				return i;
			}
		}
		return 00;
	}
	
	// Returns first free multiplier index
	public int getFreeMultiplierIndex() {
		for(int i = 0; i < this.multiplierReservationStations.length; i++) {
			if(!this.multiplierReservationStations[i].isBusy) {
				return i;
			}
		}
		return 00;
	}
}