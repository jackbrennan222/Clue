package clueGame;

import java.awt.Color;
import java.awt.Graphics2D;
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
	private boolean turnOver;
	
	public Player(String name, Color color) {
		hand = new ArrayList<Card>();
		seenCards = new HashSet<Card>();
		this.name = name;
		this.color = color;
		this.turnOver = false;
	}

	/**
	 * a function to draw players in the GUI
	 * 
	 * @param g
	 * @param cellWidth
	 * @param cellHeight
	 */
	public void draw(Graphics2D g, int cellWidth, int cellHeight) {
		g.setColor(color);
		g.fillOval(col * cellWidth + (int)(cellWidth * 0.125), row * cellHeight + (int)(cellHeight * 0.25), (int)(cellWidth * 0.25), (int)(cellHeight * 0.5));
		g.setColor(Color.BLACK);
		g.drawOval(col * cellWidth + (int)(cellWidth * 0.125), row * cellHeight + (int)(cellHeight * 0.25), (int)(cellWidth * 0.25), (int)(cellHeight * 0.5));
		g.setColor(color);
		g.fillOval(col * cellWidth + (int)(cellWidth * 0.67), row * cellHeight + (int)(cellHeight * 0.5), (int)(cellWidth * 0.33), (int)(cellHeight * 0.5));
		g.fillOval(col * cellWidth + (int)(cellWidth * 0.25), row * cellHeight + (int)(cellHeight * 0.5), (int)(cellWidth * 0.33), (int)(cellHeight * 0.5));
		g.setColor(Color.BLACK);
		g.drawOval(col * cellWidth + (int)(cellWidth * 0.25), row * cellHeight + (int)(cellHeight * 0.5), (int)(cellWidth * 0.33), (int)(cellHeight * 0.5));
		g.drawOval(col * cellWidth + (int)(cellWidth * 0.67), row * cellHeight + (int)(cellHeight * 0.5), (int)(cellWidth * 0.33), (int)(cellHeight * 0.5));
		g.setColor(color);
		g.fillOval(col * cellWidth + ((int)(cellWidth * 0.25)), row * cellHeight, ((int)(cellWidth * 0.75)), (int)(cellHeight * 0.4));
		g.setColor(Color.BLACK);
		g.drawOval(col * cellWidth + ((int)(cellWidth * 0.25)), row * cellHeight, ((int)(cellWidth * 0.75)), (int)(cellHeight * 0.4));
		g.setColor(color);
		g.fillOval(col * cellWidth + ((int)(cellWidth * 0.25)), row * cellHeight + (int)(cellHeight * 0.4), ((int)(cellWidth * 0.75)), (int)(cellHeight * 0.4));
		g.setColor(Color.BLACK);
		g.drawOval(col * cellWidth + ((int)(cellWidth * 0.25)), row * cellHeight + (int)(cellHeight * 0.4), ((int)(cellWidth * 0.75)), (int)(cellHeight * 0.4));
		g.setColor(color);		
		g.fillRect(col * cellWidth + ((int)(cellWidth * 0.25)), row * cellHeight + (int)(cellHeight * 0.2), (int)(cellWidth * 0.75), (int)(cellHeight * 0.4));
		g.setColor(Color.BLACK);
		g.drawLine(col * cellWidth + ((int)(cellWidth * 0.25)), row * cellHeight + (int)(cellHeight * 0.2), col * cellWidth + ((int)(cellWidth * 0.25)), row * cellHeight + (int)(cellHeight * 0.8));
		g.drawLine((col + 1) * cellWidth, row * cellHeight + (int)(cellHeight * 0.2), (col + 1) * cellWidth, row * cellHeight + (int)(cellHeight * 0.6));
		g.setColor(color);
		g.fillRect(col * cellWidth + (int)(cellWidth * 0.25) + 1, row * cellHeight + (int)(cellHeight * 0.5) + 1, (int)(cellWidth * 0.33) - 1, (int)(cellHeight * 0.3));
		g.fillRect(col * cellWidth + (int)(cellWidth * 0.67) + 1, row * cellHeight + (int)(cellHeight * 0.5) + 1, (int)(cellWidth * 0.33) - 1, (int)(cellHeight * 0.3));
		g.setColor(Color.CYAN);
		g.fillOval(col * cellWidth + ((int)(cellWidth * 0.545)), row * cellHeight + (int)(cellHeight * 0.2), (int)(cellWidth * 0.5), (int)(cellHeight * 0.4));
		g.setColor(Color.BLACK);
		g.drawOval(col * cellWidth + ((int)(cellWidth * 0.545)), row * cellHeight + (int)(cellHeight * 0.2), (int)(cellWidth * 0.5), (int)(cellHeight * 0.4));
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

	public abstract void updateHand(Card card);

	public void updateSeen(Card card) {
		seenCards.add(card);
		ClueGame.update();
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

	public void setPos(BoardCell cell) {
		this.row = cell.getRow();
		this.col = cell.getCol();
	}
	
	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public boolean isTurnOver() {
		return turnOver;
	}

	public BoardCell getCell() {
		return Board.getInstance().getCell(row, col);
	}

	public void setTurnOver(boolean b) {
		turnOver = b;
	}

	public abstract void doAccusation();

	public abstract Solution createSuggestion();
}
