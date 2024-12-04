//
//package com.tomasulo.test;
//
//import com.tomasulo.simulator.Pipeline;
//import com.tomasulo.utils.InstructionParser;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class PipelineTest {
//
//    public static void main(String[] args) {
//        try {
//            ArrayList<Object> instructions = InstructionParser.parseInstructions("C:\\Users\\Youse\\Desktop\\File.txt");
//
//            String[] entryNames = {"R1", "R2", "R3", "R9"};
//            String[] qIs = {"", "", "", ""};
//            String[] values = {"0", "0", "0", "0"};
//            String[] fentryNames = {"F1", "F2", "F3", "F4", "F5", "F6"};
//            String[] fqIs = {"", "", "", "", "", ""};
//            String[] fvalues = {"0.0", "0.0", "0.0", "0.0", "0.0", "0.0"};
//
//            Pipeline pipeline = new Pipeline(instructions, entryNames, qIs, values, fentryNames, fqIs, fvalues, 2, 2, 2, 2);
//
//            System.out.println("Pipeline initialized with instructions:");
//            for (Object instruction : pipeline.getInstructions()) {
//                System.out.println(instruction);
//            }
//            System.out.println(pipeline.getRegisterFileEntry("R9"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}