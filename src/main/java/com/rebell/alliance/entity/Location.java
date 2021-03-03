package com.rebell.alliance.entity;

public class Location {

	private double x;

	private double y;

	public Location(double[] points) {
		this.x = points[0];
		this.y = points[1];
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

}
