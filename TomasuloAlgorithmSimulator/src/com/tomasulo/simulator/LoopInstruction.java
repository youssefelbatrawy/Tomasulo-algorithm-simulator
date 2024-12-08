package com.tomasulo.simulator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LoopInstruction {
    
    private String name;
    private Queue<Instruction> instructions;
    
    public LoopInstruction(String name, ArrayList<Instruction> instructions) {
        this.name = name;
        this.instructions = new LinkedList<>(instructions);
    }
    
    public Queue<Instruction> getInstructions() {
        return instructions;
    }
    
    public String getName() {
        return name;
    }
    
    public Instruction dequeueInstruction() {
        return this.instructions.poll();
    }
    
    public Instruction peekInstruction() {
        return this.instructions.peek();
    }
    
    @Override
    public String toString() {
        return "LoopInstruction{" + "name='" + name + '\'' + ", instructions=" + instructions + '}';
    }
}