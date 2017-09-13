package usue;

import com.sun.istack.internal.NotNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kostyaBro
 */
public class ChessFieldNew {

    private static final char QUEEN = '\u265b'; //♛
    private static final int  QUEEN_INDEX = -1;
//    private static final char BLACK_SQUARE = '\u2b1b'; //⬛
    private static final char BLACK_SQUARE = '\u2b1c'; //⬛
    private static final int  BLACK_SQUARE_INDEX = -2;
    private static final char WHITE_SQUARE = '\u2b1c'; //⬜
    private static final int  WHITE_SQUARE_INDEX = -3;

    private final int lineAndRow;
    private int[][] board;

    public ChessFieldNew(int lineAndRow) {
        this.lineAndRow = lineAndRow;
        generateEmptyTable();
    }

    public ChessFieldNew() {
        this.lineAndRow = 8;
        generateEmptyTable();
    }

    private void generateEmptyTable() {
        board = new int[lineAndRow][lineAndRow];
        for (int[] row : board) for (int cell : row) cell = 0;
    }

    private void generateRandomTable() {
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

    public void startAlgorithm() {
        startAlgorithm(new Pair(new SecureRandom().nextInt(8), new SecureRandom().nextInt(8)));
    }

    public List<Pair> startAlgorithm(@NotNull Pair startPosition) {
        int[] beatsLine = new int[lineAndRow];//int - auto = 0
        int[] beatsRow = new int[lineAndRow];
        List<Pair> output;
        placeQueen(startPosition);
        for (int i = 1; i < lineAndRow; i++) {
            showBoardFriendly();
            System.out.println('\n');
            countBeatsArray(beatsLine, beatsRow);

            List<Pair> cellForJob = new ArrayList<>();
            foundCleanCells(beatsLine, beatsRow, cellForJob);
            if (cellForJob.size() == 1) {
                placeQueen(cellForJob.get(0));
                continue;
            }
            cleanJobListByNewBeatsCell(cellForJob);
            if (cellForJob.size() == 1) {
                placeQueen(cellForJob.get(0));
                continue;
            }
            cleanJobListByOldBeatsCell(cellForJob);
            if (cellForJob.size() == 1) {
                placeQueen(cellForJob.get(0));
                continue;
            } else {
                // TODO: 12.09.17
                placeQueen(cellForJob.get(0));
                continue;
            }
        }
        return null;
    }

    private void cleanJobListByOldBeatsCell(List<Pair> cellForJob) {
        int maxCount = 0;
        for (int i = 0; i < cellForJob.size(); i++) {
            for (int line_ = 0; line_ < lineAndRow; line_++) {
                for (int row_ = 0; row_ < lineAndRow; row_++) {
                    if (board[line_][row_] == BLACK_SQUARE_INDEX &&
                            (row_ == cellForJob.get(i).row || line_ == cellForJob.get(i).line ||
                                    Math.abs(line_ - cellForJob.get(i).line) == Math.abs(row_ - cellForJob.get(i).row)) )
                        cellForJob.get(i).oldBeats++;
                }
            }
        }
//        System.out.println(Arrays.toString(cellForJob.toArray()));
        for (Pair cell : cellForJob)
            if (maxCount < cell.oldBeats)
                maxCount = cell.oldBeats;
//        System.out.println("maxCount = " + maxCount);
        List<Pair> removePairList = new ArrayList<>();
        for (int i = 0; i < cellForJob.size(); i++) {
            if (cellForJob.get(i).oldBeats < maxCount) {
//                System.out.println(cellForJob.get(i).oldBeats + " > " + maxCount);
                removePairList.add(cellForJob.get(i));
            } else {
//                System.out.println(cellForJob.get(i).oldBeats + " <= " + maxCount);
            }
        }
        cellForJob.removeAll(removePairList);
//        System.out.println(Arrays.toString(cellForJob.toArray()));
    }

    private void cleanJobListByNewBeatsCell(List<Pair> cellForJob) {
//        System.out.println("cleanJobListByNewBeatsCell");
        int minCount = lineAndRow * lineAndRow;
        for (int i = 0; i < cellForJob.size(); i++) {
            for (int line_ = 0; line_ < lineAndRow; line_++) {
                for (int row_ = 0; row_ < lineAndRow; row_++) {
                    if (board[line_][row_] == 0 &&
                            (row_ == cellForJob.get(i).row || line_ == cellForJob.get(i).line ||
                                    Math.abs(line_ - cellForJob.get(i).line) == Math.abs(row_ - cellForJob.get(i).row)) )
                        cellForJob.get(i).newBeats++;
                }
            }
        }
//        System.out.println(Arrays.toString(cellForJob.toArray()));
        for (Pair cell : cellForJob)
            if (minCount > cell.newBeats)
                minCount = cell.newBeats;
//        System.out.println("minCount = " + minCount);
        List<Pair> removePairList = new ArrayList<>();
        for (int i = 0; i < cellForJob.size(); i++) {
            if (cellForJob.get(i).newBeats > minCount) {
//                System.out.println(cellForJob.get(i).newBeats + " > " + minCount);
                removePairList.add(cellForJob.get(i));
            } else {
//                System.out.println(cellForJob.get(i).newBeats + " <= " + minCount);
            }
        }
        cellForJob.removeAll(removePairList);
//        System.out.println(Arrays.toString(cellForJob.toArray()));
    }

    private void foundCleanCells(@NotNull int[] beatsLine, @NotNull int[] beatsRow, @NotNull List<Pair> cellForJob) {
        //find cell
//        System.out.println("foundCleanCells");
        int minBeatsCountLine = lineAndRow;
        int minBeatsCountRow = lineAndRow;
        for (int j = 0 ; j < lineAndRow; j++) {
            if (beatsLine[j] != 0 && minBeatsCountLine > beatsLine[j])
                minBeatsCountLine = beatsLine[j];
            if (beatsRow[j] != 0 && minBeatsCountRow > beatsRow[j])
                minBeatsCountRow = beatsRow[j];
        }
//        System.out.println("minBeatsCountRow = " + minBeatsCountRow);
//        System.out.println("minBeatsCountLine = " + minBeatsCountLine);
//        showBoardFriendly();
        for (int line_ = 0; line_ < lineAndRow; line_++) {
            for (int row_ = 0; row_ < lineAndRow; row_++) {
//                System.out.println("beatsLine[row_] == minBeatsCountLine && beatsRow[line_] == minBeatsCountRow && " +
//                        "board[line_][row_] == 0");
//                System.out.println(beatsLine[row_] + " == " + minBeatsCountLine + " && "
//                                + beatsRow[line_] + " == " + minBeatsCountRow + " && " +
//                        board[line_][row_] + " == 0 ");
                if (beatsLine[row_] == minBeatsCountLine && beatsRow[line_] == minBeatsCountRow &&
                        board[line_][row_] == 0)
                    cellForJob.add(new Pair(line_, row_));
            }
        }
        if (cellForJob.isEmpty())
            for (int row_ = 0; row_ < lineAndRow; row_++)
                if (beatsLine[row_] == minBeatsCountLine)
                    for (int line_ = 0; line_ < lineAndRow; line_++)
                        if (board[line_][row_] == 0)
                            cellForJob.add(new Pair(line_, row_));
//        System.out.println(Arrays.toString(cellForJob.toArray()));
    }

    private void countBeatsArray(int[] beatsLine, int[] beatsRow) {
        for (int line_ = 0; line_ < lineAndRow; line_++) {
            int counterForBeatsRow = 0;
            for (int row_ = 0; row_ < lineAndRow; row_++) {
                if (board[line_][row_] == 0)
                    counterForBeatsRow++;
            }
            beatsRow[line_] = counterForBeatsRow;
        }
        for (int row_ = 0; row_ < lineAndRow; row_++) {
            int counterForBeatsRow = 0;
            for (int line_ = 0; line_ < lineAndRow; line_++) {
                if (board[line_][row_] == 0)
                    counterForBeatsRow++;
            }
            beatsLine[row_] = counterForBeatsRow;
        }
//        System.out.println("beatsLine[] = " + Arrays.toString(beatsLine));
//        System.out.println("beatsRow[] = " + Arrays.toString(beatsRow));
    }

    private void placeQueen(Pair queen) {
        board[queen.line][queen.row] = QUEEN_INDEX;
        for (int line_ = 0; line_ < lineAndRow; line_++)
            for (int row_ = 0; row_ < lineAndRow; row_++)
                if (board[line_][row_] != QUEEN_INDEX &&
                        (row_ == queen.row || line_ == queen.line ||
                                Math.abs(line_ - queen.line) == Math.abs(row_ - queen.row)) )
                    board[line_][row_] = BLACK_SQUARE_INDEX;
    }

    public static class Pair {
        public int line;
        public int row;
        public int newBeats;
        public int oldBeats;
        public Pair(int line, int row) {
            this.line = line;
            this.row = row;
            newBeats = 0;
            oldBeats = 0;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "line=" + line +
                    ", row=" + row +
                    ", newBeats=" + newBeats +
                    ", oldBeats=" + oldBeats +
                    '}';
        }
    }
}
