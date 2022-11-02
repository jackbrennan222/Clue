package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card() {
		
	}
	
	public Card(String name, CardType cardType) {
		this.cardName = name;
		this.cardType = cardType;
	}
	
	public boolean equals(Card target) {
		return false;
	}
	
	public CardType getCardType() {
		return cardType;
	}
}
