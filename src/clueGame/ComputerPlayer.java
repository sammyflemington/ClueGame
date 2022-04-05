// Author: Eliot Edwards and Sammy Flemington

package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/*
 * ComputerPlayer class
 * 
 * Keeps track of computer players and their components
 */
public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column, false);
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}

	// Randomly selects target for computer player to move to
	// FUTURE: might want to use AI for this eventually instead of random
	public BoardCell selectTarget() {
		// TODO: LOOK BACK AT THIS!
		BoardCell cell = getCell(row, column);
		Set<BoardCell> targets = cell.getTargets();

		// https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
		int choice = new Random().nextInt(targets.size());	// get random integer in index range of targets list
		int index = 0;

		for (BoardCell c : targets) {
			if (index == choice) return c;
			index++;
		}

		return null; // should never get here
	}

	// Makes a suggestion when computer player is inside a room
	// Passes in the full deck of cards and the room that the computer player is in
	public Solution makeSuggestion(ArrayList<Card> deck, Card room) {

		ArrayList<Card> seenCards = getSeen();
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		ArrayList<Card> unseenPeople = new ArrayList<Card>();
		
		// For each card in the seen cards
		for (Card card : seenCards) {

			// For each card in a full deck
			for (Card c : deck) {

				// Add weapons and people to unseen lists
				if (c.getCardType() == CardType.WEAPON) {
					unseenWeapons.add(c);
				} else if (card.getCardType() == CardType.PERSON) {
					unseenPeople.add(c);
				}
				
				// If weapon or person card has been seen, remove it from unseen lists
				if (c.equals(card) && c.getCardType() == CardType.WEAPON) {
					unseenWeapons.remove(c);
				} else if (c.equals(card) && c.getCardType() == CardType.PERSON) {
					unseenPeople.remove(c);
				}
								
			}

		}

		int weapon = 0;
		// If only one weapon is not seen, it's selected
		if (unseenWeapons.size() != 1) {
			// https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
			weapon = new Random().nextInt(unseenWeapons.size());
		}
		
		int person = 0;
		// If only one person is not seen, it's selected
		if (unseenPeople.size() != 1) {
			// https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
			person = new Random().nextInt(unseenPeople.size());
		}
	
		return new Solution(room, unseenPeople.get(person), unseenWeapons.get(weapon));
	}


	public BoardCell getCell(int row, int column) {
		BoardCell cell = new BoardCell(row, column);
		return cell;
	}

}
