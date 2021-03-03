package com.rebell.alliance.services;

import com.rebell.alliance.entity.CargoShip;
import com.rebell.alliance.exceptions.LocationException;
import com.rebell.alliance.exceptions.MessageException;
import org.springframework.http.RequestEntity;

public interface RebellAllianceService {

    public CargoShip getCargoShip(RequestEntity<?> requestEntity) throws MessageException, LocationException;
    
    public CargoShip getSimpleCargoShip(RequestEntity<?> requestEntity, String satelliteName) throws MessageException, LocationException;
    
}
