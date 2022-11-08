package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.*;

import clueGame.*;

public class GameSetupTests {
    private static Board board;

    @BeforeAll
    static void setup() {
        // get board instance because it's singleton
		board = Board.getInstance();
		// set the proper config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// initialize the board
		board.initialize();
    }

    @Test
    void playersTest() {
        assertEquals(board.getPlayers().size(), 6); // make sure that the proper amount of players are initialized
        
        int humans = 0;
        int robots = 0;
        
        // count the number of human and computer players
        for (Player p : board.getPlayers()) {
        	if (p instanceof HumanPlayer) { humans++; }
        	else if (p instanceof ComputerPlayer) { robots++; }
        }
        
        assertEquals(humans, 1); // make sure right number of humans were initialized
        assertEquals(robots, 5); // make sure right number of computers were initialized
    }

    @Test
    void createDeckTest() {
        int rooms = 0;
        int weapons = 0;
        int people = 0;

        for (Card c : board.getDeck()) {
            CardType type = c.getCardType();
            if (type == CardType.ROOM) { rooms++; }
            else if (type == CardType.WEAPON) { weapons++; }
            else if (type == CardType.PERSON) { people++; }
        }
        
        assertEquals(board.getDeck().size(), 21); // make sure there are right number of cards in the deck
        assertEquals(rooms, 9); // make sure there are the right number of rooms in the deck
        assertEquals(weapons, 6); // make sure there are the right number of weapons in the deck
        assertEquals(people, 6); // make sure there are the right number of people in the deck
    }

    @Test
    void testPlayerHands() {
        HashSet<Card> cards = new HashSet<Card>();
        Player p1 = board.getPlayers().get(0);
        Player p2 = board.getPlayers().get(1);
        Player p3 = board.getPlayers().get(2);

        assertEquals(p1.getHand().size(), 3); // make sure each player has the right number of cards in their hand
        assertEquals(p2.getHand().size(), 3);
        assertEquals(p3.getHand().size(), 3);

        // make sure that none of the players have duplicate cards
        for (Card c : p1.getHand()) {
            assertTrue(!cards.contains(c));
            cards.add(c);
        } 

        for (Card c : p2.getHand()) {
            assertTrue(!cards.contains(c));
            cards.add(c);
        } 

        for (Card c : p3.getHand()) {
            assertTrue(!cards.contains(c));
            cards.add(c);
        } 
    }

    @Test
    void testSolution() {
        // make sure that the solution has the right type of card for each part of the solution
        Solution ans = board.getTheAnswer();
        assertTrue(ans.getPerson().getCardType() == CardType.PERSON);
        assertTrue(ans.getRoom().getCardType() == CardType.ROOM);
        assertTrue(ans.getWeapon().getCardType() == CardType.WEAPON);
    }
}
