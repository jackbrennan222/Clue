package clueGame;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Board {
	private BoardCell[][] grid;
	private int numRows,numColumns;
	private String layoutConfigFile,setupConfigFile;
	private HashMap<Character, Room> roomMap;
	private static Board theInstance = new Board();
	private HashSet<BoardCell> targets,visited;
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		roomMap = new HashMap<Character, Room>();
		
		try {
			loadSetupConfig();
			loadLayoutConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		grid = new BoardCell[numRows][numColumns];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (i - 1 >= 0) {
					grid[i][j].addAdjacency(grid[i-1][j]);
				}
				if (i + 1 < numRows) {
					grid[i][j].addAdjacency(grid[i+1][j]);
				}
				if (j - 1 >= 0) {
					grid[i][j].addAdjacency(grid[i][j-1]);
				}
				if (j + 1 < numColumns) {
					grid[i][j].addAdjacency(grid[i][j+1]);
				}
			}
		}
		
		
		
		
		
	}
	
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
		this.setupConfigFile = "./data/" + setupConfigFile;
		this.layoutConfigFile = "./data/" + layoutConfigFile;
	}
	
	public int getNumRows() {
		return 0;
	}

	public int getNumColumns() {
		return 0;
	}

	public void loadSetupConfig() throws FileNotFoundException {
		FileReader fr = new FileReader(setupConfigFile);
		Scanner in = new Scanner(fr);
		String line;
		while ( in.hasNext() ) {
			line = in.nextLine();
			if (line.charAt(0) != '/') {
				line = line.substring(line.indexOf(",") + 2, line.length());
				roomMap.put(line.charAt(line.length() - 1), new Room(line.substring(0, line.indexOf(','))));
			}
		}
	}
	
	public void loadLayoutConfig() throws FileNotFoundException {
		FileReader fr = new FileReader(layoutConfigFile);
		Scanner in = new Scanner(fr);
		String line = in.nextLine();
		String[] lineRay = line.split(",");
		numColumns = lineRay.length;
		numRows++;
		while (in.hasNext()) {
			line = in.nextLine();
			lineRay = line.split(",");
			if (lineRay.length != 0) {
				numRows++;
			}
		}
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		visited.add(startCell);
		
		findAllTargets(startCell, pathLength);
	}
	
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell adjCell : thisCell.getAdjList()) {
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
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public BoardCell getCell(int r, int c) {
		return grid[r][c];
	}
	
	public Room getRoom(char initial) {
		return roomMap.get(initial);
	}
	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}
}
