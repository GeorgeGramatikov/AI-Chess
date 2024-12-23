import java.util.Scanner;

public class Main {

    public Main(){
        Scanner str = new Scanner(System.in);
        Board b = new Board();
        // Moves move = new Moves(b.board[1][1], b.board, 2, 1);
        for(; ; ) {
            b.print();
            System.out.println("___________________________________");

            //curr.x and curr.y
            System.out.println("Select Piece to move y-axis");
            int currVertical = str.nextInt();
            System.out.println("Select Piece to move x-axis");
            int currHorizontal = str.nextInt();

            //newX and newY
            System.out.println("Move Piece to new y-axis? ");
            int newVertical = str.nextInt();
            System.out.println("Move Piece to new x-axis? ");
            int newHorizontal = str.nextInt();

            new Moves(currVertical, currHorizontal, b.board, newVertical, newHorizontal);
        }


    }

    public static void main(String[] args){
        Main m = new Main();
    }


}
