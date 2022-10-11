package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell,labelCell;
	
	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return "";
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
