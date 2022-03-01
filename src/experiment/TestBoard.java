package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] board = new TestBoardCell[4][4];
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	public TestBoard() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = new TestBoardCell(i, j);
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
