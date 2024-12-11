package com.tomasulo.simulator;

import java.util.Queue;

import com.tomasulo.memory.Cache;
import com.tomasulo.memory.Memory;
import com.tomasulo.predefintions.Status;
import com.tomasulo.utils.InstructionCategorizer;
import com.tomasulo.utils.InstructionCategorizer.OperationCategory;

public class SimulationLogic {
	
	public void start(Pipeline pipeline, Memory memory, Cache cache) {
		if (pipeline == null || memory == null || cache == null) {
			throw new IllegalArgumentException("Pipeline, Memory, and Cache must be provided.");
		}
		
		Queue<Object> instructionQueue = pipeline.getInstructions();

        while (!instructionQueue.isEmpty()) {
        	// Stage 1: Issuing
        	if (instructionQueue.peek() instanceof Instruction) {
                Instruction instruction = (Instruction) instructionQueue.peek();
                
				switch (InstructionCategorizer.whereDoesIssuingBelong(instruction.getOperation())) {
					case L:
						// Load
						if (pipeline.isThereAFreeLoadBuffer()) {
							String name = pipeline.getFreeLoadBufferName();
							
                            // Issue to Load Buffer
							pipeline.setLoadBufferEntry(name, true, instruction.getSource1());
							
							// Mark the destination register as waiting for the value
							if (instruction.getDestination().startsWith("R")) {
								pipeline.getRegisterFile().setQiValue(instruction.getDestination(), name);
							} else if (instruction.getDestination().startsWith("F")) {
								pipeline.getFloatingPointRegisterFile().setQiValue(instruction.getDestination(), name);
							}
							
							pipeline.dequeueInstruction();
                        } else {
                        	continue; // Stall
                        }
						
						break;
					case S:
						// Store operations
						if (pipeline.isThereAFreeStoreBuffer()) {
							String name = pipeline.getFreeStoreBufferName();
							
                            // Issue to Store Buffer
							pipeline.setStoreBufferEntry(name, true, instruction.getDestination(), null, instruction.getSource1());
							instruction.setStatus(Status.Issued);
							
							// Check if operands are available (aka. no dependencies): if yes -> load values, if not -> track who will produce them
							String source1 = (instruction.getSource1().contains("(") && instruction.getSource1().contains(")")) ? instruction.getSource1().substring(instruction.getSource1().indexOf('(') + 1, instruction.getSource1().indexOf(')')) : instruction.getSource1();
							
							if (source1.startsWith("R")) {
								pipeline.getRegisterFile().setQiValue(instruction.getDestination(), name);
								
								if (pipeline.getRegisterFile().getQiValue(source1) == null) {
									pipeline.setV(name, instruction.getSource1());
									pipeline.setQ(name, null);
								} else {
									pipeline.setQ(name, pipeline.getRegisterFile().getQiValue(source1));
								}
							} else if (source1.startsWith("F")) {
								pipeline.getFloatingPointRegisterFile().setQiValue(instruction.getDestination(), name);
								
								if (pipeline.getFloatingPointRegisterFile().getQiValue(source1) == null) {
									pipeline.setV(name, instruction.getSource1());
									pipeline.setQ(name, null);
								} else {
									pipeline.setQ(name, pipeline.getFloatingPointRegisterFile().getQiValue(source1));
								}
							} 
							
							pipeline.dequeueInstruction();
                        } else {
                        	continue;
                        }
						
						break;
					case A:
						if (pipeline.isThereAFreeAdder()) {
							String name = pipeline.getFreeAdderName();
							
                            // Issue to Reservation Station
							pipeline.setAdderReservationStationEntry(name, true, instruction.getOperation(), null, null, instruction.getSource1(), instruction.getSource2());
							
							String source1 = (instruction.getSource1().contains("(") && instruction.getSource1().contains(")")) ? instruction.getSource1().substring(instruction.getSource1().indexOf('(') + 1, instruction.getSource1().indexOf(')')) : instruction.getSource1();
							String source2 = (instruction.getSource2().contains("(") && instruction.getSource2().contains(")")) ? instruction.getSource2().substring(instruction.getSource2().indexOf('(') + 1, instruction.getSource2().indexOf(')')) : instruction.getSource2();
							
							if (!instruction.getDestination().equals(source1) && !instruction.getDestination().equals(source2) && !source1.equals(source2)) {
								throw new IllegalArgumentException("Source 1 and Source 2 must operate on the same register types.");
							}
							
							if (source1.startsWith("R")) {
								if (pipeline.getRegisterFile().getQiValue(source1) == null) {
									pipeline.setQ_j(name, null);
									pipeline.setV_j(name, instruction.getSource1());
								} else {
									pipeline.setQ_j(name, pipeline.getRegisterFile().getQiValue(source1));
								}
							} else if (source1.startsWith("F")) {
								if (pipeline.getFloatingPointRegisterFile().getQiValue(source1) == null) {
									pipeline.setQ_j(name, null);
									pipeline.setV_j(name, instruction.getSource1());
								} else {
									pipeline.setQ_j(name, pipeline.getFloatingPointRegisterFile().getQiValue(source1));
								}
							}
							
							if (source2.startsWith("R")) {
								if (pipeline.getRegisterFile().getQiValue(source2) == null) {
									pipeline.setQ_k(name, null);
									pipeline.setV_k(name, instruction.getSource2());
								} else {
									pipeline.setQ_k(name, pipeline.getRegisterFile().getQiValue(source2));
								}
							} else if (source2.startsWith("F")) {
								if (pipeline.getRegisterFile().getQiValue(source2) == null) {
									pipeline.setQ_k(name, null);
									pipeline.setV_k(name, instruction.getSource2());
								} else {
									pipeline.setQ_k(name, pipeline.getFloatingPointRegisterFile().getQiValue(source2));
								}
							}
							
							if (instruction.getDestination().startsWith("R")) {
								pipeline.getRegisterFile().setQiValue(instruction.getDestination(), name);
							} else if (instruction.getDestination().startsWith("F")) {
								pipeline.getFloatingPointRegisterFile().setQiValue(instruction.getDestination(), name);
							}
							
							pipeline.dequeueInstruction();
                        } else {
                        	continue;
                        }
						break;
					case M:
						// Multiply or Divide operations
						if (pipeline.isThereAFreeMultiplier()) {
                            String name = pipeline.getFreeMultiplierName();
                            // Issue to Reservation Station
                            pipeline.setMultiplierReservationStationEntry(name, true, instruction.getOperation(), null, null, instruction.getSource1(), instruction.getSource2());
                            
                            String source1 = (instruction.getSource1().contains("(") && instruction.getSource1().contains(")")) ? instruction.getSource1().substring(instruction.getSource1().indexOf('(') + 1, instruction.getSource1().indexOf(')')) : instruction.getSource1();
                            String source2 = (instruction.getSource2().contains("(") && instruction.getSource2().contains(")")) ? instruction.getSource2().substring(instruction.getSource2().indexOf('(') + 1, instruction.getSource2().indexOf(')')) : instruction.getSource2();
                            
							if (!instruction.getDestination().equals(source1) && !instruction.getDestination().equals(source2) && !source1.equals(source2)) {
								throw new IllegalArgumentException("Source 1 and Source 2 must operate on the same register types.");
							}
							
							if (source1.startsWith("R")) {
								if (pipeline.getRegisterFile().getQiValue(source1) == null) {
                                    pipeline.setQ_j(name, null);
                                    pipeline.setV_j(name, instruction.getSource1());
								} else {
									pipeline.setQ_j(name, pipeline.getRegisterFile().getQiValue(source1));
								}
                            } else if (source1.startsWith("F")) {
                                if (pipeline.getFloatingPointRegisterFile().getQiValue(source1) == null) {
                                    pipeline.setQ_j(name, null);
                                    pipeline.setV_j(name, instruction.getSource1());
								} else {
									pipeline.setQ_j(name, pipeline.getFloatingPointRegisterFile().getQiValue(source1));
								}
                            }
							
							if (source2.startsWith("R")) {
								if (pipeline.getRegisterFile().getQiValue(source2) == null) {
                                    pipeline.setQ_k(name, null);
                                    pipeline.setV_k(name, instruction.getSource2());
                                } else {
                                	pipeline.setQ_k(name, pipeline.getRegisterFile().getQiValue(source2));
                                }
                            } else if (source2.startsWith("F")) {
                                if (pipeline.getFloatingPointRegisterFile().getQiValue(source2) == null) {
                                    pipeline.setQ_k(name, null);
                                    pipeline.setV_k(name, instruction.getSource2());
								} else {
									pipeline.setQ_k(name, pipeline.getFloatingPointRegisterFile().getQiValue(source2));
								}
                            }
							
							if (instruction.getDestination().startsWith("R")) {
								pipeline.getRegisterFile().setQiValue(instruction.getDestination(), name);
                            } else if (instruction.getDestination().startsWith("F")) {
                                pipeline.getFloatingPointRegisterFile().setQiValue(instruction.getDestination(), name);
							}
							
							pipeline.dequeueInstruction();
                        } else {
                        	continue;
                        }
						
						break;
					default:
						throw new IllegalArgumentException("Unknown operation destination: " + InstructionCategorizer.whereDoesIssuingBelong(instruction.getOperation()));
				}
                
        	} else if (instructionQueue.peek() instanceof LoopInstruction) {
                LoopInstruction instruction = (LoopInstruction) instructionQueue.peek();
        	}
        }
	}
}