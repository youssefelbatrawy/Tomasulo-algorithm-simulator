package com.tomasulo.simulator;

import com.tomasulo.predefintions.Operand;
import com.tomasulo.predefintions.Status;

public class Instruction {
	Operand operand;
	String source;
	String destination;
	Status status;
	
	public Instruction(Operand operand, String source, String destination) {
		this.operand = operand;
		this.source = source;
		this.destination = destination;
		this.status = null;
	}
}