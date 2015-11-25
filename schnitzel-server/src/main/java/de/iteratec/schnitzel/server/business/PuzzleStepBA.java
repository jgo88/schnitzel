package de.iteratec.schnitzel.server.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.iteratec.schnitzel.common.model.PuzzleStep;
import de.iteratec.schnitzel.server.persistence.IBaseDAO;
import de.iteratec.schnitzel.server.persistence.PuzzleStepDAO;

@Service
@Transactional
public class PuzzleStepBA extends BaseBA<PuzzleStep> {
	
	@Autowired
	private PuzzleStepDAO dao;

	@Override
	public IBaseDAO<PuzzleStep> getDao() {
		return dao;
	}

}
