package clueGame;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClueGame extends JFrame {

    public static final int CELL_SIZE = 24;
    public static final int MENU_SIZE = 150;
    private static Board board;
    private int numRows,numCols,rowDim,colDim;

    public ClueGame() {
        initialize();

        numRows = board.getNumRows();
        numCols = board.getNumColumns();

        rowDim = numRows * CELL_SIZE;
        colDim = numCols * CELL_SIZE;
        
        setSize(colDim + MENU_SIZE, rowDim + MENU_SIZE);
        setLayout(new GridBagLayout());

        GameControlPanel gcp = new GameControlPanel();
        PlayerHandPanel php = new PlayerHandPanel();
        add(gcp, BorderLayout.SOUTH);
        add(php, BorderLayout.LINE_END);
    }

    private static void initialize() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }

    public static void main(String[] args) {
        ClueGame clue = new ClueGame();
        clue.setVisible(true);
        clue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
