package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class ComputerPlayer extends Player {
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
			} else if (!seenCards.contains(c) && !hand.contains(c)) {
				if (c.getCardType() == CardType.WEAPON) { unseenWeapons.add(c); }
			}
		}
		int randIndex = (int)(Math.random() * (unseenPersons.size()-1));
		suggestionPerson = unseenPersons.get(randIndex);
		randIndex = (int)(Math.random() * (unseenWeapons.size()-1));
		suggestionWeapon = unseenWeapons.get(randIndex);
		return new Solution(currRoomCard, suggestionPerson, suggestionWeapon);
	}

	public BoardCell selectTarget() {
		return new BoardCell(0, 0);
	}
}
