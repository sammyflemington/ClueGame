package tests;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import clueGame.Board;
import clueGame.DoorDirection;

public class BoardAdjTargetTest {
	private static int ROWS = 27;
	private static int COLUMNS = 22;
	
	private static Board board;
	
	@BeforeAll
	public static void initialize() {
		// load stuff
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
	}
	
	public void testWalkwaysToAdjacentWalkways() {
		//Walkways only connect to adjacent walkways.
	}
	
	public void testWalkwayDoorConnections() {
		//Walkways with doors will also connect to the room center the door points to.
	}
	
	public void testRoomCenters() {
		//The cell that represents the Room (i.e. connects to walkway) is the cell 
		// with a second character of ‘*’ (no other cells in a room should have adjacencies).
	}
	
	public void testRoomCentersConnections() {
		//Room center cells ONLY connect to 1) door walkways that enter the room and 2) another
		//room center cell if there is a secret passage connecting.
	}
}
