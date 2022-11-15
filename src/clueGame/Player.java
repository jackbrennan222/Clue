package clueGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
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
		this.name = name;
		this.color = color;
	}

	/**
	 * a function to draw players in the GUI
	 * 
	 * @param g
	 * @param cellWidth
	 * @param cellHeight
	 * @param r
	 */
	public void draw(Graphics2D g, int cellWidth, int cellHeight, int r) {
		// background color
		g.setColor(color);
		g.fillOval(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
		// outline
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1.5f));
		g.drawOval(col * cellWidth, row * cellHeight, cellWidth, cellHeight);
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

	public Color getColor() {
		return color;
	}
}
