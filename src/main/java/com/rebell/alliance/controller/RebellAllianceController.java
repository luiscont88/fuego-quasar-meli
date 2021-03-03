package com.rebell.alliance.controller;

import com.rebell.alliance.entity.Satellite;
import com.rebell.alliance.entity.SatelliteWrapper;
import com.rebell.alliance.exceptions.LocationException;
import com.rebell.alliance.exceptions.MessageException;
import com.rebell.alliance.services.RebellAllianceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "${communication.context.path}")
public class RebellAllianceController {

	@Autowired
	private RebellAllianceService rebellAllianceService;
	
	@PostMapping("/")
	public String validateConnection() {

		return "Connection successfully";
		
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(path = "topsecret", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity topSecret(RequestEntity<SatelliteWrapper> requestEntity) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(rebellAllianceService.getCargoShip(requestEntity));
		} catch (MessageException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (LocationException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(path = "topsecret_split", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity topSecretSplitPost(RequestEntity<Satellite> requestEntity) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(rebellAllianceService.getSimpleCargoShip(requestEntity, null));
		} catch (MessageException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (LocationException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@SuppressWarnings("rawtypes")
	@GetMapping(path = "topsecret_split/{satellite_name}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity topSecretSplitGet(RequestEntity<Satellite> requestEntity, @PathVariable("satellite_name") String satelliteName) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(rebellAllianceService.getSimpleCargoShip(requestEntity, satelliteName));
		} catch (MessageException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch (LocationException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}
