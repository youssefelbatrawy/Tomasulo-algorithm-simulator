package com.tomasulo.test;

import com.tomasulo.simulator.RegisterFile;

public class RegisterFileTest {

    // Testing RegisterFile
    public static void main(String[] args) {
        // Initialize register file entries
        String[] entryNames = {"R1", "R2", "R3"};
        String[] qIs = {"Q1", "Q2", "Q3"};
        String[] values = {"10", "20", "30"};

        // Create RegisterFile instance
        RegisterFile registerFile = new RegisterFile(entryNames, qIs, values);

        // Test getRegisterFile
        System.out.println("Initial Register File: " + registerFile);

        // Test getQiValue
        System.out.println("Qi value of R1: " + registerFile.getQiValue("R1"));

        // Test setQiValue
        registerFile.setQiValue("R1", "Q4");
        System.out.println("Updated Qi value of R1: " + registerFile.getQiValue("R1"));

        // Test setRegisterFileEntry
        registerFile.setRegisterFileEntry("R2", "Q5", "25");
        System.out.println("Updated Register File: " + registerFile);

        // Test getRegisterFileEntry
        System.out.println("Register File Entry for R3: " + java.util.Arrays.toString(registerFile.getRegisterFileEntry("R3")));

        // Test addRegFileEntry
        registerFile.addRegFileEntry("R4", "Q6", "40");
        System.out.println("Register File after adding R4: " + registerFile);
    }
}
