import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    Piece[][] board = new Piece[8][8];
    public static final String RED    = "\u001B[31m";
    public static final String GREEN  = "\u001B[32m";
    public static final String NORMAL = "\u001B[0m";


    public Board(){
        //nested for loop creating a 8x8 chess board
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                board[i][j] = new Piece();
                board[i][j].type = "E";
                board[i][j].color = "E";
            }
        }

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++) {
                board[1][j] = new Pawn(1, j, "R", false);
                board[6][j] = new Pawn(6, j, "G", false);
            }

            board[0][0] = new Rook(0,0, "R");
            board[0][7] = new Rook(0,7, "R");

            board[7][7] = new Rook(7,7, "G");
            board[7][0] = new Rook(7,0, "G");

            board[0][6] = new Knight(0, 6, "R");
            board[0][1] = new Knight(0, 1, "R");

            board[7][1] = new Knight(7, 1, "B");
            board[7][6] = new Knight(7, 6, "B");

            board[7][5] = new Bishop(7, 5, "G");
            board[7][2] = new Bishop(7,2, "G");

            board[0][5] = new Bishop(0, 5, "R");
            board[0][2] = new Bishop(0, 2, "R");

            board[0][3] = new Queen(0, 3, "R");
            board[7][3] = new Queen(7, 3, "G");

            board[0][4] = new King(0, 4, "R");
            board[7][4] = new King(7, 4, "G");

        }

    }

    public void print(){
        System.out.println("x");
        for(int i=0; i<8; i++){
            System.out.print(i + " | ");
        }
        System.out.print("y");
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board[i][j].color.equalsIgnoreCase("R")){
                    System.out.print(RED+board[i][j].type + NORMAL);
                } else if(board[i][j].type.equalsIgnoreCase("E")) {
                    System.out.print(NORMAL+board[i][j].type + NORMAL);
                }else{
                    System.out.print(GREEN+board[i][j].type + NORMAL);
                }
                System.out.print(" | " );
            }
            System.out.print(i);
            System.out.println();
        }
    }
}
