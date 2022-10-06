package experiment;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class TestBoard {
	
	private int numRows,numCols;
	private TestBoardCell[][] gameboard;
	private Map<TestBoardCell, HashSet<TestBoardCell>> adjList;
	private HashSet<TestBoardCell> targets,visited;
	
	public TestBoard(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		this.gameboard = new TestBoardCell[numRows][numCols];
		
		adjList = new HashMap<TestBoardCell, HashSet<TestBoardCell>>();
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				HashSet<TestBoardCell> vals = new HashSet<TestBoardCell>(); 
				if (i - 1 >= 0 && ! this.gameboard[i-1][j].isOccupied()) {
					vals.add(gameboard[i-1][j]);
				}
				if (i + 1 < numRows && ! this.gameboard[i+1][j].isOccupied()) {
					vals.add(gameboard[i+1][j]);
				}
				if (j - 1 >= 0 && ! this.gameboard[i][j-1].isOccupied()) {
					vals.add(gameboard[i][j-1]);
				}
				if (j + 1 < numCols && ! this.gameboard[i][j+1].isOccupied()) {
					vals.add(gameboard[i][j+1]);
				}
				adjList.put(gameboard[i][j], vals);
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
			if (! visited.contains(adjCell)) {
				visited.add(adjCell);
				if (numSteps == 1) {
					targets.add(adjCell);
				} else {
					findAllTargets(adjCell, numSteps - 1);
				}
				visited.remove(adjCell);
			}
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell(int r, int c) {
		return gameboard[r][c];
	}
}
