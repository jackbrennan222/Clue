package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	private int row,col;
	protected ArrayList<Card> hand;
	private Set<Card> seenCards;
	
	public Player(String name, Color color) {
		hand = new ArrayList<Card>();
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public abstract void updateHand(Card card);

	public abstract void updateSeen(Card card);

	public abstract Card disproveSuggestion();
}
