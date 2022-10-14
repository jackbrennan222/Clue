package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.*;

import clueGame.Board;
import clueGame.BoardCell;
import experiment.*;

public class BoardAdjTargetTest {
	private static Board board;
	
	@BeforeEach
	public void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testAdjacenciesRoom() {
		Set<BoardCell> testList = board.getAdjList(3, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(7, 12)));
		
		testList = board.getAdjList(10, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(7, 3)));
		
		testList = board.getAdjList(12, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(16, 21)));
	}
	
	@Test
	public void testAdjacencyDoor() {
		Set<BoardCell> testList = board.getAdjList(3, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 7)));
		assertTrue(testList.contains(board.getCell(3, 8)));
		assertTrue(testList.contains(board.getCell(4, 7)));
		assertTrue(testList.contains(board.getCell(3, 3)));

		testList = board.getAdjList(7, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(6, 3)));
		assertTrue(testList.contains(board.getCell(7, 4)));
		assertTrue(testList.contains(board.getCell(10, 3)));
		assertTrue(testList.contains(board.getCell(7, 2)));
		
		testList = board.getAdjList(21, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(20, 17)));
		assertTrue(testList.contains(board.getCell(21, 19)));
		assertTrue(testList.contains(board.getCell(22, 17)));
		assertTrue(testList.contains(board.getCell(21, 16)));
	}
	
	@Test
	public void testAdjacencyWalkways() {
		Set<BoardCell> testList = board.getAdjList(24, 7);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 7)));
		
		testList = board.getAdjList(7, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(6, 7)));
		assertTrue(testList.contains(board.getCell(8, 7)));
		assertTrue(testList.contains(board.getCell(7, 6)));
		assertTrue(testList.contains(board.getCell(7, 8)));
		
		testList = board.getAdjList(17, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(12, 1)));
		assertTrue(testList.contains(board.getCell(12, 3)));
	}
	
	@Test
	public void testTargetsInCTLM() {
		board.calcTargets(board.getRoom('C').getCenterCell(), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 7)));
		assertTrue(targets.contains(board.getCell(21, 19)));
		
		board.calcTargets(board.getCell(3, 3), 2);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(2, 7)));
		assertTrue(targets.contains(board.getCell(4, 7)));
		assertTrue(targets.contains(board.getCell(3, 8)));
		assertTrue(targets.contains(board.getCell(21, 17)));
		
		board.calcTargets(board.getCell(3, 3), 4);
		targets = board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(1, 8)));
		assertTrue(targets.contains(board.getCell(2, 7)));
		assertTrue(targets.contains(board.getCell(3, 8)));
		assertTrue(targets.contains(board.getCell(19, 17)));
	}
	
	@Test
	public void testTargetsInKaffadar() {
		board.calcTargets(board.getRoom('K').getCenterCell(), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 3)));
		assertTrue(targets.contains(board.getCell(21, 17)));
		
		board.calcTargets(board.getCell(21, 19), 2);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(3, 7)));
		assertTrue(targets.contains(board.getCell(21, 16)));
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(22, 17)));
		
		board.calcTargets(board.getCell(21, 19), 4);
		targets = board.getTargets();
		assertEquals(11, targets.size());
		assertTrue(targets.contains(board.getCell(24, 17)));
		assertTrue(targets.contains(board.getCell(23, 16)));
		assertTrue(targets.contains(board.getCell(1, 7)));
		assertTrue(targets.contains(board.getCell(2, 8)));
	}
	
	@Test
	public void testTargetsAtDoor() {
		board.calcTargets(board.getCell(7, 3), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(7, 2)));
		assertTrue(targets.contains(board.getCell(6, 3)));
		assertTrue(targets.contains(board.getCell(7, 4)));
		
		board.calcTargets(board.getCell(7, 3), 2);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(6, 2)));
		assertTrue(targets.contains(board.getCell(6, 4)));
		assertTrue(targets.contains(board.getCell(7, 5)));
		assertTrue(targets.contains(board.getCell(7, 1)));
		
		board.calcTargets(board.getCell(7, 3), 4);
		targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(6, 0)));
		assertTrue(targets.contains(board.getCell(7, 1)));
		assertTrue(targets.contains(board.getCell(6, 2)));
		assertTrue(targets.contains(board.getCell(6, 4)));
	}
	
	@Test
	public void testTargetsInWalkway1() {
		board.calcTargets(board.getCell(24, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(23, 7)));

		
		board.calcTargets(board.getCell(24, 7), 2);
		targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(22, 7)));
		assertTrue(targets.contains(board.getCell(23, 8)));
		
		board.calcTargets(board.getCell(24, 7), 4);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(21, 3)));
		assertTrue(targets.contains(board.getCell(23, 8)));
		assertTrue(targets.contains(board.getCell(22, 7)));
		assertTrue(targets.contains(board.getCell(21, 8)));
		assertTrue(targets.contains(board.getCell(20, 7)));
	}
	
	@Test
	public void testTargetsInWalkway2() {
		board.calcTargets(board.getCell(7, 7), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(6, 7)));
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(7, 8)));

		
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
		
		board.calcTargets(board.getCell(7, 7), 4);
		targets = board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(3, 7)));
		assertTrue(targets.contains(board.getCell(4, 8)));
		assertTrue(targets.contains(board.getCell(7, 3)));
		assertTrue(targets.contains(board.getCell(6, 4)));
		assertTrue(targets.contains(board.getCell(11, 7)));
	}
	
	@Test
	public void testTargetsOccupied() {
		board.getCell(7, 16).setOccupied(true);
		board.getCell(9, 16).setOccupied(true);
		board.calcTargets(board.getCell(8, 16), 1);
		board.getCell(7, 16).setOccupied(false);
		board.getCell(9, 16).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(8, 15)));
		assertTrue(targets.contains(board.getCell(8, 17)));
		
		board.getCell(7, 16).setOccupied(true);
		board.getCell(9, 16).setOccupied(true);
		board.calcTargets(board.getCell(8, 16), 2);
		board.getCell(7, 16).setOccupied(false);
		board.getCell(9, 16).setOccupied(false);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(8, 14)));
		assertTrue(targets.contains(board.getCell(8, 18)));
		assertTrue(targets.contains(board.getCell(7, 15)));
		assertTrue(targets.contains(board.getCell(7, 17)));
		
		board.getCell(7, 16).setOccupied(true);
		board.getCell(9, 16).setOccupied(true);
		board.calcTargets(board.getCell(8, 16), 4);
		board.getCell(7, 16).setOccupied(false);
		board.getCell(9, 16).setOccupied(false);
		targets = board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(8, 12)));
		assertTrue(targets.contains(board.getCell(6, 16)));
		assertTrue(targets.contains(board.getCell(7, 13)));
		assertTrue(targets.contains(board.getCell(8, 14git )));
	}
}
