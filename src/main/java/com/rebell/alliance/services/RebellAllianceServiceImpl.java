package com.rebell.alliance.services;

import com.rebell.alliance.entity.Carrier;
import com.rebell.alliance.entity.Location;
import com.rebell.alliance.entity.Satellite;
import com.rebell.alliance.entity.SatelliteWrapper;
import com.rebell.alliance.entity.CargoShip;
import com.rebell.alliance.exceptions.LocationException;
import com.rebell.alliance.exceptions.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RebellAllianceServiceImpl implements RebellAllianceService {

	@Autowired
	LocationService locationService;

	@Autowired
	MessageService messageService;

	@Autowired
	private Environment environment;

	@SuppressWarnings("rawtypes")
	@Override
	public CargoShip getCargoShip(RequestEntity requestEntity) throws MessageException, LocationException {

		SatelliteWrapper satelliteWrapper = (SatelliteWrapper) requestEntity.getBody();
		if (satelliteWrapper.getMessages().size() < 2)
			throw new MessageException("Número de mensajes insuficientes");
		String message = messageService.getMessage(satelliteWrapper.getMessages());

		uploadLocations(satelliteWrapper);
		if ((satelliteWrapper.getLocations().length < 2) || (satelliteWrapper.getDistances().length < 2))
			throw new LocationException("Número posicion o distancias insuficientes");
		double[] points = locationService.getLocation(satelliteWrapper.getLocations(), satelliteWrapper.getDistances());
		Location position = new Location(points);

		return new Carrier(position, message);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CargoShip getSimpleCargoShip(RequestEntity requestEntity, String satelliteName)
			throws MessageException, LocationException {
		Satellite satellite = (Satellite) requestEntity.getBody();
		if (satelliteName != null) {
			satellite.setName(satelliteName);
		}
		List<Satellite> satellities = new ArrayList<Satellite>();
		satellities.add(satellite);
		SatelliteWrapper satelliteWrapper = new SatelliteWrapper();
		satelliteWrapper.setSatellites(satellities);
		String message = messageService.getMessage(satelliteWrapper.getMessages());
		uploadLocations(satelliteWrapper);
		double[] points = locationService.getLocation(satelliteWrapper.getLocations(), satelliteWrapper.getDistances());
		Location position = new Location(points);
		return new Carrier(position, message);
	}

	public void uploadLocations(SatelliteWrapper satelliteWrapper) {

		if (satelliteWrapper.getLocations()[0] == null) {
			
			int numberSat = satelliteWrapper.getSatellites().size();
			double[][] pointsList = new double[numberSat][];
			String[] satelliteLoc;
			List<Satellite> satellites = satelliteWrapper.getSatellites();
			int satellitesCount = 0;
			for (Satellite satellite : satellites) {
				satelliteLoc = getSatelliteLoc(satellite.getName());
				pointsList[satellitesCount] = Arrays.stream(satelliteLoc).map(Double::valueOf)
						.mapToDouble(Double::doubleValue).toArray();
				satellitesCount++;
			}
			satelliteWrapper.setLocation(pointsList);
		}
	}

	public void uploadSatelliteLocation(Satellite satellite) {

		double[][] pointsList = new double[1][];
		String[] satelliteLoc = getSatelliteLoc(satellite.getName());
		pointsList[0] = Arrays.stream(satelliteLoc).map(Double::valueOf).mapToDouble(Double::doubleValue).toArray();
		Location location;
		for (int i = 0; i < pointsList.length; i++) {
			location = new Location(pointsList[i]);
			satellite.setPosition(location);
		}

	}

	public String[] getSatelliteLoc(String satelliteName) {

		return environment.getProperty("satellites." + satelliteName + ".position").split(",");

	}

}
