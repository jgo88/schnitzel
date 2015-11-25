package de.iteratec.schnitzel.server.persistence;

import org.springframework.stereotype.Component;

import de.iteratec.schnitzel.common.model.Puzzle;

@Component
public class PuzzleDAO extends BaseDAO<Puzzle> {
	
	public PuzzleDAO() {
		super(Puzzle.class);
	}

}
