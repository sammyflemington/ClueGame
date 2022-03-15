
// Author: Sammy Flemington and Eliot Edwards

package clueGame;

/*
 * The Room class allows us to handle
 * the necessary components of each room.
 * This includes its name, label cell, and 
 * center cell.
 */
public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	public Room(BoardCell center, BoardCell label) {
		centerCell = center;
		labelCell = label;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
