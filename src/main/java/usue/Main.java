package usue;

public class Main {

    public static void main(String[] args) {
        ChessField chessField = new ChessField(8);
        chessField.doIt(new ChessField.Cell(0, 0));
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
