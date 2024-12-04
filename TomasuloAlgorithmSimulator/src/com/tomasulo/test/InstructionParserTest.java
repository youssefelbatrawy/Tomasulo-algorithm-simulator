package com.tomasulo.test;


import com.tomasulo.utils.InstructionParser;
import com.tomasulo.predefintions.Operations;
import com.tomasulo.simulator.Instruction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructionParserTest {
	
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Youse\\Desktop\\File.txt";

        try {
            // Parse instructions from the file
            ArrayList<Object> instructions = InstructionParser.parseInstructions(filePath);

            // Create a map of latencies for different operations
            Map<Operations, Integer> latencies = new HashMap<>();
            latencies.put(Operations.DADDI, 1);
            latencies.put(Operations.ADD_D, 2);
            latencies.put(Operations.MUL_S, 3);
            latencies.put(Operations.SW, 1);

            // Set latencies for the parsed instructions
            InstructionParser.setLatencies(instructions, latencies);

            // Print the instructions to verify the latencies have been set correctly
            for (Object obj : instructions) {
                if (obj instanceof Instruction) {
                    Instruction inst = (Instruction) obj;
                    System.out.println(inst);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
