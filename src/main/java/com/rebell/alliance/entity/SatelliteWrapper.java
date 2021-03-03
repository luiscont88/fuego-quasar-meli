package com.rebell.alliance.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SatelliteWrapper {

	private List<Satellite> satellites;

	public void setSatellites(List<Satellite> satellites) {
		this.satellites = satellites;
	}

	public List<Satellite> getSatellites() {
		return satellites;
	}

	public double[] getDistances() {

		double[] distances;
		if (satellites.size() == 1 || satellites.size() == 2) {
			distances = new double[3];
		} else {
			distances = new double[satellites.size()];
		}
		for (int i = 0; i < satellites.size(); i++) {
			distances[i] = satellites.get(i).getDistance();
		}
		return distances;
	}

	public double[][] getLocations() {
		double[][] locations;
		if (satellites.size() == 1 || satellites.size() == 2) {
			locations = new double[3][];
		} else {
			locations = new double[satellites.size()][];
		}
		String[] points;
		for (int i = 0; i < satellites.size(); i++) {
			if (satellites.get(i).getPosition() != null) {
				points = satellites.get(i).getPosition().toString().split(",");
				locations[i] = Arrays.stream(points).map(Double::valueOf).mapToDouble(Double::doubleValue).toArray();
			}
		}
		return locations;
	}

	public void setLocation(double[][] pointsList) {
		Location location;
		for (int i = 0; i < pointsList.length; i++) {
			location = new Location(pointsList[i]);
			satellites.get(i).setPosition(location);
		}
	}

	public List<List<String>> getMessages() {
		List<List<String>> messages = new ArrayList<List<String>>();
		for (Satellite s : satellites) {
			messages.add(s.getMessage());
		}
		return messages;
	}

}
