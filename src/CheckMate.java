import java.util.ArrayList;

public class CheckMate {

    ArrayList<Integer> vMoves = new ArrayList<>();
    ArrayList<Integer> hMoves = new ArrayList<>();
    boolean result = false;


    public CheckMate(Piece king, Piece[][] board){
        result = isCheckMate(king.column, king.row, board);
    }

    public boolean isCheckMate(int v, int h, Piece[][] board){
        int numPositions = 0;
        int possibleMoves = 0;
        for(int i=v-1; i<=v+1; i++){
            if(!(i<0) && !(i>7)){
                for(int j=h-1; j<=h+1; j++){
                    if(!(j<0) && !(j>7)){
                        if(!board[i][j].color.equalsIgnoreCase(board[v][h].color)){
                            Piece temp = board[i][j];
                            board[i][j] = board[v][h];
                            board[i][j].column=i;
                            board[i][j].row=j;
                            board[v][h] = new Piece();
                            board[v][h].type = "E";
                            board[v][h].color = "E";
                            CheckKing legal = new CheckKing(board);
                            if(!legal.check){
                                possibleMoves++;
                                System.out.println("Possible move");
                                System.out.println(board[i][j].type+ " v: " + i + " h: " + j);
                                vMoves.add(i);
                                hMoves.add(j);
                            }
                            board[v][h] = board[i][j];
                            board[v][h].row = h;
                            board[v][h].column = v;
                            board[i][j] = temp;
                        }


                    }
                }
            }

        }
        if(possibleMoves>0  ){
            return true;
        }
        return false;
    }
}
