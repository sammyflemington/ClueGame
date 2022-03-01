package tests;

import org.junit.jupiter.api.BeforeEach;

import experiment.TestBoard;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
}
