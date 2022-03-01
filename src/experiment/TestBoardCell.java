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
	
	Set<TestBoardCell> getAdjList(){
		return adjList;
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
>>>>>>> 21a697e9d381eecf9b5c7a5111fee8ccddc99341
}

