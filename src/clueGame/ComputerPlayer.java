package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, Color color) {
		super(name, color);
	}
	
	public void updateHand(Card card) {
		// TODO Auto-generated method stub
		hand.add(card);
	}

	public void updateSeen(Card card) {
		return;
	}

	public Card disproveSuggestion() {
		return new Card();
	}

	public Solution createSuggestion() {
		return new Solution(null, null, null);
	}

	public BoardCell selectTarget() {
		return new BoardCell(0, 0);
	}
}
