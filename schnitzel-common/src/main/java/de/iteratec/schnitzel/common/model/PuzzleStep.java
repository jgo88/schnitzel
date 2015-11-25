package de.iteratec.schnitzel.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "PUZZLE_STEP")
public class PuzzleStep extends BaseEntity {
	
	private Beacon beacon;
	private String description;
	private PuzzleStep successor;
	
	@OneToOne(optional = false)
	public Beacon getBeacon() {
		return beacon;
	}
	
	public void setBeacon(Beacon beacon) {
		this.beacon = beacon;
	}
	
	@Column(nullable = false)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToOne
	public PuzzleStep getSuccessor() {
		return successor;
	}
	
	public void setSuccessor(PuzzleStep successor) {
		this.successor = successor;
	}

}
