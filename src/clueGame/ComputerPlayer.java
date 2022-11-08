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
	
	public void updateHand(Card card) {
		hand.add(card);
	}

	public Solution createSuggestion() {
		BoardCell currCell = Board.getInstance().getCell(row, col);
		Room currRoom = Board.getInstance().getRoom(currCell);
		Card currRoomCard = new Card(currRoom.getName(), CardType.ROOM);

		ArrayList<Card> unseenPersons = new ArrayList<Card>();
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		Card suggestionPerson = new Card();	
		Card suggestionWeapon = new Card();
		for (Card c : Board.getInstance().getDeck()) {
			if (!seenCards.contains(c) && !hand.contains(c)) {
				if (c.getCardType() == CardType.PERSON) { unseenPersons.add(c); }
				if (c.getCardType() == CardType.WEAPON) { unseenWeapons.add(c); }
			}
		}
		Random rand = new Random();
		int randIndex = rand.nextInt(unseenPersons.size());
		suggestionPerson = unseenPersons.get(randIndex);
		randIndex = rand.nextInt(unseenWeapons.size());
		suggestionWeapon = unseenWeapons.get(randIndex);
		return new Solution(currRoomCard, suggestionPerson, suggestionWeapon);
	}

	public BoardCell selectTarget() {
		Set<BoardCell> targets = Board.getInstance().getTargets();
		ArrayList<BoardCell> roomTargets = new ArrayList<>();
		for (BoardCell bc : targets) {
			if (bc.isRoom() && !visitedRooms.contains(bc)) {
				roomTargets.add(bc);
			}
		}
		Random rand = new Random();
		if (!roomTargets.isEmpty()) {
			return roomTargets.get(rand.nextInt(roomTargets.size()));
		}
		BoardCell[] targetsArr = new BoardCell[targets.size()];
		targets.toArray(targetsArr);
		return targetsArr[rand.nextInt(targets.size())];
	}

	public void setVisitedRooms(Set<BoardCell> visitedRooms) {
		this.visitedRooms = visitedRooms;
	}
}
