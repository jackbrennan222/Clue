package clueGame;

public class Card implements Comparable<Card> {
	private String cardName;
	private CardType cardType;
	
	public Card() {
		
	}
	
	public Card(String name, CardType cardType) {
		this.cardName = name;
		this.cardType = cardType;
	}
	
	public boolean equals(Card target) {
		return target.cardName == cardName && target.cardType == cardType;
	}
	
	public CardType getCardType() {
		return cardType;
	}

	@Override
	public int compareTo(Card c) {
		return this.cardType.compareTo(c.getCardType());
	}
}
