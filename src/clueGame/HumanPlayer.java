package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {
	public HumanPlayer(String name, Color color) {
		super(name, color);
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}

	public void updateSeen(Card card) {
		return;
	}

	@Override
	public void doAccusation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeSuggestion() {
		// TODO Auto-generated method stub
		
	}

	public void doMove(BoardCell cell) {
		getCell().setOccupied(false);
		setPos(cell);
		cell.setOccupied(true);
	}
}
