package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import clueGame.*;

public class GameSolutionTest {
    private Board board;

    @BeforeAll
    void fixedSetup() {
        Card 


    }

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
    void testAccusation() {
        Solution solution = board.getTheAnswer();
        Card solRoom = solution.getRoom();
        Card solPerson = solution.getPerson();
        Card solWeapon = solution.getWeapon();
        Boolean accusation = board.checkAccusation(solRoom, solPerson, solWeapon);
        assertTrue(accusation);
        ArrayList<Card> deck = board.getDeck();
        Card altRoom = deck.get(0).equals(solRoom) ? deck.get(1) : deck.get(0);
        accusation = board.checkAccusation(altRoom, solPerson, solWeapon);
        assertFalse(accusation);
        Card altPerson = deck.get(15).equals(solPerson) ? deck.get(16) : deck.get(15);
        accusation = board.checkAccusation(solRoom, altPerson, solWeapon);
        assertFalse(accusation);
        Card altWeapon = deck.get(9).equals(solPerson) ? deck.get(10) : deck.get(9);
        accusation = board.checkAccusation(solRoom, solPerson, altWeapon);
        assertFalse(accusation);
    }

    @Test 
    void testDisproveSuggestion() {
        // TODO: come back not done idk how to do this
        Player player = board.getPlayers().get(0);
        ArrayList<Card> playerHand = player.getHand();
        ArrayList<Card> deck = board.getDeck();
        Boolean inHand = false;
        Card room = deck.get(0);
        Card person = deck.get(15);
        Card weapon = deck.get(9);
        for (Card c : playerHand) {
            if (c.getCardType() == CardType.ROOM) {
                room = c;
                inHand = true;
            } else if (c.getCardType() == CardType.PERSON) {
                person = c;
                inHand = true;
            } else if (c.getCardType() == CardType.WEAPON) {
                weapon = c;
                inHand = true;
            }
        }
        Card disproves = board.handleSuggestion(room, person, weapon);

        if (inHand) {
            assertTrue(playerHand.contains(disproves));
        } else {
            assertEquals(disproves, null);
        }
    }

    @Test

}
