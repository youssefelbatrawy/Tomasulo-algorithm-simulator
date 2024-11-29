package com.tomasulo.simulator;

public abstract class Buffer {
	
	String name;
	boolean isBusy;
	String address;
	
	public Buffer(String name) {
		this.name = name;
		isBusy = false;
	}
}
