package tests;
import java.util.Set;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {

	TestBoard board;
	
	@BeforeEach // So it runs before each test
	public void setUp() {
		board = new TestBoard();		
	}
	

	public void testAdjacency() {
		
		TestBoardCell cell = board.get(0,0);
		Set<TestBoardCell> testList = cell.getAdjlist();
	
	}
	
}
