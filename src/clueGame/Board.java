package clueGame;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Board {
	private BoardCell[][] grid; // the board
	private int numRows,numColumns; // numbers of rows and columns
	private String layoutConfigFile,setupConfigFile; // filenames for the layout and setup files
	private HashMap<Character, Room> roomMap; // a data structure to map room characters to room strings
	private static Board theInstance = new Board(); // Singleton Pattern instance
	private HashSet<BoardCell> targets,visited; // Sets to store unique cells for targets of cell motion, and to store visited cells
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() { // Singleton Pattern "Constructor"
		roomMap = new HashMap<Character, Room>();
		
		try { // loading files and catching two different types of errors
			loadSetupConfig();
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
			return;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		// iterate through board and add all of the adjacent cells
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
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	// loading setup file and throwing exceptions when necessary
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader fr = new FileReader(setupConfigFile);
		Scanner in = new Scanner(fr);
		String line; // a string to hold the line of text
		while ( in.hasNext() ) { // loop through text file
			line = in.nextLine(); // grab current line
			if (line.charAt(0) != '/') { // testing if line is a not a comment
				if (!line.substring(0, 4).equals("Room") && !line.substring(0, 5).equals("Space")) throw new BadConfigFormatException(); // situation when we will throw an exception
				line = line.substring(line.indexOf(",") + 2, line.length()); // grab useful text
				roomMap.put(line.charAt(line.length() - 1), new Room(line.substring(0, line.indexOf(',')))); // add room to map if not currently in map
			}
		}
		in.close();
	}

	// loading layout file and throwing exceptions when necessary
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader fr = new FileReader(layoutConfigFile);
		Scanner in = new Scanner(fr);
		String line = in.nextLine(); // current line
		String[] lineRay = line.split(","); // current line split into an array
		numColumns = lineRay.length;
		ArrayList<String[]> tempBoard = new ArrayList<String[]>();
		tempBoard.add(lineRay);
		while (in.hasNext()) { // loop through csv file
			line = in.nextLine();
			lineRay = line.split(",");
			if (lineRay.length != numColumns) throw new BadConfigFormatException(); // when to throw an exception
			tempBoard.add(lineRay);
		}
		numRows = tempBoard.size();
		grid = new BoardCell[numRows][numColumns]; // initializing board with dimensions
		in.close();
		// iterate through board creating cells with correct attributes for each location
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (!roomMap.containsKey(tempBoard.get(i)[j].charAt(0))) throw new BadConfigFormatException(); // when to throw exception
				grid[i][j] = new BoardCell(i, j); // new cell
				grid[i][j].setInitial(tempBoard.get(i)[j].charAt(0)); // set initial
				if (tempBoard.get(i)[j].length() > 1) {
					// this switch statement deals with special spaces (spaces with more attributes than name and location)
					switch(tempBoard.get(i)[j].charAt(1)) {
					case '#':
						grid[i][j].setRoomLabel(true);
						theInstance.getRoom(grid[i][j]).setLabelCell(grid[i][j]);
						break;
					case '*': 
						grid[i][j].setRoomCenter(true);
						theInstance.getRoom(grid[i][j]).setCenterCell(grid[i][j]);
						break;
					case '^': 
						grid[i][j].setDoorDirection(DoorDirection.UP);
						break;
					case '>': 
						grid[i][j].setDoorDirection(DoorDirection.RIGHT);
						break;
					case '<': 
						grid[i][j].setDoorDirection(DoorDirection.LEFT);
						break;
					case 'v': 
						grid[i][j].setDoorDirection(DoorDirection.DOWN);
						break;
					default:
						grid[i][j].setSecretPassage(tempBoard.get(i)[j].charAt(1));
						break;
					}
				}
			}
		}
		in.close();
		
	}
	
	// calculate targets based on a given cell and length to travel
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		visited.add(startCell);
		
		findAllTargets(startCell, pathLength);
	}
	
	// recursive method to create an adjacency list
	private void findAllTargets(BoardCell thisCell, int numSteps) {
		for (BoardCell adjCell : thisCell.getAdjList()) {
			if (visited.contains(adjCell) || adjCell.isOccupied()) continue; // don't add visited/occupied cells
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
