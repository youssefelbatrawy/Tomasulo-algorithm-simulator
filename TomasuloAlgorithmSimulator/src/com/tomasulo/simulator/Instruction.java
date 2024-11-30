package com.tomasulo.simulator;

import com.tomasulo.predefintions.Operations;
import com.tomasulo.predefintions.Status;

public class Instruction {
	Operations operation;
	String source1;
	String source2;
	String destination;
	Status status;
	
	public Instruction(Operations operation, String destination, String source1, String source2) {
		this.operation = operation;
		this.destination = destination;
		this.source1 = source1;
		this.source2 = source2;
		this.status = null;
	}
	
	@Override
	public String toString() {
	    return "Instruction{" +
	            "operation=" + operation +
	            ", destination='" + destination + '\'' +
	            ", source1='" + source1 + '\'' +
	            ", source2='" + source2 + '\'' +
	            '}';
	}
}