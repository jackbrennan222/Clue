package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
public class ComputerPlayer extends Player {
	private Set<BoardCell> visitedRooms = new HashSet<BoardCell>();

	public ComputerPlayer(String name, Color color) {
		super(name, color);
	}
	
	public ComputerPlayer(String name, int row, int col, Color color) {
		super(name, color);
		this.row = row;
		this.col = col;
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}

	public Solution createSuggestion() {
		Board board = Board.getInstance();
		Room room = board.getRoom(getCell());
		Card suggestedRoom = new Card();

		for (Card c : board.getRoomCards()) {
			if (c.getCardName().equals(room.getName())) {
				suggestedRoom = c;
				break;
			}
		}
		ArrayList<Card> unseenPeople = new ArrayList<Card>();
		for (Card c : board.getPlayerCards()) {
			if (seenCards.contains(c) || hand.contains(c)) continue;
			unseenPeople.add(c);
		}
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		for (Card c : board.getWeaponCards()) {
			if (seenCards.contains(c) || hand.contains(c)) continue;
			unseenWeapons.add(c);
		}
		Random rand = new Random();
		Card suggestionPerson = unseenPeople.get(rand.nextInt(unseenPeople.size()));
		Card suggestionWeapon = unseenWeapons.get(rand.nextInt(unseenWeapons.size()));
		return new Solution(suggestedRoom, suggestionPerson, suggestionWeapon);
	}

	public BoardCell selectTarget() {
		Set<BoardCell> targets = Board.getInstance().getTargets();
		ArrayList<BoardCell> roomTargets = new ArrayList<>();
		for (BoardCell bc : targets) {
			if (bc.isRoom()) {
				roomTargets.add(bc);
			}
		}
		for (BoardCell c : visitedRooms) {
			targets.remove(c);
			roomTargets.remove(c);
		}
		Random rand = new Random();
		if (!roomTargets.isEmpty()) {
			return roomTargets.get(rand.nextInt(roomTargets.size()));
		}
		return targets.toArray(new BoardCell[0])[rand.nextInt(targets.size())];
	}

	public void setVisitedRooms(Set<BoardCell> visitedRooms) {
		this.visitedRooms = visitedRooms;
	}

	@Override
	public void doAccusation() {
		Card roomCard = null;
		Card personCard = null;
		Card weaponCard = null;
		for (Card c : Board.getInstance().getRoomCards()) {
			if (seenCards.contains(c) || hand.contains(c)) { continue; }
			roomCard = c;
			break;
		}
		for (Card c : Board.getInstance().getPlayerCards()) {
			if (seenCards.contains(c) || hand.contains(c)) { continue; }
			personCard = c;
			break;
		}
		for (Card c : Board.getInstance().getWeaponCards()) {
			if (seenCards.contains(c) || hand.contains(c)) { continue; }
			weaponCard = c;
			break;
		}
		Board.getInstance().checkAccusation(roomCard, personCard, weaponCard);
	}

	public void doMove() {
		getCell().setOccupied(false);
		Board board = Board.getInstance();
		Set<BoardCell> targets = board.getTargets();
		for (BoardCell cell : targets) {
			if (cell.isRoom()) {
				Card roomCard = null;
				for (Card c : board.getRoomCards()) {
					if (c.getCardName().equals(board.getRoom(cell).getName())) {
						roomCard = c;
					}
				}
				if (seenCards.contains(roomCard) || hand.contains(roomCard)) {
					targets.remove(cell);
				}
				Board.getInstance().getCurrentPlayer().setPos(cell);
				return;
			}
		}
		BoardCell[] targetsArr = targets.toArray(new BoardCell[0]);
		BoardCell targetCell = targetsArr[new Random().nextInt(targetsArr.length)];
		board.getCurrentPlayer().setPos(targetCell);
		targetCell.setOccupied(true);
		ClueGame.update();
	}
}
