package experiment;

import java.util.Set;
import java.util.HashSet;

public class TestBoardCell {
	private int row;
	private int col;
	private boolean isRoom = true;
	private boolean isOccupied = false;
	private Set<TestBoardCell> adjList = new HashSet<TestBoardCell> ();
	
	public TestBoardCell(int r, int c) {
		row = r;
		col = c;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}

	
	public void setRoom(boolean b) {
		isRoom = b;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean b) {
		isOccupied = b;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}

	@Override
	public String toString() {
		return "[" + Integer.toString(row) + ", " + Integer.toString(col) + "]";
	}
	public Set<TestBoardCell> getAdjList() {
		
		
		return adjList;
	}

}

