//package com.tomasulo.simulator;
//
//import java.io.IOException;
//
//import com.tomasulo.gui.InputPanel;
//import com.tomasulo.predefintions.Operations;
//import com.tomasulo.utils.InstructionParser;
//import com.tomasulo.utils.Issuer;
//
//public class SimulationLogic {
//	
//	public static void start(Pipeline pipeline) {
//		
//		Object instructionObj;
//		while(!pipeline.instructions.isEmpty()) {
//			
//			instructionObj = pipeline.deqeueInstruction();
//			
//			if(instructionObj instanceof Instruction) {
//				Instruction instruction = (Instruction) instructionObj;
//				
//				Issuer.issuerBrain(pipeline, instruction);
//				
//			} else if(instructionObj instanceof LoopInstruction) {
//				LoopInstruction loopInstruction = (LoopInstruction) instructionObj;
//			}
//		}
//		
//	}
//}
