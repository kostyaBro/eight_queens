package usue;

public class Main {

    public static void main(String[] args) {
        ChessField chessField = new ChessField(8);
        chessField.generateRandomTable();
        chessField.showBoard(false);
        System.out.println();
        chessField.showBoard(true);
    }

}
