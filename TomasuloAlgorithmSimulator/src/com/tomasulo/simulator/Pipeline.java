package com.tomasulo.simulator;

import java.util.LinkedHashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class Pipeline {

    private Queue<Object> instructions = new LinkedList<>();
    private RegisterFile floatingPointRegisterFile;
    private RegisterFile registerFile;
    private LinkedHashMap<String, LoadBuffer> loadBuffers;
    private LinkedHashMap<String, StoreBuffer> storeBuffers;
    private LinkedHashMap<String, ReservationStation> adderReservationStations;
    private LinkedHashMap<String, ReservationStation> multiplierReservationStations;

    // Helper method to streamline buffers' initialization for the constructor and add them to a LinkedHashMap, representing an entire buffer.
    private <T extends Buffer> LinkedHashMap<String, T> initializeBuffers(int count, Class<T> clazz, String prefix) {
        LinkedHashMap<String, T> bufferMap = new LinkedHashMap<>();
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

    // Helper method to streamline reservation stations' initialization for the constructor and add them to a LinkedHashMap, representing an entire reservation station.
    private LinkedHashMap<String, ReservationStation> initializeReservationStations(int count, String prefix) {
        LinkedHashMap<String, ReservationStation> stationMap = new LinkedHashMap<>();
        for (int i = 1; i <= count; i++) {
            String name = prefix + i;
            stationMap.put(name, new ReservationStation(name));
        }
        return stationMap;
    }

    public Pipeline(ArrayList<Object> instructions, String[] entryNames, String[] qIs, String[] values, String[] fentryNames, String[] fqIs, String[] fvalues, int loadBufferSize, int storeBufferSize, int adderReservationStationSize, int multiplierReservationStationSize) {
        this.instructions.addAll(instructions);
        this.registerFile = new RegisterFile(entryNames, qIs, values);
        this.floatingPointRegisterFile = new RegisterFile(fentryNames, fqIs, fvalues);
        this.loadBuffers = initializeBuffers(loadBufferSize, LoadBuffer.class, "L");
        this.storeBuffers = initializeBuffers(storeBufferSize, StoreBuffer.class, "S");
        this.adderReservationStations = initializeReservationStations(adderReservationStationSize, "A");
        this.multiplierReservationStations = initializeReservationStations(multiplierReservationStationSize, "M");
    }
//_________________________________________Object Accessor Methods_____________________________________________ 
    public Queue<Object> getInstructions() {
        return instructions;
    }
    
    public RegisterFile getRegisterFile() {
        return registerFile;
    }

    public RegisterFile getFloatingPointRegisterFile() {
        return floatingPointRegisterFile;
    }

    public LinkedHashMap<String, LoadBuffer> getLoadBuffers() {
		return loadBuffers;
	}

	public LinkedHashMap<String, StoreBuffer> getStoreBuffers() {
		return storeBuffers;
	}

	public LinkedHashMap<String, ReservationStation> getAdderReservationStations() {
		return adderReservationStations;
	}

	public LinkedHashMap<String, ReservationStation> getMultiplierReservationStations() {
		return multiplierReservationStations;
	}
//_____________________________________________________________________________________________________________

	// Load Buffer Accessor Methods
    public LoadBuffer getLoadBufferEntry(String name) {
        return this.loadBuffers.get(name);
    }

    public void setLoadBufferEntry(String name, LoadBuffer loadBuffer) {
        this.loadBuffers.put(name, loadBuffer);
    }

    // Store Buffer Accessor Methods
    public StoreBuffer getStoreBufferEntry(String name) {
        return this.storeBuffers.get(name);
    }

    public void setStoreBufferEntry(String name, StoreBuffer storeBuffer) {
        this.storeBuffers.put(name, storeBuffer);
    }
//_____________________________________________________________________________________________________________
    // Adder Reservation Station Accessor Methods
    public ReservationStation getAdderReservationStationEntry(String name) {
        return this.adderReservationStations.get(name);
    }

    public void setAdderReservationStationEntry(String name, ReservationStation adderReservationStation) {
        this.adderReservationStations.put(name, adderReservationStation);
    }

    // Multiplier Reservation Station Accessor Methods
    public ReservationStation getMultiplierReservationStationEntry(String name) {
        return this.multiplierReservationStations.get(name);
    }

    public void setMultiplierReservationStationEntry(String name, ReservationStation multiplierReservationStation) {
        this.multiplierReservationStations.put(name, multiplierReservationStation);
    }
//__________________________Reservation Stations & Buffers' isBusy() and Search________________________________
  
    // Check for free Load Buffer
    public boolean isThereAFreeLoadBuffer() {
        return loadBuffers.values().stream().anyMatch(buffer -> !buffer.isBusy());
    }

    // Check for free Store Buffer
    public boolean isThereAFreeStoreBuffer() {
        return storeBuffers.values().stream().anyMatch(buffer -> !buffer.isBusy());
    }

    // Check for free Adder
    public boolean isThereAFreeAdder() {
        return adderReservationStations.values().stream().anyMatch(station -> !station.isBusy());
    }

    // Check for free Multiplier
    public boolean isThereAFreeMultiplier() {
        return multiplierReservationStations.values().stream().anyMatch(station -> !station.isBusy());
    }

    // Get first free Load Buffer name
    public String getFreeLoadBufferName() {
        return loadBuffers.keySet().stream().filter(name -> !loadBuffers.get(name).isBusy()).findFirst().orElse(null);
    }

    // Get first free Store Buffer name
    public String getFreeStoreBufferName() {
        return storeBuffers.keySet().stream().filter(name -> !storeBuffers.get(name).isBusy()).findFirst().orElse(null);
    }

    // Get first free Adder name
    public String getFreeAdderName() {
        return adderReservationStations.keySet().stream().filter(name -> !adderReservationStations.get(name).isBusy()).findFirst().orElse(null);
    }

    // Get first free Multiplier name
    public String getFreeMultiplierName() {
        return multiplierReservationStations.keySet().stream().filter(name -> !multiplierReservationStations.get(name).isBusy()).findFirst().orElse(null);
    }
//_____________________________________________________________________________________________________________
}
