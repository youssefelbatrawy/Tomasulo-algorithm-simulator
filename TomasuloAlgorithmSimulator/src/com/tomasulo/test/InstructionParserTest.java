
package com.tomasulo.test;

import com.tomasulo.predefintions.Operations;
import com.tomasulo.utils.InstructionParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class InstructionParserTest {

    @Test
    public void testExtractOperations() throws IOException {
        String filePath = "C:\\Users\\Youse\\Desktop\\File.txt";
        Map<Operations, Integer> operations = InstructionParser.extractOperations(filePath);
        for (Map.Entry<Operations, Integer> entry : operations.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Test
    public void testParseInstructions() throws IOException {
        String filePath = "C:\\Users\\Youse\\Desktop\\File.txt";
        ArrayList<Object> instructions = InstructionParser.parseInstructions(filePath);
        for (Object instruction : instructions) {
            System.out.println(instruction);
        }
    }

    @Test
    public void testExtractDestinations() throws IOException {
        String filePath = "C:\\Users\\Youse\\Desktop\\File.txt";
        String[] destinations = InstructionParser.extractDestinations(filePath);
        for (String destination : destinations) {
            System.out.println(destination);
        }
    }

    @Test
    public void testExtractNonDestinations() throws IOException {
        String filePath = "C:\\Users\\Youse\\Desktop\\File.txt";
        String[] destinations = InstructionParser.extractDestinations(filePath);
        String[] nonDestinations = InstructionParser.extractNonDestinations(filePath, destinations);
        for (String nonDestination : nonDestinations) {
            System.out.println(nonDestination);
        }
    }

    public static void main(String[] args) throws IOException {
        InstructionParserTest test = new InstructionParserTest();
        test.testExtractOperations();
        test.testParseInstructions();
        System.out.println("Destinations:");
        test.testExtractDestinations();
        System.out.println("Non-Destinations:");
        test.testExtractNonDestinations();
    }
}