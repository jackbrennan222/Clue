package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import clueGame.Board;
import experiment.*;

public class BoardAdjTargetTest {
	private static Board board;
	
	@BeforeEach
	public void setup() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
}
