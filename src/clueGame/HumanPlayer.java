// Authors: Eliot Edwards and Sammy Flemington

package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

/*
 * HumanPlayer class
 * 
 * Keeps track of the human player and their components
 */
public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, Color color, int row, int column) {
		super(name, color, row, column, true);
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		setTurnOver(false);
	}

	@Override
	protected BoardCell selectTarget(int roll) {
		// TODO Auto-generated method stub
		return null;
	}

}
