package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.*;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	private static Board board;

    @BeforeAll
    static void setup() {
        // get board instance because it's singleton
		board = Board.getInstance();
		// set the proper config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// initialize the board
		board.initialize();
    }
	
	// test adjacencies in rooms
	// these cells are light orange on the planning spreadsheet
	@Test
	public void testAdjacenciesRoom() {
		// test marquez
		Set<BoardCell> testList = board.getAdjList(3, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(7, 12)));
		
		// test alderson
		testList = board.getAdjList(10, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(7, 3)));
		
		// test library
		testList = board.getAdjList(12, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(16, 21)));
	}
	
	// test adjacencies on doors cells
	// these cells are light orange on the planning spreadsheet
	@Test
	public void testAdjacencyDoor() {
		// test door to CTLM
		Set<BoardCell> testList = board.getAdjList(3, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 7)));
		assertTrue(testList.contains(board.getCell(3, 8)));
		assertTrue(testList.contains(board.getCell(4, 7)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		
		// test door to alderson
		testList = board.getAdjList(7, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(6, 3)));
		assertTrue(testList.contains(board.getCell(7, 4)));
		assertTrue(testList.contains(board.getCell(10, 3)));
		assertTrue(testList.contains(board.getCell(7, 2)));
		
		// test door to kaff
		testList = board.getAdjList(21, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(20, 17)));
		assertTrue(testList.contains(board.getCell(21, 19)));
		assertTrue(testList.contains(board.getCell(22, 17)));
		assertTrue(testList.contains(board.getCell(21, 16)));
	}
	
	// test adjacencies in walkways
	// these cells are dark orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways() {
		// test walkway at edge of board next to blocked cells
		Set<BoardCell> testList = board.getAdjList(24, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 7)));
		
		// test walkway diagonal to room but not next to door
		testList = board.getAdjList(7, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(6, 7)));
		assertTrue(testList.contains(board.getCell(8, 7)));
		assertTrue(testList.contains(board.getCell(7, 6)));
		assertTrue(testList.contains(board.getCell(7, 8)));
		
		// test walkway in between 2 rooms
		testList = board.getAdjList(17, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(17, 1)));
		assertTrue(testList.contains(board.getCell(17, 3)));
	}
	
	// test targets in CTLM
	// these cells are light blue on the planning spreadsheet
	@Test
	public void testTargetsInCTLM() {
		// tests a roll of 1 out of CTLM
		board.calcTargets(board.getRoom('C').getCenterCell(), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 7)));
		assertTrue(targets.contains(board.getCell(21, 19)));
		
		// tests a roll of 2 out of CTLM
		board.calcTargets(board.getCell(3, 3), 2);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(2, 7)));
		assertTrue(targets.contains(board.getCell(4, 7)));
		assertTrue(targets.contains(board.getCell(3, 8)));
		assertTrue(targets.contains(board.getCell(21, 19)));
		
		// tests a roll of 4 out of CTLM
		board.calcTargets(board.getCell(3, 3), 4);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(1, 8)));
		assertTrue(targets.contains(board.getCell(2, 7)));
		assertTrue(targets.contains(board.getCell(3, 8)));
		assertTrue(targets.contains(board.getCell(21, 19)));
	}
	
	// test targets in Kaff
	// these cells are light blue on the planning spreadsheet
	@Test
	public void testTargetsInKaffadar() {
		// tests a roll of 1 out of Kaff
		board.calcTargets(board.getRoom('K').getCenterCell(), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(21, 17)));
		
		// tests a roll of 2 out of Kaff
		board.calcTargets(board.getCell(21, 19), 2);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(21, 16)));
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(22, 17)));
		
		// tests a roll of 4 out of Kaff
		board.calcTargets(board.getCell(21, 19), 4);
		targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(24, 17)));
		assertTrue(targets.contains(board.getCell(23, 16)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(22, 17)));
	}
	
	// test targets in door cells
	// these cells are light blue on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// tests a roll of 1 at door leading to alderson
		board.calcTargets(board.getCell(7, 3), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(7, 2)));
		assertTrue(targets.contains(board.getCell(6, 3)));
		assertTrue(targets.contains(board.getCell(7, 4)));
		
		// tests a roll of 2 at door leading to alderson
		board.calcTargets(board.getCell(7, 3), 2);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(6, 2)));
		assertTrue(targets.contains(board.getCell(6, 4)));
		assertTrue(targets.contains(board.getCell(7, 5)));
		assertTrue(targets.contains(board.getCell(7, 1)));
		
		// tests a roll of 4 at door leading to alderson
		board.calcTargets(board.getCell(7, 3), 4);
		targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(6, 0)));
		assertTrue(targets.contains(board.getCell(7, 1)));
		assertTrue(targets.contains(board.getCell(6, 2)));
		assertTrue(targets.contains(board.getCell(6, 4)));
	}
	
	// test targets in walkway cells
	// these cells are light blue on the planning spreadsheet
	@Test
	public void testTargetsInWalkway1() {
		// tests a roll of 1 at walkway
		board.calcTargets(board.getCell(24, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(23, 7)));

		// tests a roll of 2 at walkway
		board.calcTargets(board.getCell(24, 7), 2);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(22, 7)));
		assertTrue(targets.contains(board.getCell(23, 8)));
		
		// tests a roll of 4 at walkway
		board.calcTargets(board.getCell(24, 7), 4);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(21, 3)));
		assertTrue(targets.contains(board.getCell(23, 8)));
		assertTrue(targets.contains(board.getCell(22, 7)));
		assertTrue(targets.contains(board.getCell(21, 8)));
		assertTrue(targets.contains(board.getCell(20, 7)));
	}
	
	// test targets in walkway cells
	// these cells are light blue on the planning spreadsheet
	@Test
	public void testTargetsInWalkway2() {
		// tests a roll of 1 at walkway
		board.calcTargets(board.getCell(7, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(6, 7)));
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(7, 8)));

		// tests a roll of 2 at walkway
		board.calcTargets(board.getCell(7, 7), 2);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(5, 7)));
		assertTrue(targets.contains(board.getCell(6, 8)));
		assertTrue(targets.contains(board.getCell(7, 9)));
		assertTrue(targets.contains(board.getCell(8, 8)));
		assertTrue(targets.contains(board.getCell(9, 7)));
		assertTrue(targets.contains(board.getCell(7, 5)));
		assertTrue(targets.contains(board.getCell(6, 6)));
		
		// tests a roll of 4 at walkway
		board.calcTargets(board.getCell(7, 7), 4);
		targets = board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(3, 7)));
		assertTrue(targets.contains(board.getCell(4, 8)));
		assertTrue(targets.contains(board.getCell(7, 3)));
		assertTrue(targets.contains(board.getCell(6, 4)));
		assertTrue(targets.contains(board.getCell(11, 7)));
	}
	
	// test targets with occupied cells
	// these cells are red on the planning spreadsheet
	@Test
	public void testTargetsOccupied() {
		// tests roll of 1 when cells are occupied
		board.getCell(7, 16).setOccupied(true);
		board.getCell(9, 16).setOccupied(true);
		board.calcTargets(board.getCell(8, 16), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(8, 15)));
		assertTrue(targets.contains(board.getCell(8, 17)));
		
		// tests roll of 2 when cells are occupied
		board.calcTargets(board.getCell(8, 16), 2);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(8, 14)));
		assertTrue(targets.contains(board.getCell(8, 18)));
		assertTrue(targets.contains(board.getCell(7, 15)));
		assertTrue(targets.contains(board.getCell(7, 17)));
		
		// tests roll of 4 when cells are occupied
		board.calcTargets(board.getCell(8, 16), 4);
		targets = board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(8, 12)));
		assertTrue(targets.contains(board.getCell(6, 16)));
		assertTrue(targets.contains(board.getCell(7, 13)));
		assertTrue(targets.contains(board.getCell(8, 14)));
		
		board.getCell(7, 16).setOccupied(false);
		board.getCell(9, 16).setOccupied(false);
	}
}