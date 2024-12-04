package com.tomasulo.simulator;

public class StoreBuffer extends Buffer {
	
	String v;
	String q;

	public StoreBuffer(String name) {
		super(name);
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
}
