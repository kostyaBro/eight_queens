package usue;

import java.security.SecureRandom;

public class ChessField {
    private static final String TAG = "ChessField";

    private static final char QUEEN = '\u265b'; //♛
    private static final int  QUEEN_INDEX = -1;
    private static final char BLACK_SQUARE = '\u2b1b'; //⬛
    private static final int  BLACK_SQUARE_INDEX = -2;
    private static final char WHITE_SQUARE = '\u2b1c'; //⬜
    private static final int  WHITE_SQUARE_INDEX = -3;

    private final int lineAndRow;
    private int[][] board;

    public ChessField(int lineAndRow) {
        this.lineAndRow = lineAndRow;
        generateEmptyTable();
    }

    private void generateEmptyTable() {
        board = new int[lineAndRow][lineAndRow];
//        for (int[] row : board) for (int cell : row) cell = 0;
    }

    public void generateRandomTable() {
        for (int line = 0; line < lineAndRow; line++)
            for (int row = 0; row < lineAndRow; row++)
                board[line][row] = ((new SecureRandom()).nextInt(3) + 1) * -1;
    }

    public static void showChars() {
        System.out.println("Queen - " + QUEEN);
        System.out.println("Black square (under attack) - " + BLACK_SQUARE);
        System.out.println("White square (not under attack) - " + WHITE_SQUARE);
        System.out.println("Test random board 8x8 :");
        for (int line = 0; line < 8; line++) {
            for (int row = 0; row < 8; row++) {

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

    public void showBoardFriendly() {
        showBoard(true);
    }

    public void showBoard(boolean toChar) {
        for (int[] line : board) {
            for (int cell : line) {
                if (toChar) {
                    switch (cell) {
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
                    System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private void placeQueen(Cell cell) {
        placeQueen(cell.line, cell.row);
    }

    public void placeQueen(int line, int row) {
        board[line][row] = QUEEN_INDEX;
        for (int line_ = 0; line_ < lineAndRow; line_++)
            for (int row_ = 0; row_ < lineAndRow; row_++)
                if (board[line_][row_] != QUEEN_INDEX && (row_ == row || line_ == line || Math.abs(line_ - line) == Math.abs(row_ - row)))
                    board[line_][row_] = BLACK_SQUARE_INDEX;
    }

    public void checkWeight() {
        for (int line = 0; line < lineAndRow; line++) {
            for (int row = 0; row < lineAndRow; row++) {
                if (board[line][row] == QUEEN_INDEX || board[line][row] == BLACK_SQUARE_INDEX) continue;
                int sum = board[line][row];
                for (int line_ = 0; line_ < lineAndRow; line_++) {
                    for (int row_ = 0; row_ < lineAndRow; row_++) {
                        if ((board[line_][row_] == QUEEN_INDEX || board[line_][row_] == BLACK_SQUARE_INDEX) &&
                                (line == line_ || row == row_ || Math.abs(line_ - line) == Math.abs(row_ - row)))
                            sum++;
//                        else
//                            sum++;
                    }
                }
                board[line][row] = sum;
            }
        }
    }

    public Cell findMaxWeight() {
        int weight = 0;
        Cell cell = new Cell(-1, -1);
        for (int line = 0; line < lineAndRow; line++) {
            for (int row = 0; row < lineAndRow; row++) {
                if (board[line][row] > weight && board[line][row] > -1) {
                    weight = board[line][row];
                    cell.line = line;
                    cell.row = row;
                }
            }
        }
        return cell;
    }

    public Cell findMaxWeight(int line) {
        int weight = 0;
        Cell cell = new Cell(-1, -1);
//        for (int line = 0; line < lineAndRow; line++) {
        for (int row = 0; row < lineAndRow; row++) {
            if (board[line][row] > weight && board[line][row] > -1) {
                weight = board[line][row];
                cell.line = line;
                cell.row = row;
            }
        }
//        }
        return cell;
    }


    public void doIt(Cell startPosition) {
        for (int i = 0; i < lineAndRow; i++) {
            placeQueen(startPosition);
            checkWeight();
            startPosition = findMaxWeight();
//            try {
//                startPosition = findMaxWeight(i + 1);
//            } catch (ArrayIndexOutOfBoundsException e) {
//                return;
//            }
            if (startPosition.line == -1 || startPosition.row == -1) {
                System.err.println("Did not work out".toUpperCase());
                return;
            }
            System.out.println();
            showBoard(false);
            System.out.println();
            showBoardFriendly();
        }
    }

    public static class Cell {
        public int line;
        public int row;
        public Cell(int line, int row) {
            this.line = line;
            this.row = row;
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "line=" + line +
                    ", row=" + row +
                    '}';
        }
    }

}
