// Author: Eliot Edwards and Sammy Flemington

package clueGame;

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
		
		BoardCell cell = getCell(row, column);
		Set<BoardCell> adjList = cell.getAdjList();
		
		// https://stackoverflow.com/questions/124671/picking-a-random-element-from-a-set
		int choice = new Random().nextInt(adjList.size());	// get random integer in index range of adjList
		int index = 0;
		
		for (BoardCell c : adjList) {
			if (index == choice) return c;
			index++;
		}

		return null; // should never get here
	}
	
	public Solution makeSuggestion() {
		
		
		
		
		return null;
	}
	
	
	public BoardCell getCell(int row, int column) {
		BoardCell cell = new BoardCell(row, column);
		return cell;
	}

}
