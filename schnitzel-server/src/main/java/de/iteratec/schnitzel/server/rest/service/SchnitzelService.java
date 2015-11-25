package de.iteratec.schnitzel.server.rest.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.iteratec.schnitzel.common.model.Beacon;
import de.iteratec.schnitzel.common.model.Puzzle;
import de.iteratec.schnitzel.common.model.PuzzleStep;
import de.iteratec.schnitzel.server.business.BeaconBA;
import de.iteratec.schnitzel.server.business.PuzzleBA;
import de.iteratec.schnitzel.server.business.PuzzleStepBA;

@Component
@Path("/schnitzel")
public class SchnitzelService {
	
	@Autowired
	private PuzzleBA puzzleBA;
	
	@Autowired
	private PuzzleStepBA puzzleStepBA;
	
	@SuppressWarnings("unused")
	@Autowired
	private BeaconBA beaconBA;
	
	@GET
	@Path("/maria")
	public Response getSchnitzel() {
		return Response.status(200).entity("2").build();
	}
	
	@GET
	@Path("/putPuzzle/{name}")
	public Response addPuzzle(@PathParam("name") String name) {
		Puzzle puzzle = new Puzzle();
		puzzle.setName(name);
		puzzleBA.insert(puzzle);
		
		String successMessage = "Inserted puzzle with name " + name
				+ ". PUZZLE table now has " + puzzleBA.findAll(null).size() + " elements.";
		return Response.status(200).entity(successMessage).build();
	}
	
	@GET
	@Path("/puzzles")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Puzzle> getAllPuzzles() {
		return puzzleBA.findAll(null);
	}
	
	@GET
	@Path("/puzzle/{puzzleId}/firstStep")
	@Produces(MediaType.APPLICATION_JSON)
	public PuzzleStep getFirstPuzzleStep(@PathParam("puzzleId") long puzzleId) {
		Puzzle puzzle = puzzleBA.findById(puzzleId, new String[] {"firstPuzzleStep"});
		return puzzle.getFirstPuzzleStep();
	}
	
	@GET
	@Path("/step/{puzzleStepId}/successor")
	@Produces(MediaType.APPLICATION_JSON)
	public PuzzleStep getSuccessor(@PathParam("puzzleStepId") long puzzleStepId) {
		PuzzleStep puzzleStep = puzzleStepBA.findById(puzzleStepId, new String[] {"successor"});
		return puzzleStep.getSuccessor();
	}
	
	@GET
	@Path("/step/{puzzleStepId}/beacon")
	@Produces(MediaType.APPLICATION_JSON)
	public Beacon getBeacon(@PathParam("puzzleStepId") long puzzleStepId) {
		PuzzleStep puzzleStep = puzzleStepBA.findById(puzzleStepId, new String[] {"beacon"});
		return puzzleStep.getBeacon();
	}

}
