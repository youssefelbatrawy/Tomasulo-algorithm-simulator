//package com.tomasulo.test;
//
//import com.tomasulo.simulator.LoadBuffer;
//import com.tomasulo.simulator.RegisterFile;
//import com.tomasulo.simulator.ReservationStation;
//import com.tomasulo.simulator.StoreBuffer;
//
//public class LoadBufferTest {
//	public static void main(String[] args) {
//		int loadinput = 3;
//		int addInput = 3;
//		int storeInput = 3;
//		int mulInput = 3;
//		LoadBuffer[] loadBuffers = new LoadBuffer[loadinput];
//		StoreBuffer[] storeBuffers = new StoreBuffer[storeInput];
//		RegisterFile registerFile = new RegisterFile(args, args, args);
//		ReservationStation[] adderReservationStations = new ReservationStation[addInput];
//		ReservationStation[] multiplierReservationStations = new ReservationStation[mulInput];
//		
//		for(int i = 0; i < loadinput; i++) {
//			loadBuffers[i] = new LoadBuffer("L" + i);
//		}
//		
//		for(int i = 0; i < storeInput; i++) {
//			storeBuffers[i] = new StoreBuffer("S" + i);
//		}
//		
//		for(int i = 0; i < addInput; i++) {
//			adderReservationStations[i] = new ReservationStation("A" + i);
//		}
//		
//		for(int i = 0; i < mulInput; i++) {
//			multiplierReservationStations[i] = new ReservationStation("M" + i);
//		}
//	}
//}
