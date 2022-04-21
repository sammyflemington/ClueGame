// Authors: Eliot Edwards and Sammy Flemington

package clueGame;

/*
 * Card class:
 * 
 * Keeps track of cards
 * 9 rooms, 6 players, and 6 weapons
 */
public class Card {

	private String cardName;
	private CardType cardType;
	
	public Card(String name, CardType type) {
		cardName = name;
		cardType = type;
	}
	
	@Override
	public boolean equals(Object target) {
		if (target instanceof Card) {//
			Card card = (Card) target;
			if (card.toString().equals(this.toString()) &&
				card.getCardType() == this.getCardType()) {
				return true;
			}
			return false;
		}
		return false;
	}

	public CardType getCardType() {
		return cardType;
	}
	
	@Override
	public String toString() {
		return cardName;
	}
	
}
