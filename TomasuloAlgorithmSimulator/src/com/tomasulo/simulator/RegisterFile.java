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

}