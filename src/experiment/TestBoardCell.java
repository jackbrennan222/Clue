package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row,col;
	private boolean room,occupied;
	private Set<TestBoardCell> adjSet = new HashSet<TestBoardCell>();
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		room = occupied = false;
	}
	
	void addAdjacency(TestBoardCell cell) {
		adjSet.add(cell);
	}
	
	Set<TestBoardCell> getAdjList() {
		return adjSet;
	}
	
	public void setRoom(boolean room) {
		this.room = room;
	}
	
	public boolean isRoom() {
		return room;
	}
	
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public boolean getOccupied() {
		return occupied;
	}
}
