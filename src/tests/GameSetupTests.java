package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import clueGame.*;

public class GameSetupTests {
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
    void playersTest() {
        ArrayList<Player> players = board.getPlayers();
        assertEquals(players.size(), 6);
        
        int humans = 0;
        int robots = 0;
        
        for (Player p : players) {
        	if (p.getClass() == new HumanPlayer().getClass()) { humans++; }
        	else if (p.getClass() == new ComputerPlayer().getClass()) { robots++; }
        }
        
        assertEquals(humans, 1);
        assertEquals(robots, 5);
    }

    @Test
    void createDeckTest() {
        ArrayList<Card> deck = board.getDeck();
        int rooms = 0;
        int weapons = 0;
        int people = 0;

        for (Card c : deck) {
            CardType type = c.getCardType();
            if (type == CardType.ROOM) { rooms++; }
            else if (type == CardType.WEAPON) { weapons++; }
            else if (type == CardType.PERSON) { people++; }
        }
        
        assertEquals(deck.size(), 21);
        assertEquals(rooms, 9);
        assertEquals(weapons, 6);
        assertEquals(people, 6);
    }

    @Test
    void testSolution() {
        Solution ans = board.getTheAnswer();
        assertTrue(ans.getPerson().equals(new Card("", CardType.PERSON)));
        assertTrue(ans.getRoom().equals(new Card("", CardType.ROOM)));
        assertTrue(ans.getWeapon().equals(new Card("", CardType.WEAPON)));
    }
    
    @Test
    void testPlayerHands() {
    	ArrayList<Player> players = board.getPlayers();
        assertEquals(players.size(), 6);
        for (int i = 0; i < players.size() - 1; i++) {
            Player p1 = players.get(i);
            Player p2 = players.get(i + 1);
            ArrayList<Card> p1Hand = p1.getHand();
            ArrayList<Card> p2Hand = p2.getHand();
            for (Card c : p1Hand) {
                assertFalse(p2Hand.contains(c));
            }
        }
    }
}
