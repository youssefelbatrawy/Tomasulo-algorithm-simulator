package com.tomasulo.utils;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import com.tomasulo.predefintions.Operations;

public class InstructionCategorizer {
	
	public enum OperationCategory {
	    L,
	    S,
	    A,
	    M,
	    B
	}

	public static OperationCategory whereDoesIssuingBelong(Operations operation) {
	    if (operation == Operations.LW || operation == Operations.LD || 
	        operation == Operations.L_S || operation == Operations.L_D) {
	        return OperationCategory.L; // Load operations
	    } else if (operation == Operations.SW || operation == Operations.SD || 
	               operation == Operations.S_S || operation == Operations.S_D) {
	        return OperationCategory.S; // Store operations
	    } else if (operation == Operations.DADDI || operation == Operations.DSUBI ||
	               operation == Operations.ADD_D || operation == Operations.ADD_S || 
	               operation == Operations.SUB_D || operation == Operations.SUB_S) {
	        return OperationCategory.A; // Add or Subtract operations
	    } else if (operation == Operations.MUL_D || operation == Operations.MUL_S ||
	               operation == Operations.DIV_D || operation == Operations.DIV_S) {
	        return OperationCategory.M; // Multiply or Divide operations
	    } else if (operation == Operations.BNE || operation == Operations.BEQ) {
	        return OperationCategory.B; // Branching operations
	    } else {
	        throw new IllegalArgumentException("Unknown operation: " + operation);
	    }
	}
}