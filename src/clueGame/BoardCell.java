package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row,col;
	private char initial,secretPassage;
	private DoorDirection doorDirection;
	private boolean roomLabel,roomCenter,isRoom,isOccupied;
	private Set<BoardCell> adjList;
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		adjList = new HashSet<BoardCell>();
	}
	
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
		return null;
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
