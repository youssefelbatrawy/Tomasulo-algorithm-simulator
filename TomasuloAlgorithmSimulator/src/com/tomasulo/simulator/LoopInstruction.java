package com.tomasulo.simulator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LoopInstruction {

	public Queue<Instruction> instructions;
	
	public LoopInstruction(ArrayList<Instruction> instructions) {
		this.instructions = new LinkedList<>();
		for(int i = 0; i < instructions.size(); i++) {
			this.instructions.add(instructions.get(i));
		}
	}
	
	@Override
	public String toString() {
	    return "LoopInstruction{" +
	            "instructions=" + instructions +
	            '}';
	}

}
