
// Authors: Eliot Edwards and Sammy Flemington

package clueGame;
import java.awt.Color;
import java.awt.Graphics;
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
	
	public void drawLabel(Graphics g, int w, int h) {
		int x = labelCell.getCol() * w;
		int y = labelCell.getRow() * h;
		g.setColor(Color.BLUE);
		g.drawString(Board.getInstance().getRoomLabel(labelCell.getInitial()), x, y);
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
