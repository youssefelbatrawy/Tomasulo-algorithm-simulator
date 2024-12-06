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

public class InstructionParser {

    public static ArrayList<Object> parseInstructions(String filePath) throws IOException {
        ArrayList<Object> instructionList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean inLoop = false;
        ArrayList<Instruction> loopInstructions = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String originalLine = line;
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.toLowerCase().startsWith("loop")) {
                if (inLoop) {
                    throw new IllegalArgumentException("Nested loops are not allowed!");
                }
                inLoop = true;
                continue;
            }

            if (inLoop && originalLine.startsWith("\t")) {
                loopInstructions.add(parseInstruction(line));
                continue;
            }

            if (inLoop && !originalLine.startsWith("\t")) {
                inLoop = false;
                instructionList.add(new LoopInstruction(loopInstructions));
                loopInstructions = new ArrayList<>();
            }

            instructionList.add(parseInstruction(line));
        }

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

        ArrayList<String> combined = new ArrayList<>(fRegisters);
        combined.addAll(rRegisters);

        return combined.toArray(new String[0]);
    }
    
    public static String[] extractNonDestinations(String filePath, String[] destinations) throws IOException {
        TreeSet<String> fRegisters = new TreeSet<>(Comparator.comparingInt(reg -> Integer.parseInt(reg.substring(1))));
        TreeSet<String> rRegisters = new TreeSet<>(Comparator.comparingInt(reg -> Integer.parseInt(reg.substring(1))));
        TreeSet<String> yRegisters = new TreeSet<>(Comparator.comparingInt(reg -> Integer.parseInt(reg.substring(2, reg.length() - 1))));

        Set<String> destinationSet = new HashSet<>(Arrays.asList(destinations));

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

            String source1 = parts[2];
            String source2 = parts.length > 3 ? parts[3] : null;

            if (source1 != null && !destinationSet.contains(source1.toUpperCase())) {
                if (source1.matches("^F\\d+$")) {
                    fRegisters.add(source1.toUpperCase());
                } else if (source1.matches("^R\\d+$")) {
                    rRegisters.add(source1.toUpperCase());
                } else if (source1.matches("^Y\\(R\\d+\\)$")) {
                    yRegisters.add(source1.toUpperCase());
                }
            }

            if (source2 != null && !destinationSet.contains(source2.toUpperCase())) {
                if (source2.matches("^F\\d+$")) {
                    fRegisters.add(source2.toUpperCase());
                } else if (source2.matches("^R\\d+$")) {
                    rRegisters.add(source2.toUpperCase());
                } else if (source2.matches("^Y\\(R\\d+\\)$")) {
                    yRegisters.add(source2.toUpperCase());
                }
            }
        }

        reader.close();

        ArrayList<String> combined = new ArrayList<>(fRegisters);
        combined.addAll(rRegisters);
        combined.addAll(yRegisters);

        return combined.toArray(new String[0]);
    }
    
    public static Map<Operations, Integer> extractOperations(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        Map<Operations, Integer> operations = new HashMap<>();
        for (Operations op : Operations.values()) {
            operations.put(op, 1);
        }
        for (String line : lines) {
            String[] parts = line.split(" ");
            Operations operation = Operations.valueOf(parts[0].replace(".", "_").toUpperCase());
            int latency = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
            operations.put(operation, latency);
        }
        return operations;
    }
}
