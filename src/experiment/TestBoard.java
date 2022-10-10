package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	private TestBoardCell[][] grid;
	private HashSet<TestBoardCell> targets,visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i, j);
			}
		}
		
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
		
		visited.add(startCell);
		
		findAllTargets(startCell, pathLength);
	}
	
	private void findAllTargets(TestBoardCell thisCell, int numSteps) {
		for (TestBoardCell adjCell : thisCell.getAdjList()) {
			if (visited.contains(adjCell) || adjCell.isOccupied()) continue;
			visited.add(adjCell);
			if (numSteps == 1 || adjCell.isRoom()) {
				targets.add(adjCell);
			} else {
				findAllTargets(adjCell, numSteps - 1);
			}
			visited.remove(adjCell);
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell(int r, int c) {
		return grid[r][c];
	}
}
