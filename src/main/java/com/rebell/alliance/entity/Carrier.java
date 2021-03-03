package com.rebell.alliance.entity;

public class Carrier extends CargoShip {

	private String message;

	public Carrier(Location position, String message) {
		this.setPosition(position);
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
