package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	protected int row,col;
	protected ArrayList<Card> hand;
	protected Set<Card> seenCards;
	
	public Player(String name, Color color) {
		hand = new ArrayList<Card>();
		seenCards = new HashSet<Card>();
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public abstract void updateHand(Card card);

	public void updateSeen(Card card) {
		seenCards.add(card);
	}

	public Card disproveSuggestion(Card room, Card person, Card weapon) {
		ArrayList<Card> matches = new ArrayList<>();
		if (hand.contains(room)) {
			matches.add(room);
		}
		if (hand.contains(person)) {
			matches.add(person);
		}
		if (hand.contains(weapon)) {
			matches.add(weapon);
		}
		if (matches.isEmpty()) {
			return null;
		}
		Random rand = new Random();
		return matches.get(rand.nextInt(matches.size()));
	}

	public void setPos(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public String getName() {
		return name;
	}
}
