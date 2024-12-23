import java.util.ArrayList;

public class legalMoves {
    ArrayList<aMove> listOfMoves = new ArrayList<aMove>();
    Piece king;
    Piece attacker;

    public legalMoves(Piece[][] board) {
        String type;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                type = board[i][j].type;
                switch (type) {
                    case "B":
                        getBishop(i, j, board);
                        break;
                    case "P":
                        getPawn(i, j, board);
                        break;
                    case "R":
                        getRook(i, j, board);
                        break;
                    case "Q":
                        getQueen(i, j, board);
                        break;
                    case "N":
                        getKnight(i, j, board);
                        break;
                    case "K":
                        getKing(i, j, board);
                        break;
                }
            }
        }
    }

    public void getPawn( int v, int h, Piece[][] board){
        if(board[v][h].color.equalsIgnoreCase("G")){
            if(h!=0 && v!=0 && !board[v-1][h-1].color.equalsIgnoreCase("G")){
                listOfMoves.add(new aMove(v, h, v-1, h-1));
            }
            if(h!=7 && v!=0 && !board[v-1][h+1].color.equalsIgnoreCase("G")){
                listOfMoves.add(new aMove(v, h, v-1, h+1));
            }
        }
        if(board[v][h].color.equalsIgnoreCase("R")) {
            if (h != 0 && v != 7 && !board[v + 1][h - 1].color.equalsIgnoreCase("R")) {
                listOfMoves.add(new aMove(v, h, v + 1, h - 1));
            }

            if (h != 7 && v != 7 && !board[v + 1][h + 1].color.equalsIgnoreCase("R")) {
                listOfMoves.add(new aMove(v, h, v + 1, v + 1));
            }
        }

        Pawn p = (Pawn) board[v][h];

        if(p.color.equalsIgnoreCase("R") && p.moved) {
                listOfMoves.add(new aMove(v, h, v + 2, h));
        }
        if (p.color.equalsIgnoreCase("R")) {
            listOfMoves.add( new aMove(v, h, v+1, h));
        }

        if(p.color.equalsIgnoreCase("G")) {
            listOfMoves.add(new aMove(v, h, v-1, h));
        }
        if(p.color.equalsIgnoreCase("G") && p.moved) {
            listOfMoves.add(new aMove(v, h, v-2, h));
        }
    }

    public void getRook( int v, int h, Piece[][] board){
        //Check Right
        for(int i=h; i<8; i++){
            if(!board[v][i].color.equalsIgnoreCase(board[v][h].color)) {
                if (v != 0 && h != 0 && !board[v][i].color.equalsIgnoreCase(board[v][h].color)) {
                    listOfMoves.add(new aMove(v, h, v, i));
                }
            }
            if(!board[i][h].type.equalsIgnoreCase("E")){
                break;
            }
        }
        //Check Left
        for(int i=h; i>0; i--){
            if(!board[v][i].color.equalsIgnoreCase(board[v][h].color)){ //if it doesn't equal the same color as the piece we are currently on
                if (v != 0 && h != 0 && !board[v][i].color.equalsIgnoreCase(board[v][h].color)) {
                    listOfMoves.add(new aMove(v, h, v, i));
                }
            }
            if(!board[v][i].type.equalsIgnoreCase("E")){
                break;
            }
        }
        //Check Up
        for(int i=v; i>0; i--){
            if(!board[i][h].color.equalsIgnoreCase(board[i][h].color)){ //if it doesn't equal the same color as the piece we are currently on
                if (v != 0 && h != 0 && !board[v][i].color.equalsIgnoreCase(board[i][h].color)) {
                    listOfMoves.add(new aMove(v, h, i, h));
                }
            }
            //If piece in the way
            if(!board[i][h].type.equalsIgnoreCase("E")){
                break;
            }

        }
        //Check Down
        for(int i=v; i<8; i++){
            if(!board[i][h].color.equalsIgnoreCase(board[i][h].color)){ //if it doesn't equal the same color as the piece we are currently on
                if (v != 0 && h != 0 && !board[v][i].color.equalsIgnoreCase(board[i][h].color)) {
                    listOfMoves.add(new aMove(v, h, i, h));
                }
            }
            if(!board[i][h].type.equalsIgnoreCase("E")){
                break;
            }
        }
    }

    public void getBishop(int v, int h, Piece[][] board){
        System.out.println(v + " " +  h);
        for(int i=1; true; i++){
            if(v+i==8 || h+i==8){
                break;
            }
            if(!board[v+i][h+i].color.equalsIgnoreCase(board[v][h].color)){ //if it doesn't equal the same color as the piece we are currently on

                    listOfMoves.add(new aMove(v, h, v+i, h+i));

            }
            if(!board[v+i][h+i].type.equalsIgnoreCase("E")){
                break;
            }
        }

        for(int i=1; true; i++){
            if(v+i==8 || h-i==-1){
                break;
            }
            if(!board[v+i][h-i].color.equalsIgnoreCase(board[v][h].color)){ //if it doesn't equal the same color as the piece we are currently
                    listOfMoves.add(new aMove(v, h, v+i, h-i));

            }
            if(!board[v+i][h-i].type.equalsIgnoreCase("E")){
                break;
            }
        }

        for(int i=1; true; i++){
            if(v-i==-1 || h+i==8){
                break;
            }
            if(!board[v-i][h+i].color.equalsIgnoreCase(board[v][h].color)){ //if it doesn't equal the same color as the piece we are currently o
                    listOfMoves.add(new aMove(v, h, v-i, h+i));
            }
            if(!board[v-i][h+i].type.equalsIgnoreCase("E")){
                break;
            }
        }

        for(int i=1; true; i++){
            if(v-i==-1 || h-i==-1){
                break;
            }

            if(!board[v][h].color.equalsIgnoreCase(board[v-i][h-i].color)){
                listOfMoves.add(new aMove(v, h, v-i, h-i));
            }
            if(!board[v-i][h-i].type.equalsIgnoreCase("E")){
                break;
            }
        }

    }

    public void getQueen(int v, int h, Piece[][] board){
        getRook( v, h, board);
        getBishop(v, h, board);
    }

    public void getKnight(int v, int h, Piece[][] board){
        if (v< 8 && v >= 0 && h< 8 && h>= 0) { //first make sure 'move' wont make knight go off the board
            //Focus on X-Movement
            if (v<6 && h!=0 && board[v+ 2][h- 1].type.equalsIgnoreCase("K")){ //right 2 down 1
                listOfMoves.add(new aMove(v, h,v+2, h-1 ));
            }
            if (v<6 && h!=7 && board[v+ 2][h + 1].type.equalsIgnoreCase("K")){ //right 2 up 1
                listOfMoves.add(new aMove(v, h, v+2, h+1));

            }
            if (v>1 && h!=7 && board[v- 2][h+ 1].type.equalsIgnoreCase("K")){ //left 2 up
                listOfMoves.add(new aMove(v, h, v-2, h+1));
            }
            if (v>1 && h!=0 && board[v - 2][h - 1].type.equalsIgnoreCase("K")){ //left 2 down 1

                listOfMoves.add((new aMove(v, h, v-2, h-1)));
            }
            //Focus on Y-Movement
            if(v!=7 && h<6 && board[h+ 2][v + 1].type.equalsIgnoreCase("K")){ //up 2 right 1
                listOfMoves.add((new aMove(v, h, h+2, v+1)));
            }
            if(v!=0 && h<6 && board[h+ 2][v- 1].type.equalsIgnoreCase("K")){ //up 2 less 1
                listOfMoves.add((new aMove(v, h, h+2, v-1)));
            }
            if(v!=0 && h>1 && board[h - 2][v- 1].type.equalsIgnoreCase("K")){ //down 2 less 1

                listOfMoves.add((new aMove(v, h, h-2, v-1)));
            }
            if( v!=7 && h>1 && board[h - 2][v + 1].type.equalsIgnoreCase("K")){ //down 2 right 1
                listOfMoves.add((new aMove(v, h, h-2, v+1)));
            }
        }
    }

    public void getKing(int v, int h, Piece[][] board){
        for(int i=v-1; i<v+1; i++){
            if(!(i<0) && !(i>7)){
                for(int j=h-1; j<h+1; j++){
                    if(!(j<0) && !(j>7)){
                        if(board[i][j].color.equalsIgnoreCase(board[v][h].color)){
                            listOfMoves.add(new aMove(v, h, i, j));
                        }
                    }
                }
            }

        }

    }

    public void setGlobals(Piece inCheck, Piece offense){
        king = inCheck;
        attacker = offense;
    }

}
