// Authors: Eliot Edwards and Sammy Flemington

package clueGame;

import java.awt.Color;

/*
 * HumanPlayer class
 * 
 * Keeps track of the human player and their components
 * 
 */
public class HumanPlayer extends Player {
	
	
	public HumanPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column, true);
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}
	
}
