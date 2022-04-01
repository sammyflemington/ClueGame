// Authors: Eliot Edwards and Sammy Flemington

package clueGame;

import java.util.ArrayList;

/*
 * Player class (abstract) :
 * 
 * Keeps track of both human and computer players
 * Each player has a name, color, and location(row, column) on the board
 */
public abstract class Player {

	private String name, strColor;
	//private Color color;
	private int row, column;
	private boolean isHuman = false;
	private ArrayList<Card> hand;	// might need different data structure
	
	// Default...
	public Player() {
		this.name = "None";
		this.strColor = "White";
		this.row = 0;
		this.column = 0;
		this.isHuman = false;
	}
	
	public Player(String name, String color, int row, int column, boolean isHuman) {
		super();
		this.name = name;
		this.strColor = color;
		this.row = row;
		this.column = column;
		this.isHuman = isHuman;	
		hand = new ArrayList<Card>();
	}

	public void updateHand(Card card) {
		hand.add(card);
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

	public void setName(String name) {
		this.name = name;
	}
	
}