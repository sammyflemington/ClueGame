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
	
	@Test
	public void testAdjacency() {
		
		TestBoardCell cell = board.get(0,0);
		Set<TestBoardCell> testList = cell.getAdjlist();
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertEquals(2, testList.size());
		
	}
	
	
}
