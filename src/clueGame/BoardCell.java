package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {

	private int col;
	private int row;
	private char initial;
	
	DoorDirection doorDirection;
	
	private boolean roomLabel;
	private boolean roomCenter;
	private char 	secretPassage;
	
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	public void addAdj(BoardCell cell) {
		// do something
	}
	
}