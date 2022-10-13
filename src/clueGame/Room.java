package clueGame;

public class Room {
	private String name; // room name
	private BoardCell centerCell,labelCell; // cell where center of room is, cell where label will be printed
	
	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}
	
}
