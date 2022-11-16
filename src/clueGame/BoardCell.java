package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row,col; // location vars
	private char initial,secretPassage; // characters to represent the room and possible secret passageways
	private DoorDirection doorDirection; // enum to denote door direction
	private boolean roomLabel,roomCenter,isRoom,isOccupied; // booleans to represent if a cell is a label, center, in a room, or occupied by a player
	private Set<BoardCell> adjList; // the cell's list of adjacent cells
	private Color color;
	
	public BoardCell(int row, int col) {
		// setting up vars
		this.row = row;
		this.col = col;
		adjList = new HashSet<BoardCell>();
		this.roomLabel = this.roomCenter = this.isRoom = this.isOccupied = false; // assume false until changed
		this.doorDirection = DoorDirection.NONE; // assume no door
	}
	
	/**
	 * function to draw a cell
	 * 
	 * @param g
	 * @param cellWidth
	 * @param cellHeight
	 * @param xOffset
	 * @param yOffset
	 */
	public void draw(Graphics2D g, int cellWidth, int cellHeight, int xOffset, int yOffset) {
		// solid background
		g.setColor(color);
		g.fillRect(xOffset, yOffset, cellWidth, cellHeight);
		// hallways have a grid
		if (!isRoom()) {	
			g.setColor(Color.BLACK);
			g.drawRect(xOffset, yOffset, cellWidth, cellHeight);
		}
		// coloring doors
		g.setColor(Color.BLUE);
		switch (doorDirection) {
			case UP: g.fillRect(xOffset, yOffset, cellWidth, 5);
				break;
			case DOWN: g.fillRect(xOffset, yOffset + cellHeight - 5, cellWidth, 5);
				break;
			case LEFT: g.fillRect(xOffset, yOffset, 5, cellHeight);
				break;
			case RIGHT: g.fillRect(xOffset + cellWidth - 5, yOffset, 5, cellHeight);
				break;
			default:
				break;
		}
	}
	
	/**
	 * a function to draw room names
	 * 
	 * @param g
	 * @param roomName
	 * @param cellWidth
	 * @param cellHeight
	 */
	public void drawRoomName(Graphics2D g, String roomName, int cellWidth, int cellHeight) {
		g.setColor(Color.BLUE);
		g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
		g.drawString(roomName, col * cellWidth, row * cellHeight);
	}

	
	public void drawTarget(Graphics2D g, int cellWidth, int cellHeight) {
		g.setColor(Color.CYAN);
		g.fillRect(col * cellWidth, row * cellHeight, (col + 1) * cellWidth, (row + 1) * cellHeight);
		g.setColor(Color.BLACK);
		g.drawRect(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
	}
	
	// add cell to adjList
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setRoom(boolean room) {
		this.isRoom = room;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		this.isOccupied = occupied;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void setRoomLabel(boolean roomLabel) {
		this.roomLabel = roomLabel;
	}
	
	public boolean isLabel() {
		return roomLabel;
	}
	
	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
	
	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}

	public char getInitial() {
		return initial;
	}
	
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public boolean isDoorway() {
		return (doorDirection != DoorDirection.NONE); 
	}	
}
