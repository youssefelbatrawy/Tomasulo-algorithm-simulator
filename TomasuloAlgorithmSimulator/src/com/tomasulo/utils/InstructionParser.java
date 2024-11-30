package com.tomasulo.utils;

import com.tomasulo.predefintions.Operations;
import com.tomasulo.simulator.LoopInstruction;
import com.tomasulo.simulator.Instruction;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class InstructionParser {

	public static ArrayList<Object> parseInstructions(String filePath) throws IOException {
	    ArrayList<Object> instructionList = new ArrayList<>();
	    @SuppressWarnings("resource") BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    String line;
	    boolean inLoop = false;
	    ArrayList<Instruction> loopInstructions = new ArrayList<>();

	    while ((line = reader.readLine()) != null) {
	        // Preserve the original line for detecting tab
	        String originalLine = line;

	        // Trim spaces for general parsing
	        line = line.trim();

	        // Skip empty lines
	        if (line.isEmpty()) {
	            continue;
	        }

	        // Check if the line is a loop start
	        if (line.toLowerCase().startsWith("loop")) {
	            if (inLoop) {
	                throw new IllegalArgumentException("Nested loops are not allowed!");
	            }
	            inLoop = true;
	            continue;
	        }

	        // Check for indented lines with tabs
	        if (inLoop && originalLine.startsWith("\t")) {
	            loopInstructions.add(parseInstruction(line));
	            continue;
	        }

	        // If indentation ends, we close the loop
	        if (inLoop && !originalLine.startsWith("\t")) {
	            inLoop = false;
	            instructionList.add(new LoopInstruction(loopInstructions));
	            loopInstructions = new ArrayList<>();
	        }

	        // Parse normal instruction
	        instructionList.add(parseInstruction(line));
	    }

	    // If loop ended unexpectedly
	    if (inLoop) {
	        instructionList.add(new LoopInstruction(loopInstructions));
	    }

	    reader.close();
	    return instructionList;
	}

    private static Instruction parseInstruction(String line) {
        String[] parts = line.split("\\s+");

        if (parts.length < 2 || parts.length > 4) {
            throw new IllegalArgumentException("Invalid MIPS instruction format: " + line);
        }

        Operations operation = parseOperation(parts[0]);
        String destination = parts.length > 1 ? parts[1] : null;
        String source1 = parts.length > 2 ? parts[2] : null;
        String source2 = parts.length > 3 ? parts[3] : null;

        validateOperands(destination, source1, source2);

        return new Instruction(operation, destination, source1, source2);
    }

    private static Operations parseOperation(String operationStr) {
        operationStr = operationStr.toUpperCase().replace(".", "_");
        try {
            return Operations.valueOf(operationStr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid operation: " + operationStr);
        }
    }

    private static void validateOperands(String... operands) {
        for (String operand : operands) {
            if (operand == null) continue;
            if (!operand.matches("^(F\\d+|R\\d+|\\d+|Y\\(R\\d+\\))$")) {
                throw new IllegalArgumentException("Invalid operand: " + operand);
            }
        }
    }
    
    public static String[] extractDestinations(String filePath) throws IOException {
        TreeSet<String> fRegisters = new TreeSet<>(Comparator.comparingInt(reg -> Integer.parseInt(reg.substring(1))));
        TreeSet<String> rRegisters = new TreeSet<>(Comparator.comparingInt(reg -> Integer.parseInt(reg.substring(1))));

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.isEmpty() || line.toLowerCase().startsWith("loop")) {
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length < 2) {
                continue;
            }

            String destination = parts[1];
            if (destination.matches("^F\\d+$")) {
                fRegisters.add(destination.toUpperCase());
            } else if (destination.matches("^R\\d+$")) {
                rRegisters.add(destination.toUpperCase());
            }
        }

        reader.close();

        // Combine the sorted sets into one array
        ArrayList<String> combined = new ArrayList<>(fRegisters);
        combined.addAll(rRegisters);

        return combined.toArray(new String[0]);
    }

    // Testing bas
//    public static void main(String[] args) {
//        try {
//        	String filePath = "C:\\Users\\Youse\\Desktop\\File.txt";
//            ArrayList<Object> instructions = parseInstructions(filePath);
//            
//            // Output the parsed instructions for verification
//            for (Object obj : instructions) {
//                if (obj instanceof LoopInstruction) {
//                    System.out.println("LoopInstruction: " + ((LoopInstruction) obj).instructions);
//                } else if (obj instanceof Instruction) {
//                    System.out.println("Instruction: " + obj);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    public static void main(String[] args) {
        try {
            String filePath = "C:\\Users\\Youse\\Desktop\\File.txt";

            // Parse instructions
            ArrayList<Object> instructions = parseInstructions(filePath);

            // Extract destinations
            String[] destinations = extractDestinations(filePath);

            // Output destinations for verification
            System.out.println("Destinations in order:");
            for (String dest : destinations) {
                System.out.println(dest);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
