package clueGame;

import java.awt.Color;

public abstract class Player {
	private String name;
	private Color color;
	private int row,col;
	private Card[] hand;
	
	public Player() {
		hand = new Card[3];
	}
	
	public abstract void updateHand();
}
