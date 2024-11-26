package com.tomasulo.simulator;

import java.util.HashMap;
import java.util.stream.IntStream;

public class RegisterFile {
	
	HashMap<String, String> registerFile;
	
	public RegisterFile(String[] fps, String[] values) {
        this.registerFile = IntStream.range(0, fps.length).collect(HashMap::new, (map, i) -> map.put(fps[i], values[i]), HashMap::putAll);
	}
	
    public HashMap<String, String> getRegisterFile() {
        return this.registerFile;
    }
    
    public String getFpValue(String target) {
        if (!registerFile.containsKey(target)) {
            throw new IllegalArgumentException("Target key '" + target + "' does not exist in the register.");
        }

        return this.registerFile.get(target);
    }
    
    public void setFpValue(String target, String newValue) {
        if (!registerFile.containsKey(target)) {
            throw new IllegalArgumentException("Target key '" + target + "' does not exist in the register.");
        }

        registerFile.put(target, newValue);
    }
	
    // Kont ba test hena
//	public static void main(String[] args) {
//		String[] a = {"f0", "f1", "f2", "f3"};
//		String[] b = {"1", "2", "LD", "M1"};
//		
//		RegisterFile d = new RegisterFile(a, b);
//		
//		System.out.println(d.getRegisterFile());
//		System.out.println(d.getFpValue("f0"));
//		d.setFpValue("f0", "2");
//		System.out.println(d.getFpValue("f0"));
//		d.setFpValue("f10", "2");
//		
//	}
}
