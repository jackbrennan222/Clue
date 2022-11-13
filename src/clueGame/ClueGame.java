package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class ClueGame extends JFrame {

    public static final int CELL_SIZE = 24;
    public static final int MENU_SIZE = 150;
    private static Board board;
    private int numRows,numCols,yDim,xDim;
    private static GameControlPanel gcp;
    private static PlayerHandPanel php;

    public ClueGame() {
        initialize();

        numRows = board.getNumRows();
        numCols = board.getNumColumns();

        xDim = numCols * CELL_SIZE;
        yDim = numRows * CELL_SIZE;
        
        setSize(xDim + MENU_SIZE, yDim + MENU_SIZE);
        setLayout(new BorderLayout());

        gcp = new GameControlPanel();
        php = new PlayerHandPanel();
        add(php, BorderLayout.EAST);
        add(gcp, BorderLayout.SOUTH);
        add(board, BorderLayout.CENTER);
    }

    private static void initialize() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    private void update() {
        php.updatePanels();
    }

    public static void main(String[] args) {
        ClueGame clue = new ClueGame();
        clue.setVisible(true);
        clue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clue.update();

        for (Card c : board.getDeck()) {
            board.getPlayers().get(0).seenCards.add(c);
        }
        clue.update();
    }
}
