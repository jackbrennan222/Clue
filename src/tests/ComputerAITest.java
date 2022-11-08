package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.*;

import clueGame.*;

public class ComputerAITest {
    private Board board;

    @BeforeEach
    void setup() {
        // get board instance because it's singleton
		board = Board.getInstance();
		// set the proper config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// initialize the board
		board.initialize();
    }

    @Test
    void selectTargets() {

    }
    
    @Test
    void createSuggestion() {
        
    }
}
