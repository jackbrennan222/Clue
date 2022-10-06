package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	private int row,col;
	private boolean isRoom,isOccupied;
	private Set<TestBoardCell> adjSet = new HashSet<TestBoardCell>();
	
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		isRoom = isOccupied = false;
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
	
	public boolean getOccupied() {
		return isOccupied;
	}
}
