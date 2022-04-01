// Authors: Eliot Edwards and Sammy Flemington

package clueGame;

import java.util.ArrayList;

/*
 * Solution class:
 * 
 * Holds the room, person, and weapon card that are the solution to the current game
 */
public class Solution {

	// Only need 1 copy of each for solution class
	private Card room;
	private Card person;
	private Card weapon;
	private ArrayList<Card> solutionCards;
	
	public Solution(Card room, Card person, Card weapon) {
		this.room = room;
		this.person = person;
		this.weapon = weapon;
		
		solutionCards = new ArrayList<Card>();
		solutionCards.add(room);
		solutionCards.add(person);
		solutionCards.add(weapon);
	}
	
	// Getters and Setters
	
	public Card getRoom() {
		return room;
	}

	public void setRoom(Card room) {
		this.room = room;
	}

	public Card getPerson() {
		return person;
	}

	public void setPerson(Card person) {
		this.person = person;
	}

	public Card getWeapon() {
		return weapon;
	}

	public void setWeapon(Card weapon) {
		this.weapon = weapon;
	}
	
	public ArrayList<Card> getCards() {
		return solutionCards;
	}
}
