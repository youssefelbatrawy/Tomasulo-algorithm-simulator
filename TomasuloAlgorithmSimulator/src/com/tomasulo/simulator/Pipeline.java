package com.tomasulo.simulator;

import java.util.LinkedHashMap;
import java.util.Queue;

import com.tomasulo.utils.FixedSizeLinkedHashMap;

import java.util.LinkedList;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Pipeline {

    private Queue<Object> instructions = new LinkedList<>();
    private RegisterFile floatingPointRegisterFile;
    private RegisterFile registerFile;
    private FixedSizeLinkedHashMap<String, LoadBuffer> loadBuffers;
    private FixedSizeLinkedHashMap<String, StoreBuffer> storeBuffers;
    private FixedSizeLinkedHashMap<String, ReservationStation> adderReservationStations;
    private FixedSizeLinkedHashMap<String, ReservationStation> multiplierReservationStations;
    private HashMap<String, LoopInstruction> loopInstructions = new HashMap<>();

    private <T extends Buffer> FixedSizeLinkedHashMap<String, T> initializeBuffers(int count, Class<T> clazz, String prefix) {
        FixedSizeLinkedHashMap<String, T> bufferMap = new FixedSizeLinkedHashMap<>(count);
        for (int i = 1; i <= count; i++) {
            try {
                String name = prefix + i;
                T buffer = clazz.getConstructor(String.class).newInstance(name);
                bufferMap.put(name, buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bufferMap;
    }

    private FixedSizeLinkedHashMap<String, ReservationStation> initializeReservationStations(int count, String prefix) {
        FixedSizeLinkedHashMap<String, ReservationStation> stationMap = new FixedSizeLinkedHashMap<>(count);
        for (int i = 1; i <= count; i++) {
            String name = prefix + i;
            stationMap.put(name, new ReservationStation(name));
        }
        return stationMap;
    }

    private void parseLoopInstructions(ArrayList<Object> instructions) {
        for (Object instruction : instructions) {
            if (instruction instanceof LoopInstruction) {
                LoopInstruction loopInstruction = (LoopInstruction) instruction;
                loopInstructions.put(loopInstruction.getName(), loopInstruction);
            }
        }
    }

    public Pipeline(ArrayList<Object> instructions, String[] entryNames, String[] qIs, String[] values, String[] fentryNames, String[] fqIs, String[] fvalues, int loadBufferSize, int storeBufferSize, int adderReservationStationSize, int multiplierReservationStationSize) {
        this.instructions.addAll(instructions);
        this.registerFile = new RegisterFile(entryNames, qIs, values);
        this.floatingPointRegisterFile = new RegisterFile(fentryNames, fqIs, fvalues);
        this.loadBuffers = initializeBuffers(loadBufferSize, LoadBuffer.class, "L");
        this.storeBuffers = initializeBuffers(storeBufferSize, StoreBuffer.class, "S");
        this.adderReservationStations = initializeReservationStations(adderReservationStationSize, "A");
        this.multiplierReservationStations = initializeReservationStations(multiplierReservationStationSize, "M");
        parseLoopInstructions(instructions);
    }
//______________________________________________Object Accessor Methods_________________________________________________ 
    public Queue<Object> getInstructions() {
        return instructions;
    }
    
    public RegisterFile getRegisterFile() {
        return registerFile;
    }

    public RegisterFile getFloatingPointRegisterFile() {
        return floatingPointRegisterFile;
    }

    public FixedSizeLinkedHashMap<String, LoadBuffer> getLoadBuffers() {
        return loadBuffers;
    }

    public FixedSizeLinkedHashMap<String, StoreBuffer> getStoreBuffers() {
        return storeBuffers;
    }

    public FixedSizeLinkedHashMap<String, ReservationStation> getAdderReservationStations() {
        return adderReservationStations;
    }

    public FixedSizeLinkedHashMap<String, ReservationStation> getMultiplierReservationStations() {
        return multiplierReservationStations;
    }
//________________________________________Instruction Accessor Methods__________________________________________________
	public Object dequeueInstruction() {
		return this.instructions.poll();
	}

	public Object peekInstruction() {
		return this.instructions.peek();
	}
	
	public boolean hasInstructions() {
		return !this.instructions.isEmpty();
	}
	
    public LoopInstruction getLoopInstruction(String name) {
        return loopInstructions.get(name);
    }
//_______________________________Buffer & Reservation Stations Accessor Methods__________________________________________
	// Load Buffer Accessor Methods
    public LoadBuffer getLoadBufferEntry(String name) {
        return this.loadBuffers.get(name);
    }

    public void setLoadBufferEntry(String name, boolean isBusy, String address) {
        LoadBuffer buffer = this.loadBuffers.get(name);
        if (buffer != null) {
            buffer.setBusy(isBusy);
            buffer.setAddress(address);
        } else {
            throw new IllegalArgumentException("LoadBuffer with name " + name + " does not exist.");
        }
    }

    // Store Buffer Accessor Methods
    public StoreBuffer getStoreBufferEntry(String name) {
        return this.storeBuffers.get(name);
    }

    public void setStoreBufferEntry(String name, boolean isBusy, String address, String v, String q) {
        StoreBuffer buffer = this.storeBuffers.get(name);
        if (buffer != null) {
            buffer.setBusy(isBusy);
            buffer.setAddress(address);
            buffer.setV(v);
            buffer.setQ(q);
        } else {
            throw new IllegalArgumentException("StoreBuffer with name " + name + " does not exist.");
        }
    }

    // Adder Reservation Station Accessor Methods
    public ReservationStation getAdderReservationStationEntry(String name) {
        return this.adderReservationStations.get(name);
    }

    public void setAdderReservationStationEntry(String name, boolean isBusy, String op, String v_j, String v_k, String q_j, String q_k) {
        ReservationStation station = this.adderReservationStations.get(name);
        if (station != null) {
            station.setReservationStation(isBusy, op, v_j, v_k, q_j, q_k);
        } else {
            throw new IllegalArgumentException("Adder Reservation Station with name " + name + " does not exist.");
        }
    }

    // Multiplier Reservation Station Accessor Methods
    public ReservationStation getMultiplierReservationStationEntry(String name) {
        return this.multiplierReservationStations.get(name);
    }

    public void setMultiplierReservationStationEntry(String name, boolean isBusy, String op, String v_j, String v_k, String q_j, String q_k) {
        ReservationStation station = this.multiplierReservationStations.get(name);
        if (station != null) {
            station.setReservationStation(isBusy, op, v_j, v_k, q_j, q_k);
        } else {
            throw new IllegalArgumentException("Multiplier Reservation Station with name " + name + " does not exist.");
        }
    }
//_______________________________Buffers & Reservation Stations Single Attribute Methods____________________________________
    // Helper Method for Error Messages
    private <T> T validateAndRetrieve(Map<String, T> map, String name, String type) {
        T element = map.get(name);
        if (element == null) {
            throw new IllegalArgumentException(type + " with name " + name + " does not exist.");
        }
        return element;
    }

    // Load & Store Buffers Methods
    public boolean isBufferBusy(String name) {
        if (name.startsWith("L")) {
            return validateAndRetrieve(loadBuffers, name, "Load Buffer").isBusy();
        } else if (name.startsWith("S")) {
            return validateAndRetrieve(storeBuffers, name, "Store Buffer").isBusy();
        } else {
            throw new IllegalArgumentException("Invalid buffer type for name: " + name);
        }
    }

    public void setBufferBusy(String name, boolean isBusy) {
        if (name.startsWith("L")) {
            validateAndRetrieve(loadBuffers, name, "Load Buffer").setBusy(isBusy);
        } else if (name.startsWith("S")) {
            validateAndRetrieve(storeBuffers, name, "Store Buffer").setBusy(isBusy);
        } else {
            throw new IllegalArgumentException("Invalid buffer type for name: " + name);
        }
    }

    public String getAddress(String name) {
        if (name.startsWith("L")) {
            return validateAndRetrieve(loadBuffers, name, "Load Buffer").getAddress();
        } else if (name.startsWith("S")) {
            return validateAndRetrieve(storeBuffers, name, "Store Buffer").getAddress();
        } else {
            throw new IllegalArgumentException("Invalid buffer type for name: " + name);
        }
    }

    public void setAddress(String name, String address) {
        if (name.startsWith("L")) {
            validateAndRetrieve(loadBuffers, name, "Load Buffer").setAddress(address);
        } else if (name.startsWith("S")) {
            validateAndRetrieve(storeBuffers, name, "Store Buffer").setAddress(address);
        } else {
            throw new IllegalArgumentException("Invalid buffer type for name: " + name);
        }
    }

    // Store Buffer Exclusive Methods
    public String getV(String name) {
        return validateAndRetrieve(storeBuffers, name, "Store Buffer").getV();
    }

    public void setV(String name, String v) {
        validateAndRetrieve(storeBuffers, name, "Store Buffer").setV(v);
    }

    public String getQ(String name) {
        return validateAndRetrieve(storeBuffers, name, "Store Buffer").getQ();
    }

    public void setQ(String name, String q) {
        validateAndRetrieve(storeBuffers, name, "Store Buffer").setQ(q);
    }

    // Reservation Station Methods
    public boolean isReservationStationBusy(String name) {
        if (name.startsWith("A")) {
            return validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").isBusy();
        } else if (name.startsWith("M")) {
            return validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").isBusy();
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public void setReservationStationBusy(String name, boolean isBusy) {
        if (name.startsWith("A")) {
            validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").setBusy(isBusy);
        } else if (name.startsWith("M")) {
            validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").setBusy(isBusy);
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public String getOp(String name) {
        if (name.startsWith("A")) {
            return validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").getOp();
        } else if (name.startsWith("M")) {
            return validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").getOp();
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public void setOp(String name, String op) {
        if (name.startsWith("A")) {
            validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").setOp(op);
        } else if (name.startsWith("M")) {
            validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").setOp(op);
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public String getV_j(String name) {
        if (name.startsWith("A")) {
            return validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").getV_j();
        } else if (name.startsWith("M")) {
            return validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").getV_j();
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public void setV_j(String name, String v_j) {
        if (name.startsWith("A")) {
            validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").setV_j(v_j);
        } else if (name.startsWith("M")) {
            validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").setV_j(v_j);
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public String getV_k(String name) {
        if (name.startsWith("A")) {
            return validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").getV_k();
        } else if (name.startsWith("M")) {
            return validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").getV_k();
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public void setV_k(String name, String v_k) {
        if (name.startsWith("A")) {
            validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").setV_k(v_k);
        } else if (name.startsWith("M")) {
            validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").setV_k(v_k);
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public String getQ_j(String name) {
        if (name.startsWith("A")) {
            return validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").getQ_j();
        } else if (name.startsWith("M")) {
            return validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").getQ_j();
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public void setQ_j(String name, String q_j) {
        if (name.startsWith("A")) {
            validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").setQ_j(q_j);
        } else if (name.startsWith("M")) {
            validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").setQ_j(q_j);
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public String getQ_k(String name) {
        if (name.startsWith("A")) {
            return validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").getQ_k();
        } else if (name.startsWith("M")) {
            return validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").getQ_k();
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }

    public void setQ_k(String name, String q_k) {
        if (name.startsWith("A")) {
            validateAndRetrieve(adderReservationStations, name, "Adder Reservation Station").setQ_k(q_k);
        } else if (name.startsWith("M")) {
            validateAndRetrieve(multiplierReservationStations, name, "Multiplier Reservation Station").setQ_k(q_k);
        } else {
            throw new IllegalArgumentException("Invalid reservation station type for name: " + name);
        }
    }
//______________________________Reservation Stations & Buffers' isBusy() and Search_____________________________________
  
    // Search and Free Methods
    public boolean isThereAFreeLoadBuffer() {
        return loadBuffers.values().stream().anyMatch(buffer -> buffer != null && !buffer.isBusy());
    }

    public boolean isThereAFreeStoreBuffer() {
        return storeBuffers.values().stream().anyMatch(buffer -> buffer != null && !buffer.isBusy());
    }

    public boolean isThereAFreeAdder() {
        return adderReservationStations.values().stream().anyMatch(station -> station != null && !station.isBusy());
    }

    public boolean isThereAFreeMultiplier() {
        return multiplierReservationStations.values().stream().anyMatch(station -> station != null && !station.isBusy());
    }
    
    // Get first free Load Buffer name
    public String getFreeLoadBufferName() {
        return loadBuffers.entrySet().stream().filter(entry -> entry.getValue() != null && !entry.getValue().isBusy()).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    // Get first free Store Buffer name
    public String getFreeStoreBufferName() {
        return storeBuffers.entrySet().stream().filter(entry -> entry.getValue() != null && !entry.getValue().isBusy()).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    // Get first free Adder name
    public String getFreeAdderName() {
        return adderReservationStations.entrySet().stream().filter(entry -> entry.getValue() != null && !entry.getValue().isBusy()).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    // Get first free Multiplier name
    public String getFreeMultiplierName() {
        return multiplierReservationStations.entrySet().stream().filter(entry -> entry.getValue() != null && !entry.getValue().isBusy()).map(Map.Entry::getKey).findFirst().orElse(null);
    }
//______________________________________________________________________________________________________________________
}