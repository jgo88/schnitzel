package de.iteratec.schnitzel.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "PUZZLE")
public class Puzzle extends BaseEntity {
	
	public static final String COL_NAME = "NAME";
	
	private String name;
	private PuzzleStep firstPuzzleStep;
	
	@Column(name=COL_NAME, unique=true, nullable=false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@OneToOne
	public PuzzleStep getFirstPuzzleStep() {
		return firstPuzzleStep;
	}

	public void setFirstPuzzleStep(PuzzleStep firstPuzzleStep) {
		this.firstPuzzleStep = firstPuzzleStep;
	}

}
