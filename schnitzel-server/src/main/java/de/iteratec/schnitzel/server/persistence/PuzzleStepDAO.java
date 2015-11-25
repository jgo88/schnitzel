package de.iteratec.schnitzel.server.persistence;

import org.springframework.stereotype.Component;

import de.iteratec.schnitzel.common.model.PuzzleStep;

@Component
public class PuzzleStepDAO extends BaseDAO<PuzzleStep> {
	
	public PuzzleStepDAO() {
		super(PuzzleStep.class);
	}

}
