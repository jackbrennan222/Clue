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
		
	}
	
	@Test
	public void testTargetsInCTLM() {
		
	}
	
	@Test
	public void testTargetsInKaffadar() {
		
	}
	
	@Test
	public void testTargetsAtDoor() {
		
	}
	
	@Test
	public void testTargetsInWalkway1() {
		
	}
	
	@Test
	public void testTargetsInWalkway2() {
		
	}
	
	@Test
	public void testTargetsOccupied() {
		
>>>>>>> 0e4e044362d309303ed46baedff5f5dabe9fc352
	}
}
