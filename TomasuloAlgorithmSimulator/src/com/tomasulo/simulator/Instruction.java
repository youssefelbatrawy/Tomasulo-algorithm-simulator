package com.tomasulo.simulator;

import com.tomasulo.predefintions.Operations;
import com.tomasulo.predefintions.Status;

public class Instruction {
	private Operations operation;
	private String source1;
	private String source2;
	private String destination;
	private Status status;
	private int latency;
	
	public Instruction(Operations operation, String destination, String source1, String source2) {
		this.operation = operation;
		this.destination = destination;
		this.source1 = source1;
		this.source2 = source2;
		this.status = null;
	}
	
	public Operations getOperation() {
		return operation;
	}

	public void setOperation(Operations operation) {
		this.operation = operation;
	}

	public String getSource1() {
		return source1;
	}

	public void setSource1(String source1) {
		this.source1 = source1;
	}

	public String getSource2() {
		return source2;
	}

	public void setSource2(String source2) {
		this.source2 = source2;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getLatency() {
		return latency;
	}

	public void setLatency(int latency) {
		this.latency = latency;
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