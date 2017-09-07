package usue;

import java.security.SecureRandom;
import java.util.ArrayList;

public class ChessField {
    private static final String TAG = "ChessField";

    private static final char QUEEN = '\u265b'; //♛
    private static final int  QUEEN_INDEX = -1;
    private static final char BLACK_SQUARE = '\u2b1b'; //⬛
    private static final int  BLACK_SQUARE_INDEX = -2;
    private static final char WHITE_SQUARE = '\u2b1c'; //⬜
    private static final int  WHITE_SQUARE_INDEX = -3;

    private final int rowAndLine;
    private ArrayList<ArrayList<Integer>> board = new ArrayList<>();

    public ChessField(int rowAndLine) {
        this.rowAndLine = rowAndLine;
        generateEmptyTable(rowAndLine);
    }

    private void generateEmptyTable(int rowAndLine) {
        for (int i = 0; i < rowAndLine; i++) {
            ArrayList<Integer> emptyLine = new ArrayList<>();
            for (int j = 0; j < rowAndLine; j++)
                emptyLine.add(0);
            board.add(emptyLine);
        }
    }

    public void generateRandomTable() {
        for (int i = 0; i < rowAndLine; i++)
            for (int j = 0; j < rowAndLine; j++)
                board.get(i).set(j, ((new SecureRandom()).nextInt(3) + 1) * -1 );
    }

    public static void showChars() {
        System.out.println("Queen - " + QUEEN);
        System.out.println("Black square (under attack) - " + BLACK_SQUARE);
        System.out.println("White square (not under attack) - " + WHITE_SQUARE);
        System.out.println("Test random board 8x8 :");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                switch ((new SecureRandom()).nextInt(3)) {
                    case 0:
                        System.out.print(QUEEN + " ");
                        break;
                    case 1:
                        System.out.print(BLACK_SQUARE + " ");
                        break;
                    case 2:
                        System.out.print(WHITE_SQUARE + " ");
                        break;
                    default:
                        System.out.print("E ");
                }
            }
            System.out.println();
        }
    }

    public void showBoard(boolean toChar) {
        for (int i = 0; i < rowAndLine; i++) {
            for (int j = 0; j < rowAndLine; j++) {
                if (toChar) {
                    switch (board.get(i).get(j)) {
                        case QUEEN_INDEX:
                            System.out.print(QUEEN + " ");
                            break;
                        case BLACK_SQUARE_INDEX:
                            System.out.print(BLACK_SQUARE + " ");
                            break;
                        case WHITE_SQUARE_INDEX:
                            System.out.print(WHITE_SQUARE + " ");
                            break;
                        case 0:
                            System.out.print("0 ");
                            break;
                        default:
                            System.out.print("E ");
                    }
                } else
                    System.out.print(board.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

}
