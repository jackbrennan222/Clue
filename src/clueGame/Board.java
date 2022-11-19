package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Erik Swanson
 * @author Jack Brennan
 */
public class Board extends JPanel {
	private BoardCell[][] grid; // the board
	private int numRows,numColumns; // numbers of rows and columns
	private String layoutConfigFile,setupConfigFile; // filenames for the layout and setup files
	private Set<Character> roomSet = new HashSet<Character>(); // a data structure to map room characters to room strings
	private Map<Character, Room> configMap = new HashMap<Character, Room>();
	private static Board theInstance = new Board(); // Singleton Pattern instance
	private Set<BoardCell> targets,visited; // Sets to store unique cells for targets of cell motion, and to store visited cells
	private ArrayList<Player> players = new ArrayList<Player>();; // ArrayList to store the players
	private Solution theAnswer; // Solution instance to hold the referential answer to the game
	private ArrayList<Card> deck = new ArrayList<Card>(); // ArrayList to hold all of the cards
	private Set<Card> roomCards = new HashSet<Card>();
	private Set<Card> playerCards = new HashSet<Card>();
	private Set<Card> weaponCards = new HashSet<Card>();
	private HumanPlayer human;
	private Player currentPlayer;
	private Random random;
	private int dice;
	private int cellWidth, cellHeight;

	private HashMap<String, Color> colorMap = new HashMap<>(); // Map to switch strings to awt.colors objects
	
	private Board() {
		super();
	}
	
	/**
	 * a faux constructor 
	 */
	public void initialize() { // Singleton Pattern "Constructor"
		clearData();
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
		setupGame();
	}

	public void clearData() {
		roomSet.clear();
		configMap.clear();
		deck.clear();
		players.clear();
		roomCards.clear();
		playerCards.clear();
		weaponCards.clear();
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
			String[] info = line.split(",");
			if (info.length == 2) {
				String type = info[0].strip();
				String name = info[1].strip();
				if (type.equals("Weapon")) {
					Card weapon = new Card(name, CardType.WEAPON);
					deck.add(weapon);
					weaponCards.add(weapon);
				} else {
					in.close();
					throw new BadConfigFormatException("setup file contains incorrect lines"); // situation when we will throw an exception
				}
			} else if (info.length == 3 || info.length == 5) {
				String type = info[0].strip();
				String name = info[1].strip();
				String extra = info[2].strip();
				int row = 0;
				int col = 0;
				if (info.length == 5) {
					row = Integer.parseInt(info[3].strip());
					col = Integer.parseInt(info[4].strip());	
				}
				Color color;
				Player player;
				Card card;
				switch (type) {
					case "Room":
					roomSet.add(extra.charAt(0)); // add room to map if not currently in map
						configMap.put(extra.charAt(0), new Room(name)); // add room to map if not currently in map
						card = new Card(name, CardType.ROOM);
						deck.add(card);
						roomCards.add(card);
						break;
						case "Space":
						configMap.put(extra.charAt(0), new Room(name)); // add room to map if not currently in map
						break;
					case "Human":
					if (!colorMap.containsKey(extra.toLowerCase())) {
						in.close();
						throw new BadConfigFormatException("bad color input"); // exception: file colors not correct
					}
					color = colorMap.get(extra.toLowerCase());
					player = new HumanPlayer(name, color);
					player.setPos(row, col);
						players.add(player); // add new player to game
						card = new Card(name, CardType.PERSON);
						deck.add(card); // add new card for the person
						playerCards.add(card);
						human = (HumanPlayer) player;
						break;
						case "Computer":
						if (!colorMap.containsKey(extra.toLowerCase())) { 
							in.close();
							throw new BadConfigFormatException("bad color input"); // exception: file colors not correct
						}
						color = colorMap.get(extra.toLowerCase());
						player = new ComputerPlayer(name, color);
						player.setPos(row, col);
						players.add(player); // add new computer player to the game
						card = new Card(name, CardType.PERSON);
						deck.add(card); // add new card for the person
						playerCards.add(card);
						break;
					default:
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
		float ratio = 0.33f;
		colorMap.put("red", lighter(Color.RED, ratio));
		colorMap.put("green", Color.GREEN);
		colorMap.put("blue", lighter(Color.BLUE, ratio));
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
	
	public Color lighter(Color color, float ratio) {
		return mergeColors(Color.WHITE, ratio, color, 1 - ratio);
    }
	
    public static Color mergeColors(Color a, float fa, Color b, float fb) {
        if (a == null) {
            return b;
        }
        if (b == null) {
			return a;
        }
        return new Color((fa * a.getRed() + fb * b.getRed()) / (fa + fb) / 255f,
		(fa * a.getGreen() + fb * b.getGreen()) / (fa + fb) / 255f,
		(fa * a.getBlue() + fb * b.getBlue()) / (fa + fb) / 255f);
    }
    
    @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
    	int windowWidth = getWidth();
    	int windowHeight = getHeight();
		// get the width and height for each individual cell
    	cellWidth = windowWidth / getNumColumns();
    	cellHeight = windowHeight / getNumRows();
    	
		// loop through each cell and draw on the board GUI
    	for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumColumns(); c++) {
				BoardCell curCell = getCell(r, c);
				boolean inTargets = currentPlayer instanceof HumanPlayer && (targets.contains(curCell) || targets.contains(configMap.get(curCell.getInitial()).getCenterCell()));
				grid[r][c].draw((Graphics2D) g, cellWidth, cellHeight, cellWidth * c, cellHeight * r, inTargets);
    		}
    	}
		
		// loop through each room and draw the name on the board GUI
		for (Room room : configMap.values()) {
			
			if (room.getLabelCell() != null) {
				room.getLabelCell().drawRoomName((Graphics2D) g, room.getName(), cellWidth, cellHeight);
			}
		}

		// draw each player on the board GUI
		for (Player p : players) {
			p.draw((Graphics2D) g, cellWidth, cellHeight);
		}

		
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
		ArrayList<BoardCell[]> boardArr = new ArrayList<>();
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
				row[i] = new BoardCell(boardArr.size(), i);
				BoardCell curCell = row[i];
				String label = lineRay[i];
				char initial = label.charAt(0);
				if (!configMap.containsKey(initial)) { 
					in.close();
					throw new BadConfigFormatException("layout file room not present in setup file");
				}
				curCell.setInitial(initial);
				switch(initial) {
				case 'X': curCell.setColor(Color.BLACK);
					break;
				case 'W': curCell.setColor(Color.YELLOW);
					break;
				default: curCell.setColor(Color.GRAY);
					break;
				}
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
			boardArr.add(row);
		}
		in.close();
		numRows = boardArr.size();
		grid = new BoardCell[numRows][numColumns];
		boardArr.toArray(grid);
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

	private void setupGame() {
		addMouseListener(new BoardListener());
		theAnswer = createSolution();
		deal();
		currentPlayer = players.get(0);
		random = new Random();
		dice = random.nextInt(6) + 1;
		calcTargets(currentPlayer.getCell(), dice);
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
		// TODO: remove line below after computerAI test / class has been refactored
		Collections.sort(deck);
		Card[] rooms = roomCards.toArray(new Card[0]);
		Card[] people = playerCards.toArray(new Card[0]);
		Card[] weapons = weaponCards.toArray(new Card[0]);
		
		// incorrect conditions to generate an answer
		if (people.length < 1 || weapons.length < 1) {
			return new Solution(null, null, null);
		}
		
		// randomly indexed cards for all three CardTypes
		Random rand = new Random();
		Card solRoom = rooms[rand.nextInt(rooms.length)];
		Card solPerson = people[rand.nextInt(people.length)];
		Card solWeapon = weapons[rand.nextInt(weapons.length)];
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
		return room.equals(theAnswer.getRoom()) && person.equals(theAnswer.getPerson()) && weapon.equals(theAnswer.getWeapon());
	}

	public Card handleSuggestion(Player player, Solution suggestion) {
		for (Player p : players) {
			if (p.equals(player)) { continue; }
			Card dispute = p.disproveSuggestion(suggestion.getRoom(), suggestion.getPerson(), suggestion.getWeapon());
			if (dispute != null) {
				return dispute;
			}
		}
		return null;
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

	public Set<Card> getRoomCards() {
		return roomCards;
	}

	public Set<Card> getPlayerCards() {
		return playerCards;
	}

	public Set<Card> getWeaponCards() {
		return weaponCards;
	}
	
	public HumanPlayer getHuman() {
		return human;
	}

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

	public int getDice() {
		return dice;
	}

	public void next() {
		if (!currentPlayer.isTurnOver()) {
			ClueGame.errorMessage("Please finish your turn!");
		} else {
			cellWidth = getWidth() / getNumColumns();
			cellHeight = getHeight() / getNumRows();
			if (currentPlayer instanceof HumanPlayer) {
				repaint();
			}
			// udpate player
			int index = players.indexOf(currentPlayer);
			index = (index + 1) % players.size();
			currentPlayer = players.get(index);
			// roll dice
			dice = random.nextInt(6) + 1;
			// calc targets
			calcTargets(currentPlayer.getCell(), dice);
			if (currentPlayer instanceof HumanPlayer) {
				repaint();
				currentPlayer.setTurnOver(false);
			} else {
				ComputerPlayer comp = (ComputerPlayer) currentPlayer;
				comp.doAccusation();
				comp.doMove();
				comp.makeSuggestion();
				currentPlayer.setTurnOver(true);
			}
		}
	}

	 private class BoardListener implements MouseListener {
		 @Override
		 public void mousePressed(MouseEvent e) {}
		 @Override
		 public void mouseReleased(MouseEvent e) {}
		 @Override
		 public void mouseEntered(MouseEvent e) {}
		 @Override
		 public void mouseExited(MouseEvent e) {}


		@Override
		public void mouseClicked(MouseEvent e) {
			if (currentPlayer.isTurnOver()) {
				return;
			}
			if (currentPlayer instanceof HumanPlayer) {
				HumanPlayer human = (HumanPlayer) currentPlayer;
				boolean onTarget = false;

				int cellCol = e.getX() / cellWidth;
				int cellRow = e.getY() / cellHeight;

				BoardCell cell = getCell(cellRow, cellCol);

				if (targets.contains(cell)) {
					onTarget = true;
					human.doMove(cell);
					targets.clear();
					repaint();
					if (human.getCell().isRoom()) {
						human.makeSuggestion();
					}
					currentPlayer.setTurnOver(true);
				} else if (cell.isRoom() && targets.contains(configMap.get(cell.getInitial()).getCenterCell())) {
					onTarget = true;
					human.doMove(configMap.get(cell.getInitial()).getCenterCell());
					targets.clear();
					repaint();
					if (human.getCell().isRoom()) {
						human.makeSuggestion();
					}
					currentPlayer.setTurnOver(true);

				}
				if (!onTarget) {
					ClueGame.errorMessage("That is not a target.");
				}
			}		
		}
	 }
}