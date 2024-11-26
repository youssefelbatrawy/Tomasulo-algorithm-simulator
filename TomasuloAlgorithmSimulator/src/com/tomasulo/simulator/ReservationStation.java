package com.tomasulo.simulator;

public class ReservationStation {
	String name;
	boolean isBusy;
	String op;
	String v_j;
	String v_k;
	String q_j;
	String q_k;
	
	public ReservationStation(String name) {
		this.name = name;
		this.isBusy = false;
	}
}
