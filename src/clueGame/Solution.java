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
	private static Card room;
	private static Card person;
	private static Card weapon;
	private static ArrayList<Card> solutionCards;
	
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
	
	public static Card getRoom() {
		return room;
	}

	public static void setRoom(Card room) {
		Solution.room = room;
	}

	public static Card getPerson() {
		return person;
	}

	public static void setPerson(Card person) {
		Solution.person = person;
	}

	public static Card getWeapon() {
		return weapon;
	}

	public static void setWeapon(Card weapon) {
		Solution.weapon = weapon;
	}
	
	public static ArrayList<Card> getCards() {
		return solutionCards;
	}
}
