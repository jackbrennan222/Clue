package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	
	private int numRows,numCols;
	private TestBoardCell[][] gameboard;
	private HashSet<TestBoardCell> targets,visited;
	
	public TestBoard(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		gameboard = new TestBoardCell[numRows][numCols];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				gameboard[i][j] = new TestBoardCell(i, j);
			}
		}
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (i - 1 >= 0) {
					gameboard[i][j].addAdjacency(gameboard[i-1][j]);
				}
				if (i + 1 < numRows) {
					gameboard[i][j].addAdjacency(gameboard[i+1][j]);
				}
				if (j - 1 >= 0) {
					gameboard[i][j].addAdjacency(gameboard[i][j-1]);
				}
				if (j + 1 < numCols) {
					gameboard[i][j].addAdjacency(gameboard[i][j+1]);
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
		return gameboard[r][c];
	}
}
