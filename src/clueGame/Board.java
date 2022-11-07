package clueGame;

import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.Color;

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
	private ArrayList<Player> players; // ArrayList to store the players
	private Solution theAnswer; // Solution instance to hold the referential answer to the game
	private ArrayList<Card> deck; // ArrayList to hold all of the cards

	private HashMap<String, Color> colorMap = new HashMap<>(); // Map to switch strings to awt.colors objects
	
	private Board() {
		super();
	}
	
	/**
	 * a faux constructor 
	 */
	public void initialize() { // Singleton Pattern "Constructor"
		roomSet = new HashSet<>();
		configMap = new HashMap<>();
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();

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
		theAnswer = createSolution();
		deal();
	}

	/**
	 * loading setup file and throwing exceptions when necessary
	 * 
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader fr = new FileReader(setupConfigFile);
		Scanner in = new Scanner(fr);
		fillColorMap();
		while ( in.hasNext() ) { // loop through text file
			String line = in.nextLine(); // grab current line
			if (line.startsWith("//")) { continue; } // testing if line is a not a comment
			if (line.indexOf(',') == -1) {
				in.close();
				throw new BadConfigFormatException();
			}
			String[] info = line.split(",");
			if (info.length == 2) {
				String type = info[0].strip();
				String name = info[1].strip();
				if (type.equals("Weapon")) {
					Card weapon = new Card(name, CardType.WEAPON);
					deck.add(weapon);
				} else {
					in.close();
					throw new BadConfigFormatException("setup file contains incorrect lines"); // situation when we will throw an exception
				}
			} else if (info.length == 3) {
				String type = info[0].strip();
				String name = info[1].strip();
				String extra = info[2].strip();
				if (type.equals("Room")) {
					roomSet.add(extra.charAt(0)); // add room to map if not currently in map
					configMap.put(extra.charAt(0), new Room(name)); // add room to map if not currently in map
					deck.add(new Card(name, CardType.ROOM));
				} else if (type.equals("Space")) {
					configMap.put(extra.charAt(0), new Room(name)); // add room to map if not currently in map
				} else if (type.equals("Human")) {
					if (!colorMap.containsKey(extra.toLowerCase())) {
						in.close();
						throw new BadConfigFormatException("bad color input"); // exception: file colors not correct
					}
					Color color = colorMap.get(extra.toLowerCase());
					Player player = new HumanPlayer(name, color);
					players.add(player); // add new player to game
					deck.add(new Card(name, CardType.PERSON)); // add new card for the person
				} else if (type.equals("Computer")) {
					if (!colorMap.containsKey(extra.toLowerCase())) { 
						in.close();
						throw new BadConfigFormatException("bad color input"); // exception: file colors not correct
					}
					Color color = colorMap.get(extra.toLowerCase());
					Player player = new ComputerPlayer(name, color);
					players.add(player); // add new computer player to the game
					deck.add(new Card(name, CardType.PERSON)); // add new card for the person
				}else {
					in.close();
					throw new BadConfigFormatException("setup file contains incorrect lines"); // situation when we will throw an exception
				}
			} else {
				in.close();
				throw new BadConfigFormatException(); // situation when we will throw an exception
			}
		}
		in.close();
	}

	/**
	 * used to initiate the colorMap data structure
	 */
	private void fillColorMap() {
		colorMap.put("red", Color.RED);
		colorMap.put("green", Color.GREEN);
		colorMap.put("blue", Color.BLUE);
		colorMap.put("yellow", Color.YELLOW);
		colorMap.put("magenta", Color.MAGENTA);
		colorMap.put("cyan", Color.CYAN);
		colorMap.put("black", Color.BLACK);
		colorMap.put("white", Color.WHITE);
		colorMap.put("gray", Color.GRAY);
		colorMap.put("light_gray", Color.LIGHT_GRAY);
		colorMap.put("dark_gray", Color.DARK_GRAY);
		colorMap.put("orange", Color.ORANGE);
		colorMap.put("pink", Color.PINK);
	}

	/**
	 * loading layout file and throwing exceptions when necessary
	 * 
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
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

	/**
	 * setup list for all cells mapped to a list of their adjacent cells
	 */
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

	/**
	 * calculate adjacent cells
	 * 
	 * @param i
	 * @param j
	 * @param curCell
	 */
	private void calcAdj(int i, int j, BoardCell curCell) {
		if (curCell.isRoom() && roomSet.contains(curCell.getSecretPassage())) {
			BoardCell roomCenter = configMap.get(curCell.getInitial()).getCenterCell();
			roomCenter.addAdjacency(configMap.get(curCell.getSecretPassage()).getCenterCell());
		}
		if (grid[i][j].getInitial() == 'W') {
			curCell.addAdjacency(grid[i][j]);
		}
	}

	/**
	 * create the solution
	 * 
	 * @return a randomly selected Solution object
	 */
	private Solution createSolution() {
		Collections.sort(deck);
		int numRooms = roomSet.size();
		int numPersons = players.size();
		int numWeapons = deck.size() - numPersons - numRooms;
		
		// incorrect conditions to generate an answer
		if (numPersons < 1 || numWeapons < 1) {
			return new Solution(null, null, null);
		}
		
		Card solRoom, solPerson, solWeapon;
		
		// randomly indexed cards for all three CardTypes
		int randRoom = (int)(Math.random() * (numRooms - 1));
		solRoom = deck.get(randRoom);
		
		int randPerson = numRooms + (int)(Math.random() * (numPersons - 1));
		solPerson = deck.get(randPerson);
		
		int randWeapon = numRooms + numPersons + (int)(Math.random() * (numWeapons - 1));
		solWeapon = deck.get(randWeapon);
		
		return new Solution(solRoom, solPerson, solWeapon);
	}

	private void deal() {
		if (players.size() < 1) { return; }
		ArrayList<Card> cardsAvailable = new ArrayList<Card>(deck);
		cardsAvailable.remove(theAnswer.getRoom());
		cardsAvailable.remove(theAnswer.getPerson());
		cardsAvailable.remove(theAnswer.getWeapon());
		for (int i = cardsAvailable.size(); i > 0; i--) {
			int playerPos = i % players.size();
			Player player = players.get(playerPos);
			Random rand = new Random();
			int index = rand.nextInt(cardsAvailable.size());
			Card card = cardsAvailable.get(index);
			player.updateHand(card);
			cardsAvailable.remove(card);
		}
	}

	public boolean checkAccusation(Card room, Card person, Card weapon) {
		return room == theAnswer.getRoom() && person == theAnswer.getPerson() && weapon == theAnswer.getPerson();
	}

	public Card handleSuggestion() {
		return new Card();
	}
	
	/**
	 * calculate targets based on a given cell and length to travel
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<>();
		targets = new HashSet<>();
		
		visited.add(startCell);
		
		findAllTargets(startCell, pathLength);
	}
	
	/**
	 * recursive method to create an adjacency list
	 * 
	 * @param thisCell
	 * @param numSteps
	 */
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
	// setters
	
	public void setConfigFiles(String layoutConfigFile, String setupConfigFile) {
		this.setupConfigFile = "./data/" + setupConfigFile;
		this.layoutConfigFile = "./data/" + layoutConfigFile;
	}
	
	// getters

	public static Board getInstance() {
		return theInstance;
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
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Solution getTheAnswer() {
		return theAnswer;
	}
}
