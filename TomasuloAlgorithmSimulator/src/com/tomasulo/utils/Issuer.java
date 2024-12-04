//package com.tomasulo.utils;
//
//
//import com.tomasulo.predefintions.Operations;
//import com.tomasulo.simulator.Instruction;
//import com.tomasulo.simulator.LoadBuffer;
//import com.tomasulo.simulator.Pipeline;
//import com.tomasulo.simulator.ReservationStation;
//import com.tomasulo.simulator.StoreBuffer;
//
//public class Issuer {
//	
//	public static int whereDoesIssuingBelong(Operations operation) {
//	    if (operation == Operations.LW || operation == Operations.LD || 
//	        operation == Operations.L_S || operation == Operations.L_D) {
//	        return 0; // Load operations
//	    } else if (operation == Operations.SW || operation == Operations.SD || 
//	               operation == Operations.S_S || operation == Operations.S_D) {
//	        return 1; // Store operations
//	    } else if (operation == Operations.DADDI || operation == Operations.DSUBI ||
//	               operation == Operations.ADD_D || operation == Operations.ADD_S || 
//	               operation == Operations.SUB_D || operation == Operations.SUB_S) {
//	        return 2; // Add or Subtract operations
//	    } else if (operation == Operations.MUL_D || operation == Operations.MUL_S ||
//	               operation == Operations.DIV_D || operation == Operations.DIV_S) {
//	        return 3; // Multiply or Divide operations
//	    } else if (operation == Operations.BNE || operation == Operations.BEQ) {
//	        return 4; // Branching operations
//	    } else {
//	        throw new IllegalArgumentException("Unknown operation: " + operation);
//	    }
//	}
//
//	public static void issueReservationStation(ReservationStation reservationStation) {
//		
//	}
//	
//	public static void issueLoadBuffer(LoadBuffer loadBuffer) {
//		
//	}
//	
//	public static void issueStoreBuffer(StoreBuffer storeBuffer) {
//		
//	}
//	
//	public static void issuerBrain(Pipeline pipeline, Instruction instruction) {
//		int whereDoesIssuingBelong = whereDoesIssuingBelong(instruction.operation);
//		
//		if(whereDoesIssuingBelong == 0) { // Load operations
//			if(pipeline.isThereAFreeLoadBuffer()) {
//				Issuer.issueLoadBuffer(pipeline.loadBuffers[pipeline.getFreeLoadBufferIndex()]);
//			} else {
//				
//			}
//		} else if (whereDoesIssuingBelong == 1) { // Store operations
//			if(pipeline.isThereAFreeStoreBuffer()) {
//				Issuer.issueStoreBuffer(pipeline.storeBuffers[pipeline.getFreeStoreBufferIndex()]);
//			} else {
//				
//			}
//		} else if (whereDoesIssuingBelong == 2) { // Add or Subtract operations
//			if(pipeline.isThereAFreeAdder()) {
//				Issuer.issueReservationStation(pipeline.adderReservationStations[pipeline.getFreeAdderIndex()]);
//			} else {
//				
//			}
//		} else if (whereDoesIssuingBelong == 3) { // Multiply or Divide operations
//			if(pipeline.isThereAFreeMultiplier()) {
//				Issuer.issueReservationStation(pipeline.multiplierReservationStations[pipeline.getFreeMultiplierIndex()]);
//			} else {
//				
//			}
//		} else if (whereDoesIssuingBelong == 4) { // Branching operations
//			
//		}
//	}
//}
