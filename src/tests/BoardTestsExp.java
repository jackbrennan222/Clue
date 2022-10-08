package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.Set;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setup() {
		board = new TestBoard(4, 4);
	}
	
	@Test
	public void testAdjacency1() {
		TestBoardCell cell = board.getCell(0, 0); //store the cell at location in a variable
		Set<TestBoardCell> testList = cell.getAdjList();
		assertEquals(3, testList.size());
		
		TestBoardCell cell1 = board.getCell(3, 3); //store the cell at location in a variable
		Set<TestBoardCell> testList1 = cell1.getAdjList();
		assertEquals(3, testList1.size());
		
		TestBoardCell cell2 = board.getCell(1, 3); //store the cell at location in a variable
		Set<TestBoardCell> testList2 = cell2.getAdjList();
		assertEquals(4, testList2.size());
		
		TestBoardCell cell3 = board.getCell(1, 3); //store the cell at location in a variable
		Set<TestBoardCell> testList3 = cell3.getAdjList();
		assertEquals(4, testList3.size());
		
		TestBoardCell cell4 = board.getCell(1, 3); //store the cell at location in a variable
		Set<TestBoardCell> testList4 = cell4.getAdjList();
		assertEquals(4, testList4.size());
	}
	
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		
		assertEquals(7, targets.size());
		
		TestBoardCell cell1 = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell1, 4);
		Set<TestBoardCell> targets1 = board.getTargets();
		
		assertEquals(7, targets1.size());
		
		TestBoardCell cell2 = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell2, 5);
		Set<TestBoardCell> targets2 = board.getTargets();
		
		assertEquals(5, targets2.size());
		
		TestBoardCell cell3 = board.getCell(3, 3); //store the cell at location in a variable
		board.calcTargets(cell3, 5);
		Set<TestBoardCell> targets3 = board.getTargets();
		
		assertEquals(4, targets3.size());
		
		TestBoardCell cell4 = board.getCell(3, 3); //store the cell at location in a variable
		board.calcTargets(cell4, 6);
		Set<TestBoardCell> targets4 = board.getTargets();
		
		assertEquals(4, targets4.size());
	}
	
	@Test
	public void testTargetsRoom() {
		board.getCell(1, 0).setRoom(true);
		board.getCell(0, 1).setRoom(true);
		
		TestBoardCell cell = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		
		assertEquals(6, targets.size());
		
		TestBoardCell cell2 = board.getCell(1, 1); //store the cell at location in a variable
		board.calcTargets(cell2, 6);
		Set<TestBoardCell> targets2 = board.getTargets();
		
		assertEquals(3, targets2.size());
	}
	
	@Test
	public void testTargetsOccupied() {
		board.getCell(1, 0).setOccupied(true);
		board.getCell(0, 1).setOccupied(true);
		
		TestBoardCell cell = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		
		assertEquals(6, targets.size());
		
		TestBoardCell cell2 = board.getCell(1, 1); //store the cell at location in a variable
		board.calcTargets(cell2, 6);
		Set<TestBoardCell> targets2 = board.getTargets();
		
		assertEquals(3, targets2.size());
	}
	
	@Test
	public void testTargetsMixed() {
		board.getCell(1, 0).setOccupied(true);
		board.getCell(0, 1).setRoom(true);
		
		TestBoardCell cell = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		
		assertEquals(2, targets.size());
		
		TestBoardCell cell2 = board.getCell(1, 1); //store the cell at location in a variable
		board.calcTargets(cell2, 6);
		Set<TestBoardCell> targets2 = board.getTargets();
		
		assertEquals(3, targets2.size());
	}
}
