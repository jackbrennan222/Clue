package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	private TestBoardCell[][] grid;
	private HashSet<TestBoardCell> targets,visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS]; //create the board with set size
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j); //make a new board cell for each space in the board
			}
		}
		
		//for loop to calculate the adjacency of each tile
		//adds the cell to adjlist if valid adjacent cell
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (i - 1 >= 0) {
					grid[i][j].addAdjacency(grid[i-1][j]);
				}
				if (i + 1 < ROWS) {
					grid[i][j].addAdjacency(grid[i+1][j]);
				}
				if (j - 1 >= 0) {
					grid[i][j].addAdjacency(grid[i][j-1]);
				}
				if (j + 1 < COLS) {
					grid[i][j].addAdjacency(grid[i][j+1]);
				}
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		
		visited.add(startCell); //add the start cell to visited set because you can not pass over it again on the same turn
		
		findAllTargets(startCell, pathLength); //start the recursive function to calculate possible target cells
	}
	
	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for (TestBoardCell adjCell : thisCell.getAdjList()) {
			if (visited.contains(adjCell) || adjCell.isOccupied()) continue; //check if adjCell is in visited list and skips steps if it is
			visited.add(adjCell); //adds adjCell to visited list
			if (numSteps == 1 || adjCell.isRoom()) { //checks if at last step or in a room and adds adjCell to targets if it is
				targets.add(adjCell);
			} else { //else recurse through function
				findAllTargets(adjCell, numSteps - 1);
			}
			visited.remove(adjCell); //remove adjCell from visited list
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell(int r, int c) {
		return grid[r][c];
	}
}
