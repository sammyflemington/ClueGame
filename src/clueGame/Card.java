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
	
	public boolean equals(Card target) {
		// TODO: Not yet implemented
		return false;
	}

	public CardType getCardType() {
		return cardType;
	}
	
	
}
