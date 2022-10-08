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
		Set<TestBoardCell> testList = cell.getAdjList(); //get the adjList of the cell
		assertEquals(3, testList.size()); //test if list is the right size
		
		TestBoardCell cell1 = board.getCell(3, 3); //store the cell at location in a variable
		Set<TestBoardCell> testList1 = cell1.getAdjList(); //get the adjList of the cell
		assertEquals(3, testList1.size()); //test if list is the right size
		
		TestBoardCell cell2 = board.getCell(1, 3); //store the cell at location in a variable
		Set<TestBoardCell> testList2 = cell2.getAdjList(); //get the adjList of the cell
		assertEquals(4, testList2.size()); //test if list is the right size
		
		TestBoardCell cell3 = board.getCell(1, 3); //store the cell at location in a variable
		Set<TestBoardCell> testList3 = cell3.getAdjList(); //get the adjList of the cell
		assertEquals(4, testList3.size()); //test if list is the right size
		
		TestBoardCell cell4 = board.getCell(1, 3); //store the cell at location in a variable
		Set<TestBoardCell> testList4 = cell4.getAdjList(); //get the adjList of the cell
		assertEquals(4, testList4.size()); //test if list is the right size
	}
	
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell, 3); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets = board.getTargets(); //get the target list of the target calc
		
		assertEquals(7, targets.size()); //test if list is the right size
		
		TestBoardCell cell1 = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell1, 4); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets1 = board.getTargets(); //get the target list of the target calc
		
		assertEquals(7, targets1.size()); //test if list is the right size
		
		TestBoardCell cell2 = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell2, 5); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets2 = board.getTargets(); //get the target list of the target calc
		
		assertEquals(5, targets2.size()); //test if list is the right size
		
		TestBoardCell cell3 = board.getCell(3, 3); //store the cell at location in a variable
		board.calcTargets(cell3, 5); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets3 = board.getTargets(); //get the target list of the target calc
		
		assertEquals(4, targets3.size()); //test if list is the right size
		
		TestBoardCell cell4 = board.getCell(3, 3); //store the cell at location in a variable
		board.calcTargets(cell4, 6); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets4 = board.getTargets(); //get the target list of the target calc
		
		assertEquals(4, targets4.size()); //test if list is the right size
	}
	
	@Test
	public void testTargetsRoom() {
		board.getCell(1, 0).setRoom(true); //set the cell to be a room
		board.getCell(0, 1).setRoom(true); //set the cell to be a room
		
		TestBoardCell cell = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell, 3); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets = board.getTargets(); //get the target list of the target calc
		
		assertEquals(6, targets.size()); //test if list is the right size
		
		TestBoardCell cell2 = board.getCell(1, 1); //store the cell at location in a variable
		board.calcTargets(cell2, 6); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets2 = board.getTargets(); //get the target list of the target calc
		
		assertEquals(3, targets2.size()); //test if list is the right size
	}
	
	@Test
	public void testTargetsOccupied() {
		board.getCell(1, 0).setOccupied(true); //set the cell to be occupied
		board.getCell(0, 1).setOccupied(true); //set the cell to be occupied
		
		TestBoardCell cell = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell, 3); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets = board.getTargets(); //get the target list of the target calc
		
		assertEquals(6, targets.size()); //test if list is the right size
		
		TestBoardCell cell2 = board.getCell(1, 1); //store the cell at location in a variable
		board.calcTargets(cell2, 6); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets2 = board.getTargets(); //get the target list of the target calc
		
		assertEquals(3, targets2.size()); //test if list is the right size
	}
	
	@Test
	public void testTargetsMixed() {
		board.getCell(1, 0).setOccupied(true); //set the cell to be occupied
		board.getCell(0, 1).setRoom(true); //set the cell to be a room
		
		TestBoardCell cell = board.getCell(0, 0); //store the cell at location in a variable
		board.calcTargets(cell, 3); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets = board.getTargets(); //get the target list of the target calc
		
		assertEquals(2, targets.size()); //test if list is the right size
		
		TestBoardCell cell2 = board.getCell(1, 1); //store the cell at location in a variable
		board.calcTargets(cell2, 6); //calc targets for the cell with given pathLength
		Set<TestBoardCell> targets2 = board.getTargets(); //get the target list of the target calc
		
		assertEquals(3, targets2.size()); //test if list is the right size
	}
}
