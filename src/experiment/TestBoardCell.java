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

	
	void setRoom(boolean b) {
		isRoom = b;
	}
	
	boolean isRoom() {
		return isRoom;
	}
	
	void setOccupied(boolean b) {
		isOccupied = b;
	}
	
	boolean getOccupied() {
		return isOccupied;
	}

	public Set<TestBoardCell> getAdjList() {
		
		
		return adjList;
	}

}

