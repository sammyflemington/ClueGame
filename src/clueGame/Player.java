// Authors: Eliot Edwards and Sammy Flemington

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/*
 * Player class (abstract) :
 * 
 * Keeps track of both human and computer players
 * Each player has a name, color, and location(row, column) on the board
 */
public abstract class Player {

	protected String name;
	protected Color color;
	//private Color color;
	protected int row, column;
	protected boolean isHuman = false;
	protected ArrayList<Card> hand;
	protected ArrayList<Card> seenCards;
	protected Board board;
	private boolean turnOver;
	
	// Default...
	public Player() {
		this.name = "None";
		this.color = Color.WHITE;
		this.row = 0;
		this.column = 0;
		this.isHuman = false;
	}
	
	public boolean isTurnOver() {
		return turnOver;
	}

	public void setTurnOver(boolean turnOver) {
		this.turnOver = turnOver;
	}

	public Player(String name, Color color, int row, int column, boolean isHuman) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		this.isHuman = isHuman;	
		hand = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
	}

	public void draw(Graphics g, int w, int h) {
		int x = w * column;
		int y = h * row;
		int offset = 0;
		int offsetTotal = 0;
		int margin = 10;
		// if we're in a room, offset 
		if (board.getCell(row, column).isRoomCenter()) {
			ArrayList<Player> occ = board.getOccupancy(board.getRoom(board.getCell(row, column)));
			offset = occ.indexOf(this) * margin;
			offsetTotal = - margin * (occ.size() / 2);
		}
		
		g.setColor(color);
		g.fillOval(x + offset + offsetTotal, y, w, h);
	}
	
	public void setBoard(Board b) {
		board = b;
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void updateSeen(Card card) {
		// avoid adding duplicates
		if (!seenCards.contains(card)) {
			seenCards.add(card);
		}
		
	}

	public String getName() {
		return name;
	}

	public boolean isHuman() {
		return isHuman;
	}

	public void setHuman(boolean isHuman) {
		this.isHuman = isHuman;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public ArrayList<Card> getSeen() {
		return seenCards;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void clearHand() {
		hand = new ArrayList<Card>();
	}
	
	public Color getColor() {
		return color;
	}
	
	public abstract Solution makeSuggestion(ArrayList<Card> deck);
	public abstract Solution checkForAccusation(ArrayList<Card> deck);
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> suggested = suggestion.getCards();
		Random rand = new Random();
		
		
		ArrayList<Card> matches = new ArrayList<Card>();
		for (Card c : hand) {
			if (suggested.contains(c)) {
				matches.add(c);
			}
		}
		// If we have any matches, pick a random one. Otherwise, return null.  
		if (matches.size() >= 1) 
			return matches.get(rand.nextInt(matches.size()));
		else
			return null;
	}
	
	public void moveTo(int r, int c) {
		board.getCell(row, column).setOccupied(false);
		if (board.getCell(row, column).isRoomCenter()) {
			// remove from occupancy of this room
			board.removeOccupancy(board.getRoom(board.getCell(row, column)), this);
		}
		row = r;
		column = c;
		board.getCell(row, column).setOccupied(true);
		if (board.getCell(row, column).isRoomCenter()) {
			// add to occupancy of this room
			board.addOccupancy(board.getRoom(board.getCell(row, column)), this);
		}
		
	}
	
	public BoardCell getCell() {
		return board.getCell(row, column);
	}

	protected abstract BoardCell selectTarget(int roll);
	
}
