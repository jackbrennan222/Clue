package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color) {
		super(name, color);
	}
	
	@Override
	public void updateHand(Card card) {
		// TODO Auto-generated method stub
		hand.add(card);
	}

}
