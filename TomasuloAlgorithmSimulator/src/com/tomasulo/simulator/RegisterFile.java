
package com.tomasulo.simulator;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class RegisterFile {

    private TreeMap<String, String[]> registerFile;

    public RegisterFile(String[] entryName, String[] qIs, String[] values) {
        this.registerFile = new TreeMap<>();
        IntStream.range(0, entryName.length).forEach(i -> {String qIValue = (qIs != null && i < qIs.length) ? qIs[i] : null; String regValue = (values != null && i < values.length) ? values[i] : null; registerFile.put(entryName[i].toUpperCase(), new String[]{qIValue, regValue});});
    }

    public Map<String, String[]> getRegisterFile() {
        return this.registerFile;
    }

    public String getQiValue(String target) {
        String targetUpper = target.toUpperCase();
        if (!registerFile.containsKey(targetUpper)) {
            throw new IllegalArgumentException("Entry with name '" + targetUpper + "' does not exist in the register.");
        }
        return registerFile.get(targetUpper)[0];
    }

    public void setQiValue(String target, String qI) {
        String targetUpper = target.toUpperCase();
        if (!registerFile.containsKey(targetUpper)) {
            throw new IllegalArgumentException("Entry with name '" + targetUpper + "' does not exist in the register.");
        }
        String[] existingEntry = registerFile.get(targetUpper);
        registerFile.put(targetUpper, new String[]{qI, existingEntry[1]});
    }
    
    public void setValue(String target, String value) {
        String targetUpper = target.toUpperCase();
        if (!registerFile.containsKey(targetUpper)) {
            throw new IllegalArgumentException("Entry with name '" + targetUpper + "' does not exist in the register.");
        }
        String[] existingEntry = registerFile.get(targetUpper);
        registerFile.put(targetUpper, new String[]{existingEntry[0], value});
    }

    public String getValue(String target) {
        String targetUpper = target.toUpperCase();
        if (!registerFile.containsKey(targetUpper)) {
            throw new IllegalArgumentException("Entry with name '" + targetUpper + "' does not exist in the register.");
        }
        return registerFile.get(targetUpper)[1];
    }
    
    public String[] getRegisterFileEntry(String target) {
        String targetUpper = target.toUpperCase();
        if (!registerFile.containsKey(targetUpper)) {
            throw new IllegalArgumentException("Target key '" + targetUpper + "' does not exist in the register.");
        }
        return this.registerFile.get(targetUpper);
    }

    public void setRegisterFileEntry(String target, String qI, String newValue) {
        String targetUpper = target.toUpperCase();
        if (!registerFile.containsKey(targetUpper)) {
            throw new IllegalArgumentException("Target key '" + targetUpper + "' does not exist in the register.");
        }

        String[] existingEntry = registerFile.get(targetUpper);

        String updatedQI = (qI != null) ? qI : existingEntry[0];
        String updatedValue = (newValue != null) ? newValue : existingEntry[1];

        registerFile.put(targetUpper, new String[]{updatedQI, updatedValue});
    }

    public void addRegFileEntry(String target, String qI, String value) {
        String targetUpper = target.toUpperCase();
        if (registerFile.containsKey(targetUpper)) {
            throw new IllegalArgumentException("Entry with name '" + targetUpper + "' already exists in the register.");
        }
        registerFile.put(targetUpper, new String[]{qI, value});
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        registerFile.forEach((key, value) -> {sb.append(key).append("=["); sb.append(String.join(", ", value)); sb.append("], ");});
        
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        
        sb.append("}");
        
        return sb.toString();
    }
}