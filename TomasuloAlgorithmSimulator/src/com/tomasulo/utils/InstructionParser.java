package com.tomasulo.utils;

import com.tomasulo.predefintions.Operations;
import com.tomasulo.simulator.LoopInstruction;
import com.tomasulo.simulator.Instruction;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

public class InstructionParser {
	
	// Helper method to get the indentation level of a line
	private static int getIndentationLevel(String line) {
	    int index = 0;
	    while (index < line.length() && (line.charAt(index) == ' ' || line.charAt(index) == '\t')) {
	        index++;
	    }
	    return index;
	}
	
	public static ArrayList<Object> parseInstructions(String filePath) throws IOException {
	    ArrayList<Object> instructionList = new ArrayList<>();
	    BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    String line;
	    boolean inLoop = false;
	    ArrayList<Instruction> loopInstructions = new ArrayList<>();
	    String loopLabel = null;
	    int loopIndentationLevel = -1;

	    while ((line = reader.readLine()) != null) {
	        String originalLine = line;
	        line = line.trim();

	        if (line.isEmpty()) {
	            continue;
	        }

	        if (line.matches("^[A-Za-z_][A-Za-z0-9_]*:?;?$")) {
	            String label = line.replace(":", "").replace(";", "").trim();

	            if (inLoop) {
	                instructionList.add(new LoopInstruction(loopLabel, loopInstructions));
	                loopInstructions = new ArrayList<>();
	            }

	            loopLabel = label;
	            inLoop = true;
	            loopIndentationLevel = originalLine.indexOf(label);
	            continue;
	        }

	        int currentIndentationLevel = getIndentationLevel(originalLine);

	        if (inLoop && currentIndentationLevel > loopIndentationLevel) {
	            loopInstructions.add(parseInstruction(line));
	            continue;
	        } else if (inLoop) {
	            instructionList.add(new LoopInstruction(loopLabel, loopInstructions));
	            loopInstructions = new ArrayList<>();
	            loopLabel = null;
	            inLoop = false;
	        }
	        
	        instructionList.add(parseInstruction(line));
	    }

	    if (inLoop) {
	        instructionList.add(new LoopInstruction(loopLabel, loopInstructions));
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
	    String destination = parts.length > 1 ? parts[1].replace(",", "").toUpperCase().trim() : null;
	    String source1 = parts.length > 2 ? parts[2].replace(",", "").toUpperCase().trim() : null;
	    String source2 = parts.length > 3 ? parts[3].replace(",", "").toUpperCase().trim() : null;
	
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
	        if (!operand.toUpperCase().matches("^(F\\d+|R\\d+|\\d+|\\d+\\(R\\d+\\)|[A-Za-z_][A-Za-z0-9_]*)$")) {
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

	        String destination = parts[1].replace(",", "").toUpperCase();

	        if (destination.matches("^F\\d+$")) {
	            fRegisters.add(destination);
	        } else if (destination.matches("^R\\d+$")) {
	            rRegisters.add(destination);
	        }
	    }

	    reader.close();

	    ArrayList<String> combined = new ArrayList<>();
	    combined.addAll(rRegisters);
	    combined.addAll(fRegisters);

	    return combined.toArray(new String[0]);
	}

	public static String[] extractNonDestinations(String filePath, String[] destinations) throws IOException {
	    Set<String> destinationSet = new HashSet<>(Arrays.asList(destinations));

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
	        if (parts.length < 3) {
	            continue;
	        }

	        String source1 = parts[2].replace(",", "").toUpperCase();
	        String source2 = parts.length > 3 ? parts[3].replace(",", "").toUpperCase() : null;

	        Consumer<String> processSource = (src) -> {
	            if (src == null) return;
	            
	            if (src.matches("^R\\d+$") || src.matches("^F\\d+$")) {
	                if (!destinationSet.contains(src)) {
	                    if (src.startsWith("R")) {
	                        rRegisters.add(src);
	                    } else {
	                        fRegisters.add(src);
	                    }
	                }
	            } else if (src.matches("^\\d+\\(R\\d+\\)$")) {
	                String regInside = src.substring(src.indexOf('(') + 1, src.indexOf(')'));
	                regInside = regInside.toUpperCase();
	                if (!destinationSet.contains(regInside)) {
	                    rRegisters.add(regInside);
	                }
	            } 
	        };

	        processSource.accept(source1);
	        processSource.accept(source2);
	    }

	    reader.close();

	    fRegisters.removeAll(destinationSet);
	    rRegisters.removeAll(destinationSet);

	    ArrayList<String> combined = new ArrayList<>();
	    combined.addAll(rRegisters);
	    combined.addAll(fRegisters);

	    return combined.toArray(new String[0]);
	}
    
    public static Map<Operations, Integer> extractOperations(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        Map<Operations, Integer> operations = new HashMap<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.trim().split("\\s+");
            if (parts.length < 1) {
                continue;
            }

            try {
                String operationName = parts[0].replace(".", "_").toUpperCase();
                Operations operation = Operations.valueOf(operationName);

                operations.put(operation, 1);
            } catch (IllegalArgumentException e) {
            	// Do nothing
            }
        }

        return operations;
    }   
}