// Author: Eliot Edwards and Sammy Flemington

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/*
 * ComputerPlayer class
 * 
 * Keeps track of computer players and their components
 */
public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column, false);
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		setTurnOver(true);
	}

	// Randomly selects target for computer player to move to
	// FUTURE: might want to use AI for this eventually instead of random
	public BoardCell selectTarget(int roll) {

		Random rand = new Random();
		BoardCell cell = board.getCell(row, column);
		board.calcTargets(cell, roll);
		Set<BoardCell> targets = board.getTargets();
		// https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
		for (BoardCell tcell : targets) {
			if (tcell.isRoomCenter()) {
				// Check if we have seen this room
				//boolean flag = false;
				if (!getSeen().contains(new Card(board.getRoom(tcell).getName(), CardType.ROOM))) {
					return tcell;
				}
//				for (Card card : getSeen()) {
//					if (card.equals(new Card(board.getRoom(tcell).getName(), CardType.ROOM)))
//						flag = true;
//				}
//				// prioritize moving into a room we haven't seen
//				if (!flag)
//					return tcell;
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

		int weapon = 0;
		if (unseenWeapons.size() > 1) {
			weapon = rand.nextInt(unseenWeapons.size());
		}

		int person = 0;
		if (unseenPeople.size() > 1) {
			person = rand.nextInt(unseenPeople.size());
		}

		return new Solution(room, unseenPeople.get(person), unseenWeapons.get(weapon));
	}

	public Solution checkForAccusation(ArrayList<Card> deck) {
		// if this player has solved the mystery, make an accusation!
		ArrayList<Card> unseen = new ArrayList<Card>();
		for (Card c : getSeen()) {
			if (!deck.contains(c)) {
				unseen.add(c);
			}
		}
		if (unseen.size() == 3) {
			Card no = new Card("sdkljfasd;kfj", CardType.PERSON);
			Card person = no, room = no, weapon= no;
			for (Card c : unseen) {
				switch(c.getCardType()) {
				case ROOM:
					room = c;
					break;
				case PERSON:
					person = c;
					break;
					
				case WEAPON:
					weapon = c;
					break;
				}
			}
			if (room != no && person != no && weapon != no)
				return new Solution(room, person, weapon);
		}
		return null;
	}
}
