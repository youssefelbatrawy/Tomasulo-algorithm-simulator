
package com.tomasulo.test;

import com.tomasulo.predefintions.Operations;
import com.tomasulo.utils.InstructionParser;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class InstructionParserTest {

    @Test
    public void testExtractOperations() throws IOException {
        // Specify your own file path
        String filePath = "C:\\Users\\Youse\\Desktop\\File.txt";

        // Call the method to test
        Map<Operations, Integer> operations = InstructionParser.extractOperations(filePath);

        // Print the results
        for (Map.Entry<Operations, Integer> entry : operations.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}