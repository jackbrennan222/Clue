package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row,col; //row and col on the board that the cell is positioned at
	private boolean isRoom,isOccupied; //bool if the cell is a room or is occupied by a piece
	private Set<TestBoardCell> adjSet; //set of cell adjacent to the cell
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		isRoom = isOccupied = false;
		adjSet = new HashSet<TestBoardCell>();
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjSet.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjSet;
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
}
