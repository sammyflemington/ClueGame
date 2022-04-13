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
	
	// Default...
	public Player() {
		this.name = "None";
		this.color = Color.WHITE;
		this.row = 0;
		this.column = 0;
		this.isHuman = false;
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
		g.setColor(this.color);
		g.fillOval(x, y, w, h);
	}
	public void setBoard(Board b) {
		board = b;
	}
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void updateSeen(Card card) {
		seenCards.add(card);
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
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> suggested = suggestion.getCards();
		Random rand = new Random();
		ArrayList<Card> matches = new ArrayList<Card>();
		for (Card c : hand) {
			for (Card s : suggested) {
				if (c.equals(s)) {
					// We have a match!
					matches.add(s);
				}
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
		row = r;
		column = c;
		board.getCell(row, column).setOccupied(true);
	}
	
}
