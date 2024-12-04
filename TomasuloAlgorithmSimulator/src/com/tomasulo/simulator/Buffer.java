package com.tomasulo.simulator;

public abstract class Buffer {
	
	private String name;
	private boolean isBusy;
	private String address;
	
	public Buffer(String name) {
		this.name = name;
		isBusy = false;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
