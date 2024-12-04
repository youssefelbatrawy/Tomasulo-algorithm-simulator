package com.tomasulo.simulator;

public class ReservationStation {
	private String name;
	private boolean isBusy;
	private String op;
	private String v_j;
	private String v_k;
	private String q_j;
	private String q_k;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getV_j() {
		return v_j;
	}

	public void setV_j(String v_j) {
		this.v_j = v_j;
	}

	public String getV_k() {
		return v_k;
	}

	public void setV_k(String v_k) {
		this.v_k = v_k;
	}

	public String getQ_j() {
		return q_j;
	}

	public void setQ_j(String q_j) {
		this.q_j = q_j;
	}

	public String getQ_k() {
		return q_k;
	}

	public void setQ_k(String q_k) {
		this.q_k = q_k;
	}
}
