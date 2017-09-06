package usue;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class ChessField {
    private static final String TAG = "ChessField";

    private static final char QUEEN = '\u265b'; //♛
    private static final char BLACK_SQUARE = '\u2b1b'; //⬛
    private static final char WHITE_SQUARE = '\u2b1c'; //⬜

    private ArrayList<Integer> board = new ArrayList<>();

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
                            System.out.print('E');
                }
            }
            System.out.println();
        }
    }

}
