package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
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
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		// start
		Set<TestBoardCell> visited = new HashSet<TestBoardCell>();
		visited.clear();
		visited.add(startCell);
		calculate(startCell, pathLength - 1, visited);
		
	}
	
	// Does a recursive search of the grid and avoids obstacles. 
	// TODO: No support yet for entering walkways!
	public void calculate(TestBoardCell startCell, int pathLength, Set<TestBoardCell> visited) {
		for (TestBoardCell c : startCell.getAdjList()) {
			if (!visited.contains(c) && !c.getOccupied() && !c.isRoom()) {
				if (pathLength == 0) {
					targets.add(c);
				} else {
					// Create copy of visited list for this branch
					Set<TestBoardCell> v = new HashSet<TestBoardCell>(visited);
					v.add(c);
					calculate(c, pathLength - 1, v);
				}
			}
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return board[row][col];
	}
}
