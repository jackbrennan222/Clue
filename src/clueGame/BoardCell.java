package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row,col; // location vars
	private char initial,secretPassage; // characters to represent the room and possible secret passageways
	private DoorDirection doorDirection; // enum to denote door direction
	private boolean roomLabel,roomCenter,isRoom,isOccupied; // booleans to represent if a cell is a label, center, in a room, or occupied by a player
	private Set<BoardCell> adjList; // the cell's list of adjacent cells
	
	// constructor
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		adjList = new HashSet<BoardCell>();
		this.roomLabel = this.roomCenter = this.isRoom = this.isOccupied = false; // assume false until changed
		this.doorDirection = DoorDirection.NONE; // assume no door
	}
	
	// add cell to adjList
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
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
