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
	public void testAdjacency0() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertEquals(3, testList.size());
	}
	@Test
	public void testAdjacency1() {
		TestBoardCell cell = board.getCell(3, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertEquals(3, testList.size());
	}
	@Test
	public void testAdjacency2() {
		TestBoardCell cell = board.getCell(1, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertEquals(4, testList.size());
	}
	@Test
	public void testAdjacency3() {
		TestBoardCell cell = board.getCell(3, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertEquals(4, testList.size());
	}
	@Test
	public void testAdjacency4() {
		TestBoardCell cell = board.getCell(2, 2);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertEquals(3, testList.size());
	}
}
