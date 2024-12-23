public class CheckKing {
    Boolean check = false;
    Piece king;
    Piece attacker;

    public CheckKing(Piece[][] board){
        String type;
        String color;
        boolean found = false;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                type = board[i][j].type;
                color = board[i][j].color;
                switch(type){
                    case "B":
                        found = checkBishop(i, j, board);
                        if(found)
                            check=found;
                        break;
                    case "P":
                        found = checkPawn(i, j, board);
                        if(found)
                            check=found;
                        break;
                    case "R":
                        found =  checkRook(i, j, board);
                        if(found)
                            check=found;
                        break;
                    case "Q":
                        found = checkQueen(i, j, board);
                        if(found)
                            check=found;
                        break;
                    case "N":
                        found = checkKnight(i, j, board);
                        if(found)
                            check=found;
                        break;
                }

            }
            if(found){
                check = found;
                break;
            }
        }
    }

    public boolean checkPawn( int v, int h, Piece[][] board){
        if(board[v][h].color.equalsIgnoreCase("G")){
            if(h!=0 && board[v-1][h-1].type.equalsIgnoreCase("K") ){
                king = board[v-1][h-1];
                return true;
            }
            if(h!=7 && board[v-1][h+1].type.equalsIgnoreCase("K")){
                king = board[v-1][h+1];
                return true;
            }
        }
        if(board[v][h].color.equalsIgnoreCase("R")){
            if(h!=0 && board[v+1][h-1].type.equalsIgnoreCase("K")) {
                king = board[v + 1][h - 1];
                return true;
            }
            if(h!=7 && board[v+1][h+1].type.equalsIgnoreCase("K")) {
                king = board[v+1][h+1];
                return true;
            }
        }
        return false;
    }

    public boolean checkRook( int v, int h, Piece[][] board){
        //Check Right
        for(int i=h; i<8; i++){
            if(board[v][i].type.equalsIgnoreCase("K") && !board[v][i].color.equalsIgnoreCase(board[v][h].color)){
                king = board[v][i];
                return true;
            }
            if(!board[v][i].type.equalsIgnoreCase("E")){
                break;
            }
        }
        //Check Left
        for(int i=h; i>0; i--){
            if(board[v][i].type.equalsIgnoreCase("K") &&!board[v][i].color.equalsIgnoreCase(board[v][h].color)){
                king = board[v][i];
                return true;
            }
            if(!board[v][i].type.equalsIgnoreCase("E")){
                break;
            }
        }
        //Check Up
        for(int i=v; i>0; i--){
            if(board[i][h].type.equalsIgnoreCase("K") && !board[v][i].color.equalsIgnoreCase(board[v][h].color)){
                king = board[i][h];
                return true;
            }
            if(!board[i][h].type.equalsIgnoreCase("E")){
                break;
            }
        }
        //Check Down
        for(int i=v; i<8; i++){
            if(board[i][h].type.equalsIgnoreCase("K") &&!board[v][i].color.equalsIgnoreCase(board[v][h].color)){
                king = board[i][h];
                return true;
            }
            if(!board[i][h].type.equalsIgnoreCase("E")){
                break;
            }
        }
        return false;
    }

    public boolean checkBishop(int v, int h, Piece[][] board){
        System.out.println(v + " " +  h);
        for(int i=1; true; i++){
            if(v+i==8 || h+i==8){
                break;
            }
            if(board[v+i][h+i].type.equalsIgnoreCase("K") &&!board[v][h].color.equalsIgnoreCase(board[v+i][h+i].color)){
                king = board[v+i][h+i];
                return true;
            }
            if(!board[v+i][h+i].type.equalsIgnoreCase("E")){
                break;
            }
        }

        for(int i=1; true; i++){
            if(v+i==8 || h-i==-1){
                break;
            }
            if(board[v+i][h-i].type.equalsIgnoreCase("K") &&!board[v][h].color.equalsIgnoreCase(board[v+i][h-i].color)){
                king = board[v+i][h-i];
                return true;
            }
            if(!board[v+i][h-i].type.equalsIgnoreCase("E")){
                break;
            }
        }

        for(int i=1; true; i++){
            if(v-i==-1 || h+i==8){
                break;
            }
            if(board[v-i][h+i].type.equalsIgnoreCase("K") &&!board[v][h].color.equalsIgnoreCase(board[v-i][h+i].color)){
                king = board[v-i][h+i];
                return true;
            }
            if(!board[v-i][h+i].type.equalsIgnoreCase("E")){
                break;
            }
        }

        for(int i=1; true; i++){
            if(v-i==-1 || h-i==-1){
                break;
            }
            System.out.println(board[v-i][h-i].type + " v: " + (v-i) + " h: " + (h-i) );
            if(board[v-i][h-i].type.equalsIgnoreCase("K") &&!board[v][h].color.equalsIgnoreCase(board[v-i][h-i].color)){
                king = board[v-i][h-i];
                return true;
            }
            if(!board[v-i][h-i].type.equalsIgnoreCase("E")){
                break;
            }
        }
        return false;
    }

    public boolean checkQueen(int v, int h, Piece[][] board){
        boolean  r = checkRook( v, h, board);
        boolean b = checkBishop(v, h, board);
        return r | b;
    }

    public boolean checkKnight(int v, int h, Piece[][] board){
        if (v< 8 && v >= 0 && h< 8 && h>= 0) { //first make sure 'move' wont make knight go off the board
            //Focus on X-Movement
            if (v<6 && h!=0 && board[v+ 2][h- 1].type.equalsIgnoreCase("K")){ //right 2 down 1
                king = board[v+ 2][h- 1];
                return true;
            }
            if (v<6 && h!=7 && board[v+ 2][h + 1].type.equalsIgnoreCase("K")){ //right 2 up 1
                king = board[v+ 2][h + 1];
                return true;
            }
            if (v>1 && h!=7 && board[v- 2][h+ 1].type.equalsIgnoreCase("K")){ //left 2 up 1
                king = board[v- 2][h+ 1];
                return true;
            }
            if (v>1 && h!=0 && board[v - 2][h - 1].type.equalsIgnoreCase("K")){ //left 2 down 1
                king = board[v - 2][h - 1];
                return true;
            }
            //Focus on Y-Movement
            if(v!=7 && h<6 && board[h+ 2][v + 1].type.equalsIgnoreCase("K")){ //up 2 right 1
                king = board[h+ 2][v + 1];
                return true;
            }
            if(v!=0 && h<6 && board[h+ 2][v- 1].type.equalsIgnoreCase("K")){ //up 2 less 1
                king = board[h+ 2][v- 1];
                return true;
            }
            if(v!=0 && h>1 && board[h - 2][v- 1].type.equalsIgnoreCase("K")){ //down 2 less 1
                king = board[h-2][v-1];
                return true;
            }
            if( v!=7 && h>1 && board[h - 2][v + 1].type.equalsIgnoreCase("K")){ //down 2 right 1
                king = board[h-2][v+1];
                return true;
            }
        }
        return false;
    }

    public boolean checkKing(int v, int h, Piece[][] board){
        for(int i=v-1; i<v+1; i++){
            if(!(i<0) && !(i>7)){
                for(int j=h-1; j<h+1; j++){
                    if(!(j<0) && !(j>7)){
                        if(board[i][j].type.equalsIgnoreCase("K")){
                            king = board[i][j];
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public void setGlobals(Piece inCheck, Piece offense){
        king = inCheck;
        attacker = offense;
    }

}
