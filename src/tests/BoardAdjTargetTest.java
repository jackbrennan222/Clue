package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import clueGame.Board;
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
		
	}
}
