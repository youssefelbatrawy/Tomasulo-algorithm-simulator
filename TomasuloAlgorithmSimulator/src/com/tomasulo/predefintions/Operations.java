package com.tomasulo.predefintions;

public enum Operations {
	    
	    // Arithmetic Operations (Integer)
	    DADDI,  // Integer Add Immediate
	    DSUBI,  // Integer Subtract Immediate
//______________________________________________________________________________________________________________
	    // Arithmetic Operations (Floating Point)
	    ADD_D,  // Floating Point Addition (Double)
	    ADD_S,  // Floating Point Addition (Single)
	    
	    SUB_D,  // Floating Point Subtraction (Double)
	    SUB_S,  // Floating Point Subtraction (Single)
	    
	    MUL_D,  // Floating Point Multiplication (Double)
	    MUL_S,  // Floating Point Multiplication (Single)
	    
	    DIV_D,  // Floating Point Division (Double)
	    DIV_S,  // Floating Point Division (Single)
//______________________________________________________________________________________________________________
	    // Load Operations
	    LW,     // Load Word (Integer)
	    LD,     // Load Double (Floating Point)
	    L_S,    // Load Single (Floating Point)
	    L_D,    // Load Double (Floating Point)
	    
	    // Store Operations
	    SW,     // Store Word (Integer)
	    SD,     // Store Double (Floating Point)
	    S_S,    // Store Single (Floating Point)
	    S_D,    // Store Double (Floating Point)
//______________________________________________________________________________________________________________	    
	    // Branch Operations
	    BNE,    // Branch Not Equal
	    BEQ;    // Branch Equal
//______________________________________________________________________________________________________________
}