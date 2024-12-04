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
	
	public Queue<Instruction> getInstructions() {
        return instructions;
	}
	
	public Instruction deqeueInstruction() {
		return this.instructions.poll();
	}
	
	public Instruction peekInstruction() {
		return this.instructions.peek();
	}
	
	@Override
	public String toString() {
	    return "LoopInstruction{" + "instructions=" + instructions + '}';
	}
}
