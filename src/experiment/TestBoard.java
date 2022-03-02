package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		board = new TestBoardCell[ROWS][COLS];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = new TestBoardCell(i, j);
			}
		}
		
		// Calculate adjacencies
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (i > 0) 			board[i][j].addAdjacency(board[i-1][j]); // Left
				if (i < ROWS - 1) 	board[i][j].addAdjacency(board[i+1][j]); // right
				if (j > 0)			board[i][j].addAdjacency(board[i][j-1]); // up
				if (j < COLS - 1) 	board[i][j].addAdjacency(board[i][j+1]); // down
			}
		}
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		// Do nothing
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return board[row][col];
	}
}
