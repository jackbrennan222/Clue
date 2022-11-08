package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {
	public HumanPlayer(String name, Color color) {
		super(name, color);
	}
	
	public void updateHand(Card card) {
		// TODO Auto-generated method stub
		hand.add(card);
	}

	public void updateSeen(Card card) {
		return;
	}
}
