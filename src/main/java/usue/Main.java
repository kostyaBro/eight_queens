package usue;

public class Main {

    public static void main(String[] args) {
        ChessField chessField = new ChessField(8);
        for (int line = 0; line < 8; line++) {
            for (int row = 0; row < 8; row++) {
                if (chessField.doIt(new ChessField.Cell(line, row))) {
                    System.out.println("EEEEEE");
                    return;
                } else {
                    chessField.generateEmptyTable();
                    System.err.println("error");
                }
            }
        }
//        chessField.generateRandomTable();
//        chessField.test_1_1();
//        chessField.placeQueen(3, 4);
//        chessField.checkWeight();
        chessField.showBoard(false);
        System.out.println();
        chessField.showBoardFriendly();
        System.out.println();
//        System.out.println(chessField.findMaxWeight());
    }

}
