package de.iteratec.schnitzel.server.persistence;

import org.springframework.stereotype.Component;

import de.iteratec.schnitzel.common.model.Beacon;

@Component
public class BeaconDAO extends BaseDAO<Beacon> {
	
	public BeaconDAO() {
		super(Beacon.class);
	}

}
