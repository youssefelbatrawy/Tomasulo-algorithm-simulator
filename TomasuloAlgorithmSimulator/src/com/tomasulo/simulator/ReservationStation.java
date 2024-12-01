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
	
	public void setReservationStation(boolean isBusy, String op, String v_j, String v_k, String q_j, String q_k) {
		this.isBusy = isBusy;
		this.op = op;
		this.v_j = v_j;
		this.v_k = v_k;
		this.q_j = q_j;
		this.q_k = q_k;
	}
}
