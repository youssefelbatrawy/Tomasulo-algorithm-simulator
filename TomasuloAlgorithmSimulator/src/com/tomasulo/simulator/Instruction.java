package com.tomasulo.simulator;

import com.tomasulo.predefintions.Operand;
import com.tomasulo.predefintions.Status;

public class Instruction {
	int opcode;
	int opCycle;
	String[] sourceAndDestination;
	Operand operand; 
	String effectiveAddress;
	Status status;
	int latency; // Cycles

	public Instruction(int OpCycle, String[] sourceAndDestination, Operand operand, String effectiveAddress, Status status, int latency) {
		this.opcode = 0;
		this.sourceAndDestination = sourceAndDestination;
		this.operand = operand;
		this.effectiveAddress = effectiveAddress;
		this.status = status;
		this.latency = latency;
	}
}
