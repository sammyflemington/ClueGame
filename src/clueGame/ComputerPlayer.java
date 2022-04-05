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
		BoardCell cell = board.getCell(row, column);
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
	public Solution makeSuggestion(ArrayList<Card> deck) {
		Card room = new Card(board.getRoom(board.getCell(row, column).getInitial()).getName(), CardType.ROOM);
		ArrayList<Card> seenCards = getSeen();
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		ArrayList<Card> unseenPeople = new ArrayList<Card>();
		Random rand = new Random();
		// For each card in the seen cards
		for (Card c : deck) {
			if (c.getCardType() == CardType.WEAPON) {
				unseenWeapons.add(c);
			} else if (c.getCardType() == CardType.PERSON) {
				unseenPeople.add(c);
			}
		}
		
		for (Card c : seenCards) {
			unseenWeapons.remove(c);
			unseenPeople.remove(c);
		}
	
		//return new Solution(room, unseenPeople.get(rand.nextInt(unseenPeople.size())), unseenWeapons.get(rand.nextInt(unseenWeapons.size())));
		int weapon = 0;
		// If only one weapon is not seen, it's selected
		if (unseenWeapons.size() > 1) {
			// https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
			weapon = rand.nextInt(unseenWeapons.size());
		}
		
		int person = 0;
		// If only one person is not seen, it's selected
		if (unseenPeople.size() > 1) {
			// https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
			person = rand.nextInt(unseenPeople.size());
		}
	
		return new Solution(room, unseenPeople.get(person), unseenWeapons.get(weapon));
	}

}
