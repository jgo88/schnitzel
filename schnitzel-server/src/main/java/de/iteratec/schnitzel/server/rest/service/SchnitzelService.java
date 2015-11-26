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
	
	@GET
	@Path("/insertBeacons")
	public Response insertBeacons() {
		beaconBA.insert(createBeacon("ice1", "6E4331BD-A7B4-4E21-9402-FE00A851497B"));
		beaconBA.insert(createBeacon("blueberry2", "B38BA925-D62C-431C-9BCF-5B7B1E25FA10"));
		beaconBA.insert(createBeacon("mint3", "49D24F9D-54DC-4139-8A81-1C97FDEC8A19"));
		beaconBA.insert(createBeacon("ice4", "8D1CA287-DED3-4A5D-A01E-B241AA9D8895"));
		beaconBA.insert(createBeacon("ice5", "A8D508D4-52C4-480F-BD1D-30B321F0FF23"));
		beaconBA.insert(createBeacon("blueberry6", "1E794D19-0DEA-4B82-AE83-ECC0662B4666"));
		beaconBA.insert(createBeacon("blueberry7", "6B6F1426-73D9-4B10-A466-3E293D3AF0DD"));
		beaconBA.insert(createBeacon("mint8", "3C52BAD2-4B1D-4BA4-9535-0AE9771DFC3E"));
		beaconBA.insert(createBeacon("mint9", "C6B1BE53-62B2-4BC8-8891-6265FD467226"));
		return Response.status(200).entity("beacons inserted successfully").build();
	}

	private Beacon createBeacon(String name, String uuid) {
		Beacon beacon = new Beacon();
		beacon.setName(name);
		beacon.setBeaconUuid(uuid);
		return beacon;
	}

}
