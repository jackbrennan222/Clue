package clueGame;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Erik Swanson
 * @author Jack Brennan
 */
public class Board {
	private BoardCell[][] grid; // the board
	private int numRows,numColumns; // numbers of rows and columns
	private String layoutConfigFile,setupConfigFile; // filenames for the layout and setup files
	private HashSet<Character> roomSet; // a data structure to map room characters to room strings
	private HashMap<Character, Room> configMap;
	private static Board theInstance = new Board(); // Singleton Pattern instance
	private HashSet<BoardCell> targets,visited; // Sets to store unique cells for targets of cell motion, and to store visited cells
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() { // Singleton Pattern "Constructor"
		roomSet = new HashSet<>();
		configMap = new HashMap<>();
		
		try { // loading files and catching two different types of errors
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			System.out.println(e);
			return;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return;
		}
		setupAdjList();
	}

	// loading setup file and throwing exceptions when necessary
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader fr = new FileReader(setupConfigFile);
		Scanner in = new Scanner(fr);
		while ( in.hasNext() ) { // loop through text file
			String line = in.nextLine(); // grab current line
			if (line.startsWith("//")) { continue; } // testing if line is a not a comment
			if (line.indexOf(',') == -1) {
				in.close();
				throw new BadConfigFormatException();
			}
			String[] info = line.split(",");
			if (info.length != 3) {
				in.close();
				throw new BadConfigFormatException();
			}
			String cellType = info[0].strip();
			String cellName = info[1].strip();
			char cellInitial = info[2].strip().charAt(0);
			if (cellType.equals("Room")) {
				roomSet.add(cellInitial); // add room to map if not currently in map
				configMap.put(cellInitial, new Room(cellName)); // add room to map if not currently in map
			} else if (line.startsWith("Space")) {
				configMap.put(cellInitial, new Room(cellName)); // add room to map if not currently in map
			} else {
				in.close();
				throw new BadConfigFormatException("setup file contains incorrect lines"); // situation when we will throw an exception
			}
		}
		in.close();
	}

	// loading layout file and throwing exceptions when necessary
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader fr = new FileReader(layoutConfigFile);
		Scanner in = new Scanner(fr);
		numColumns = -1;
		ArrayList<BoardCell[]> boardAR = new ArrayList<>();
		while (in.hasNext()) { // loop through csv file
			String line = in.nextLine();
			String[] lineRay = line.split(",");
			if (numColumns == -1) { numColumns = lineRay.length; }
			if (lineRay.length != numColumns) {
				in.close();
				throw new BadConfigFormatException("layout file lines are not equal length"); // when to throw an exception
			}
			BoardCell[] row = new BoardCell[numColumns];
			for (int i = 0; i < numColumns; i++) {
				row[i] = new BoardCell(boardAR.size(), i);
				BoardCell curCell = row[i];
				String label = lineRay[i];
				char initial = label.charAt(0);
				if (!configMap.containsKey(initial)) { 
					in.close();
					throw new BadConfigFormatException("layout file room not present in setup file");
				}
				curCell.setInitial(initial);
				if (roomSet.contains(initial)) {
					curCell.setRoom(true);
				}
				if (label.length() != 2) { continue; }
				char modifier = label.charAt(1);
				switch (modifier) {
					case '#':
					curCell.setRoomLabel(true);
					getRoom(curCell).setLabelCell(curCell);
					break;
					case '*': 
					curCell.setRoomCenter(true);
					getRoom(curCell).setCenterCell(curCell);
					break;
					case '^': 
					curCell.setDoorDirection(DoorDirection.UP);
					break;
					case '>': 
					curCell.setDoorDirection(DoorDirection.RIGHT);
					break;
					case '<': 
					curCell.setDoorDirection(DoorDirection.LEFT);
					break;
					case 'v': 
					curCell.setDoorDirection(DoorDirection.DOWN);
					break;
					default:
					curCell.setSecretPassage(modifier);
					break;
				}
			}
			boardAR.add(row);
		}
		in.close();
		numRows = boardAR.size();
		grid = new BoardCell[numRows][numColumns];
		boardAR.toArray(grid);
	}

	private void setupAdjList() {
		// iterate through board and add all of the adjacent cells
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				BoardCell curCell = grid[i][j];
				if (i - 1 >= 0) {
					if (curCell.getDoorDirection() == DoorDirection.UP) {
						BoardCell roomCenter = configMap.get(grid[i - 1][j].getInitial()).getCenterCell();
						curCell.addAdjacency(roomCenter);
						roomCenter.addAdjacency(curCell);
					}
					calcAdj(i - 1, j, curCell);
				}
				if (i + 1 < numRows) {
					if (curCell.getDoorDirection() == DoorDirection.DOWN) {
						BoardCell roomCenter = configMap.get(grid[i + 1][j].getInitial()).getCenterCell();
						curCell.addAdjacency(roomCenter);
						roomCenter.addAdjacency(curCell);
					}
					calcAdj(i + 1, j, curCell);
				}
				if (j - 1 >= 0) {
					if (curCell.getDoorDirection() == DoorDirection.LEFT) {
						BoardCell roomCenter = configMap.get(grid[i][j - 1].getInitial()).getCenterCell();
						curCell.addAdjacency(roomCenter);
						roomCenter.addAdjacency(curCell);
					}
					calcAdj(i, j - 1, curCell);
				}
				if (j + 1 < numColumns) {
					if (curCell.getDoorDirection() == DoorDirection.RIGHT) {
						BoardCell roomCenter = configMap.get(grid[i][j + 1].getInitial()).getCenterCell();
						curCell.addAdjacency(roomCenter);
						roomCenter.addAdjacency(curCell);
					}
					calcAdj(i, j + 1, curCell);
				}
			}
		}
	}

	private void calcAdj(int i, int j, BoardCell curCell) {
		if (curCell.isRoom() && roomSet.contains(curCell.getSecretPassage())) {
			BoardCell roomCenter = configMap.get(curCell.getInitial()).getCenterCell();
			roomCenter.addAdjacency(configMap.get(curCell.getSecretPassage()).getCenterCell());
		}
		if (grid[i][j].getInitial() == 'W') {
			curCell.addAdjacency(grid[i][j]);
		}
	}
	
	// calculate targets based on a given cell and length to travel
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<>();
		targets = new HashSet<>();
		
		visited.add(startCell);
		
		findAllTargets(startCell, pathLength);
	}
	
	// recursive method to create an adjacency list
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell adjCell : thisCell.getAdjList()) {
			if (visited.contains(adjCell) || (adjCell.isOccupied() && !adjCell.isRoom())) continue; // don't add visited/occupied cells
			visited.add(adjCell);
			if (numSteps == 1 || adjCell.isRoom()) {
				targets.add(adjCell);
			} else {
				findAllTargets(adjCell, numSteps - 1);
			}
			visited.remove(adjCell);
		}
	}

	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
		this.setupConfigFile = "./data/" + setupConfigFile;
		this.layoutConfigFile = "./data/" + layoutConfigFile;
	}
	
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public BoardCell getCell(int r, int c) {
		return grid[r][c];
	}
	
	public Room getRoom(char initial) {
		return configMap.get(initial);
	}
	
	public Room getRoom(BoardCell cell) {
		return configMap.get(cell.getInitial());
	}
	
	public Set<BoardCell> getAdjList(int r, int c) {
		return theInstance.getCell(r, c).getAdjList();
	}
}
