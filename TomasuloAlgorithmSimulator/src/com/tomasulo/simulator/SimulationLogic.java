package com.tomasulo.simulator;

//import com.tomasulo.utils.Issuer;

public class SimulationLogic {

    public static void start(Pipeline pipeline) {
        // Print instructions
        System.out.println("Instructions:");
        pipeline.getInstructions().forEach(System.out::println);

        // Print register file entries
        System.out.println("Register File Entries:");
        pipeline.getRegisterFile().getRegisterFile().forEach((key, value) -> 
            System.out.println(key + ": " + String.join(", ", value))
        );

        // Print floating point register file entries
        System.out.println("Floating Point Register File Entries:");
        pipeline.getFloatingPointRegisterFile().getRegisterFile().forEach((key, value) -> 
            System.out.println(key + ": " + String.join(", ", value))
        );

        // Print load buffers
        System.out.println("Load Buffers:");
        pipeline.getLoadBuffers().forEach((name, buffer) -> System.out.println(name + ": " + buffer));

        // Print store buffers
        System.out.println("Store Buffers:");
        pipeline.getStoreBuffers().forEach((name, buffer) -> System.out.println(name + ": " + buffer));

        // Print adder reservation stations
        System.out.println("Adder Reservation Stations:");
        pipeline.getAdderReservationStations().forEach((name, station) -> System.out.println(name + ": " + station));

        // Print multiplier reservation stations
        System.out.println("Multiplier Reservation Stations:");
        pipeline.getMultiplierReservationStations().forEach((name, station) -> System.out.println(name + ": " + station));
    }
}
