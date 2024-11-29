package com.tomasulo.simulator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class RegisterFile {

    private HashMap<String, String[]> registerFile;

    public RegisterFile(String[] fps, String[] qI, String[] values) {
        this.registerFile = new HashMap<>();

        IntStream.range(0, fps.length).forEach(i -> {String qIValue = (qI != null && i < qI.length) ? qI[i] : null; String regValue = (values != null && i < values.length) ? values[i] : null; registerFile.put(fps[i], new String[]{qIValue, regValue});});
    }

    public Map<String, String[]> getRegisterFile() {
        return this.registerFile;
    }

    public String[] getFpValue(String target) {
        if (!registerFile.containsKey(target)) {
            throw new IllegalArgumentException("Target key '" + target + "' does not exist in the register.");
        }
        return this.registerFile.get(target);
    }

    public void setFpValue(String target, String qI, String newValue) {
        if (!registerFile.containsKey(target)) {
            throw new IllegalArgumentException("Target key '" + target + "' does not exist in the register.");
        }

        String[] existingEntry = registerFile.get(target);

        String updatedQI = (qI != null) ? qI : existingEntry[0];
        String updatedValue = (newValue != null) ? newValue : existingEntry[1];

        registerFile.put(target, new String[]{updatedQI, updatedValue});
    }

//    public static void main(String[] args) {
//        String[] fps =    {"f0", "f1", "f2", "f3"};
//        String[] qI =     {null, null, null, null}; 
//        String[] values = {null, null, null, "9"};
//
//        RegisterFile registerFile = new RegisterFile(fps, qI, values);
//
//        System.out.println("Before Update:");
//        registerFile.getRegisterFile().forEach((key, value) ->
//                System.out.println(key + " -> Q_i: " + value[0] + ", Value: " + value[1]));
//
//        registerFile.setFpValue("f1", "M1", null);
//
//        registerFile.setFpValue("f2", null, "35.0");
//
//        registerFile.setFpValue("f3", "M2", "50.0");
//
//        System.out.println("\nAfter Update:");
//        registerFile.getRegisterFile().forEach((key, value) ->
//                System.out.println(key + " -> Q_i: " + value[0] + ", Value: " + value[1]));
//    }
}