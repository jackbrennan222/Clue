package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	private int row,col;
	private ArrayList<Card> hand;
	
	public Player(String name, Color color) {
		hand = new ArrayList<Card>();
	}
	
	public abstract void updateHand();

	public ArrayList<Card> getHand() {
		return hand;
	}
}
