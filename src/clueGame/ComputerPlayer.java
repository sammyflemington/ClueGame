// Author: Eliot Edwards and Sammy Flemington

package clueGame;

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


}
