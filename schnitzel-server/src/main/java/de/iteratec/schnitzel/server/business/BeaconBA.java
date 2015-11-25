package de.iteratec.schnitzel.server.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.iteratec.schnitzel.common.model.Beacon;
import de.iteratec.schnitzel.server.persistence.BeaconDAO;
import de.iteratec.schnitzel.server.persistence.IBaseDAO;

@Service
@Transactional
public class BeaconBA extends BaseBA<Beacon> {
	
	@Autowired
	private BeaconDAO dao;

	@Override
	public IBaseDAO<Beacon> getDao() {
		return dao;
	}

}
