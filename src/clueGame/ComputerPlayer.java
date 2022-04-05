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
	public BoardCell selectTarget(int roll) {
		// TODO: LOOK BACK AT THIS!
		Random rand = new Random();
		BoardCell cell = board.getCell(row, column);
		board.calcTargets(cell, roll);
		Set<BoardCell> targets = board.getTargets();
		// https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
		for (BoardCell tcell : targets) {
			if (tcell.isRoomCenter()) {
				// Check if we have seen this room
				for (Card card : getSeen()) {
					if (card.equals(new Card(board.getRoom(tcell).getName(), CardType.ROOM)))
						continue;
				}
				// prioritize moving into a room we haven't seen
				return tcell;
			}
		}
		int r = rand.nextInt(targets.size());
		int i = 0;
		for (BoardCell c : targets) {
			if (i == r) {
				return c;
			}
			i++;
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

		for (Card c : deck) {
			boolean flag = false;
			for (Card card : seenCards) {
				if (card.equals(c)) {
					flag = true;
				}
			}
			if (!flag) {
				if (c.getCardType() == CardType.WEAPON) {
					unseenWeapons.add(c);
				} else if (c.getCardType() == CardType.PERSON) {
					unseenPeople.add(c);
				}
			}
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
