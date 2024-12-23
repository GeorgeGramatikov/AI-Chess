import java.util.ArrayList;

public class Moves {
    int newV; //new y-axis co-ordinate
    int newH; //new x-axis co-ordinate

    boolean moveGreen = false;
    boolean moveRed = false;
    boolean gameEnds = false;

    //AI Component



    Moves(int currVertical, int currHorizontal, Piece[][] board, int newVertical, int newHorizontal){
        System.out.println("int at " + currVertical + " " + currHorizontal );
        Piece curr = board[currVertical][currHorizontal];
        System.out.println("is at" + curr.column + " "  + curr.row);

        newV = newVertical;
        newH = newHorizontal;
        String type = curr.type;
        if ((newV < 8 && newV >= 0 && newH < 8 && newH >= 0)) {

            ////////AI Component////////
            boolean maxColor = true;
            int depth = 0;
            int targetDepth = 5;
            int alpha = Integer.MIN_VALUE;
            int beta = Integer.MAX_VALUE;
            Node root = new Node(null, board,0, new ArrayList<Node>(), null);
            //int depth, boolean maxColor, int targetDepth, int alpha, int beta, Node node
            root.board = board;
            //miniMax(depth, maxColor, targetDepth, alpha, beta, root);
            /////////////////
            switch (type) {
                case "P":
                    pawnMoves((Pawn) curr, board);
                    break;
                case "B":
                    bishopMoves(curr, board);
                    break;
                case "R":
                    rookMoves((Rook) curr, board);
                    break;
                case "Q":
                    queenMoves(curr, board);
                    break;
                case "N":
                    knightMoves(curr, board);
                    break;
                case "K":
                    kingMoves((King) curr, board);
                    break;

            }

            //checks king for CHECKMATE
            /*CheckKing check = new CheckKing(board);
            if (check.check) {
                System.out.println(check.king.column + " " + check.king.row);
                CheckMate checkMate = new CheckMate(check.king, board);
                System.out.println("King is in Check ");
                gameEnds = checkMate.result;
                System.out.println("Game_ENDS = " + gameEnds);
                return;
            }*/
            

            if(curr.color.equalsIgnoreCase(board[newV][newH].color)){ //check to make sure same color
                if(board[newVertical][newHorizontal].type.equalsIgnoreCase("K") && curr.type.equalsIgnoreCase("R")){ //check to make sure same letter
                    if(curr.row == 0) {
                        System.out.println("X: " + curr.column + " Y: " + curr.row);
                        King king = (King) board[newV][newH];
                        Rook rook = (Rook) board[curr.column][curr.row];
                        board[newV][newH-1] = rook; //move rook 1 left
                        board[newV][newH-2] = king; //move king 2 left
                        //change x,y coordinate for king class
                        board[newV][newH-2].column = curr.column;
                        board[newV][newH-2].row = curr.row;

                        //change x,y coordinate for rook
                        board[newV][newH-1].row = curr.row;
                        board[newV][newH-1].column = curr.column;

                        curr = new Piece();
                        curr.color = "E";
                        curr.type = "E";

                        board[newV][newH] = new Piece();
                        board[newV][newH].color = "E";
                        board[newV][newH].type = "E";
                    }
                    if(curr.row == 7) {
                        System.out.println("X: " + curr.column + " Y: " + curr.row);
                        King king = (King) board[newV][newH];
                        Rook rook = (Rook) board[curr.column][curr.row];
                        board[newV][newH+1] = rook; //move rook 1 left
                        board[newV][newH+2] = king; //move king 2 left
                        //change x,y coordinate for king class
                        board[newV][newH+2].column = curr.column;
                        board[newV][newH+2].row = curr.row;

                        //change x,y coordinate for rook
                        board[newV][newH+1].row = curr.row;
                        board[newV][newH+1].column = curr.column;

                        board[currVertical][currHorizontal] = new Piece();
                        board[currVertical][currHorizontal].color = "E";
                        board[currVertical][currHorizontal].type = "E";

                        board[newV][newH] = new Piece();
                        board[newV][newH].color = "E";
                        board[newV][newH].type = "E";
                    }
                }
            }
        }
    }



    ///AI////
    /*
    public Node returnP(Piece p, int depth, boolean maxColor, int alpha, int beta, Node iterate) {
        //Piece p = current position
        //depth = how many moves ahead we want to search
        //maxColor = boolean (if true then greens turn)

        if (depth == 0 || gameEnds == true) { //if game is over at the current position or no moves for current piece;
           // return ;
        }
        else{
            miniMax(p, depth, maxColor, alpha, beta, iterate);
        }
        return depth;//Fix
    }

    public Node miniMax(Piece p, int depth, boolean maxColor, int alpha, int beta, Node iterate){
        Node eval = null;

        legalMoves legal = new legalMoves(iterate.board);
        ArrayList<aMove> children = legal.listOfMoves;


        if (maxColor == true){ //if its the players turn
            int maxEval = Integer.MIN_VALUE;
            for(int i = 0; i < children.size(); i++) { //all children in current position; i++){
                Node temp = cloneNode(iterate);//creating a clone of the actual board
                //assign the current location and new location (to variables)
                int curV = children.get(i).currV;
                int curH = children.get(i).currH;
                int nextV = children.get(i).nextV;
                int nextH = children.get(i).nextH;
                // assign the current location and new location (to tempB
                temp.board[nextV][nextH] = temp.board[curH][curV];
                temp.board[nextV][nextH].column = nextV;
                temp.board[nextV][nextH].row = nextH;
                temp.board[curV][curH] = new Piece();
                temp.board[curV][curH].color = "E";
                temp.board[curV][curH].type = "E";
                Node child = new Node(temp.board, 0, new ArrayList<Node>(), children.get(i));

                /*eval = returnP(p, depth - 1, false, alpha, beta, child);
                ///////////////////////////////
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
                return maxEval;
            }

        }
        else {
            //int minEval = Integer.MAX_VALUE;

            for (int i = 0; i < children.size(); i++) { //all children in current position; j++){
                System.out.println(children.get(i).nextH + " " + children.get(i).nextV);

                Node temp = cloneNode(iterate);//creating a clone of the actuall board
                //assign the current location and new location (to variables)
                int curV = children.get(i).currV;
                int curH = children.get(i).currH;
                int nextV = children.get(i).nextV;
                int nextH = children.get(i).nextH;
                // assign the current location and new location (to tempB
                temp.board[nextV][nextH] = temp.board[curH][curV];
                temp.board[nextV][nextH].column = nextV;
                temp.board[nextV][nextH].row = nextH;
                temp.board[curV][curH] = new Piece();
                temp.board[curV][curH].color = "E";
                temp.board[curV][curH].type = "E";
                Node child = new Node(temp.board, 0, new ArrayList<Node>(), children.get(i));

                /*eval = returnP(p, depth - 1, true, alpha, beta, child);
                minEval = min(minEval, eval);
                beta = min(beta, eval);

                if (beta <= alpha) {
                    break;
                }
                return minEval;
            }
        }
        return eval;
    }
    */

    /*public int miniMax(int depth, boolean maxColor, int targetDepth, int alpha, int beta, Node node ){
        if(depth == targetDepth){
            return node.value;
        }

        legalMoves legal = new legalMoves(node.board);
        ArrayList<aMove> legalMoves = legal.listOfMoves;
        int[] values = new int[legalMoves.size()];
        for(int i=0; i<values.length; i++){
            Node temp = cloneNode(node);//creating a clone of the actuall board
            //assign the current location and new location (to variables)
            int curV = legalMoves.get(i).currV;
            int curH = legalMoves.get(i).currH;
            int nextV = legalMoves.get(i).nextV;
            int nextH = legalMoves.get(i).nextH;
            // assign the current location and new location (to tempB
            temp.board[nextV][nextH] = temp.board[curH][curV];
            temp.board[nextV][nextH].column = nextV;
            temp.board[nextV][nextH].row = nextH;
            temp.board[curV][curH] = new Piece();
            temp.board[curV][curH].color = "E";
            temp.board[curV][curH].type = "E";
            values[i] = evalBoard(temp.board);
            Node child = new Node(node, temp.board, values[i], new ArrayList<Node>(), legalMoves.get(i));
            node.children.add(child);
        }

        if(maxColor == true) { //get max only if its players turn
            if (value > alpha) { //get the max value
                alpha = value;
            }

            if (alpha >= beta) { //prunning
                return alpha;
            }
        }
        maxColor = false;
        return alpha; //return the max value alpha
    }

    public int min(int depth, boolean maxColor, int targetDepth, int alpha, int beta, Node node){


        if(value <= alpha){ //get the min value
            alpha = value;

        }

        if(alpha >= beta){ //prunning
            return beta;
        }
        return beta; //return the max value alpha
    }

    /*public int min(int ab, int eval){
        if(ab<eval){
            eval = ab;
        }
        return eval;
    }

    public int max(int ab, int eval){
        if(ab>eval){
            eval = ab;
        }
        return eval;
    }*/

    public void pawnMoves(Pawn p, Piece[][] board) {

        EnPassant(p, board);
        pawnPromotion(p, board);
        pawnAttack(p,board);
        moveGreen = false;
        moveRed = false;
            //let it move 2 forward
            if (newV == p.column + 2 && newH == p.row && p.moved == true) { //check if pawn has moved
                board[p.column + 2][p.row] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.row = newH;
                p.column = newV;
                ((Pawn) p).lastMove = true;
                p.moved = false;
            } else {
                if (newV == p.column + 1 && newH == p.row) {
                    board[p.column + 1][p.row] = board[p.column][p.row];
                    board[p.column][p.row] = new Piece();
                    board[p.column][p.row].type = "E";
                    board[p.column][p.row].color = "E";
                    p.row = newH;
                    p.column = newV;
                    p.moved = false;
                }
            }
        //let it move 1 forward(only)
        if (newV == p.column - 1 && newH == p.row) {
            board[p.column - 1][p.row] = board[p.column][p.row];
            board[p.column][p.row] = new Piece();
            board[p.column][p.row].type = "E";
            board[p.column][p.row].color = "E";
            p.column = newV;
            p.row = newH;
            p.moved = false;
        }
        if (newV == p.column - 2 && newH == p.row && p.moved == true) {
            board[p.column - 2][p.row] = board[p.column][p.row];
            board[p.column][p.row] = new Piece();
            board[p.column][p.row].type = "E";
            board[p.column][p.row].color = "E";
            p.row = newH;
            p.column = newV;
            ((Pawn) p).lastMove = true;
            p.moved = false;
        }
    }

    public void EnPassant(Piece p, Piece[][] board){

        Piece leftP = board[p.column][p.row - 1];
        Piece rightP = board[p.column][p.row + 1];

        //Green Does En Passant
        for(int i = 0; i < 8; i++){
            if(p.color.equals("G") && ((Pawn) p).lastMove == true){ //if our green pawn is located row 4 and any of the columns
                ((Pawn) p).lastMove = false;
                System.out.println(leftP.color);
                if(leftP.color.equals("R")) { //if the piece to the left is red it means we can Enpassant

                    board[newV][newH] = board[p.column][p.row]; //put current piece to new location
                    board[p.column][p.row] = new Piece();
                    board[p.column][p.row].type = "E";
                    board[p.column][p.row].color = "E";

                    board[p.column][p.row-1] = new Piece(); //remove the red piece and create a new type E piece there
                    board[p.column][p.row-1].type = "E";
                    board[p.column][p.row-1].color = "E";

                    p.row = newH;
                    p.column = newV;
                    break;
                }
                if(rightP.color.equals("R")) {

                    board[newV][newH] = board[p.column][p.row]; //put current piece to new location
                    board[p.column][p.row] = new Piece();
                    board[p.column][p.row].type = "E";
                    board[p.column][p.row].color = "E";

                    board[p.column][p.row+1] = new Piece(); //remove the red piece and create a new type E piece there
                    board[p.column][p.row+1].type = "E";
                    board[p.column][p.row+1].color = "E";

                    p.row = newH;
                    p.column = newV;
                    break;
                }
            }
        }

        //Red Does En passant
        for(int i = 0; i < 8; i++){
            if(p.color.equals("R") && ((Pawn) p).lastMove == true){ //if our green pawn is located row 4 and any of the columns
                ((Pawn) p).lastMove = false;
                System.out.println(leftP.color);
                if(leftP.color.equals("G")) { //if the piece to the left is red it means we can Enpassant

                    board[newV][newH] = board[p.column][p.row]; //put current piece to new location
                    board[p.column][p.row] = new Piece();
                    board[p.column][p.row].type = "E";
                    board[p.column][p.row].color = "E";

                    board[p.column][p.row-1] = new Piece(); //remove the red piece and create a new type E piece there
                    board[p.column][p.row-1].type = "E";
                    board[p.column][p.row-1].color = "E";

                    p.row = newH;
                    p.column = newV;
                    break;
                }
                if(rightP.color.equals("G")) {

                    board[newV][newH] = board[p.column][p.row]; //put current piece to new location
                    board[p.column][p.row] = new Piece();
                    board[p.column][p.row].type = "E";
                    board[p.column][p.row].color = "E";

                    board[p.column][p.row+1] = new Piece(); //remove the red piece and create a new type E piece there
                    board[p.column][p.row+1].type = "E";
                    board[p.column][p.row+1].color = "E";

                    p.row = newH;
                    p.column = newV;
                    break;
                }
            }
        }
    }

    public void pawnPromotion(Piece p,Piece board[][]){
        if(p.color.equals("G")){
            for(int i = 0; i < 8; i++) { //check each row index using i
                //&& board[newV][newH].color.equals("E")
                if(board[newV][newH] == board[0][i] ){ //if the new position is equal to the last row of the board
                    board[newV][newH] =  new Queen(newH, newV, "G");
                    board[p.column][p.row] = new Piece();
                    board[p.column][p.row].type = "E";
                    board[p.column][p.row].color = "E";
                    p.row = newH;
                    p.column = newV;
                }
            }
        }

        if(p.color.equals("R")){
            for(int i = 0; i < 8; i++) { //check each row index using i
                if(board[newV][newH] == board[7][i]){ //if the new position is equal to the last row of the board
                    board[newV][newH] =  new Queen(newH, newV, "R");
                    board[p.column][p.row] = new Piece();
                    board[p.column][p.row].type = "E";
                    board[p.column][p.row].color = "E";
                    p.row = newH;
                    p.column = newV;
                }
            }
        }
    }

    public void pawnAttack(Piece p,Piece board[][]){

        //Attack top right
        if(p.color.equals("G")) {
            moveGreen = true;
            if (p.row + 1 == newH && p.column - 1 == newV) { //attack top right
                if(board[newV][newH].color.equals("R")){
                    board[newV][newH] = board[p.column][p.row]; //put the current piece to new location
                    board[p.column][p.row] = new Piece(); //make a new piece of nothing
                    board[p.column][p.row].type = "E"; // insert the 'E' at that new piece(empty)
                    board[p.column][p.row].color = "E";
                    p.row = newH;
                    p.column = newV;
                }
            }
            //Attack top left
            if (p.row - 1 == newH && p.column - 1 == newV) { //attack top left
                if(board[newV][newH].color.equals("R")){
                    board[newV][newH] = board[p.column][p.row]; //put the current piece to new location
                    board[p.column][p.row] = new Piece(); //make a new piece of nothing
                    board[p.column][p.row].type = "E"; // insert the 'E' at that new piece(empty)
                    board[p.column][p.row].color = "E";
                    p.row = newH;
                    p.column = newV;
                }
            }
        }
        //bottom right(red side) so technically top right
        if(p.color.equals("R")){
            moveRed = true;
            if (p.row + 1 == newH && p.column + 1 == newV) { //attack bottom right
                if(board[newV][newH].color.equals("G")){
                    board[newV][newH] = board[p.column][p.row]; //put the current piece to new location
                    board[p.column][p.row] = new Piece(); //make a new piece of nothing
                    board[p.column][p.row].type = "E"; // insert the 'E' at that new piece(empty)
                    board[p.column][p.row].color = "E";
                    p.row = newH;
                    p.column = newV;
                }
            }
        }

        //bottom left(red side)
        if(p.color.equals("R")){
            moveRed = true;
            if (p.row - 1 == newH && p.column + 1 == newV) { //attack bottom right
                if(board[newV][newH].color.equals("G")){
                    board[newV][newH] = board[p.column][p.row]; //put the current piece to new location
                    board[p.column][p.row] = new Piece(); //make a new piece of nothing
                    board[p.column][p.row].type = "E"; // insert the 'E' at that new piece(empty)
                    board[p.column][p.row].color = "E";
                    p.row = newH;
                    p.column = newV;
                }
            }
        }
    }


    public void bishopMoves(Piece p, Piece[][] board){

        if(!checkDiag(p, board)){
            System.out.println("HERE");
            return;
        }

        //Create move
        double firstD = Math.abs(p.column - newV);
        double secondD = Math.abs(p.row - newH);
        if(firstD == secondD){ //Check if this move is possible for a bishop
            board[newV][newH] = board[p.column][p.row]; //put the current piece to new location
            board[p.column][p.row] = new Piece(); //make a new piece of nothing
            board[p.column][p.row].type = "E"; // insert the 'E' at that new piece(empty)
            board[p.column][p.row].color = "E";
            p.row = newH;
            p.column = newV;
        }
    }

    public void rookMoves(Rook p, Piece[][] board) {
        //if we are moving a green piece
        if(p.color.equals("G")) {
            moveGreen = true;
            if (checkGreenPieces(p, board) == true) {
                System.out.println("Moving through your own piece. Re-enter your move");
                return;
            }
        }
        if(p.color.equals("R")) {
            moveRed = true;
            if (checkGreenPieces(p, board) == true) {
                System.out.println("Moving through your own piece. Re-enter your move");
                return;
            }
        }
        moveGreen = false;
        moveRed = false;
        if (board[newV][newH].color.equalsIgnoreCase("G")) {
            System.out.println("Moving to your own piece. Re-enter your move");
            return;
        }
            if (newV == p.column || newH == p.row) {
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.castle = false;
                p.row = newH;
                p.column = newV;
            }
         else{
            if (newV == p.column || newH == p.row) {
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.castle = false;
                p.row = newH;
                p.column = newV;

            }
        }
    }

    public void knightMoves(Piece p, Piece[][] board) {
        //Check if valid destination
        if (p.color.equalsIgnoreCase("G")) {
            System.out.println("Moving to your own piece. Re-enter your move");
            return;
        }

        //Check if this move is possible for a bishop
        if (newV < 8 && newV >= 0 && newH < 8 && newH >= 0) { //first make sure 'move' wont make knight go off the board
            //Focus on X-Movement
            if (newV == p.column + 2  && newH == p.row - 1){ //right 2 down 1
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.row = newH;
                p.column = newV;
            }
            if (newV == p.column + 2 && newH == p.row + 1){ //right 2 up 1
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.row = newH;
                p.column = newV;
            }
            if (newV == p.column - 2 && newH == p.row + 1){ //left 2 up 1
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.row = newH;
                p.column = newV;
            }
            if (newV == p.column - 2 && newH == p.row - 1){ //left 2 down 1
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.row = newH;
                p.column = newV;
            }
            //Focus on Y-Movement
            if(newH == p.row + 2 && newV == p.column + 1){ //up 2 right 1
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.row = newH;
                p.column = newV;
            }
            if(newH == p.row + 2 && newV == p.column - 1){ //up 2 less 1
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.row = newH;
                p.column = newV;
            }
            if(newH == p.row - 2 && newV == p.column - 1){ //down 2 less 1
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.row = newH;
                p.column = newV;
            }
            if(newH == p.row - 2 && newV == p.column + 1){ //down 2 right 1
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
                p.row = newH;
                p.column = newV;
            }
        }
    }
    public void queenMoves(Piece p, Piece[][] board){

        if (p.color.equalsIgnoreCase("G")) {
            System.out.println("Moving to your own piece. Re-enter your move");
            return;
        }
            if (newV == p.column || newH == p.row) {
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";
            }

            else{
            if (newV == p.column || newH == p.row) {
                board[newV][newH] = board[p.column][p.row];
                board[p.column][p.row] = new Piece();
                board[p.column][p.row].type = "E";
                board[p.column][p.row].color = "E";

            }
        }
        bishopMoves(p, board);
    }

    public void kingMoves(King p, Piece[][] board){

        if (p.color.equalsIgnoreCase("G")) {
            System.out.println("Moving to your own piece. Re-enter your move");
            return;
        }

        if ((newV == p.column + 1 || newV == p.column - 1)) {
            board[newV][newH] = board[p.column][p.row];
            board[p.column][p.row] = new Piece();
            board[p.column][p.row].type = "E";
            board[p.column][p.row].color = "E";
            p.castle = false;
        }else if((newH == p.row+1 || newH == p.row -1)){
            board[newV][newH] = board[p.column][p.row];
            board[p.column][p.row] = new Piece();
            board[p.column][p.row].type = "E";
            board[p.column][p.row].color = "E";
            p.castle = false;
        }
    }

    public boolean checkGreenPieces(Piece p, Piece[][] board) {
        //p = current piece
        boolean check = false;
        int colDist = 0;
        int colIndex = 0;
        int tempCol = 0;

        int rowDist = 0;
        int rowIndex = 0;
        int tempRow = 0;


        //get the row and column distances from original piece to new piece
        rowDist = Math.abs(p.row - newH);
        colDist = Math.abs(p.column - newV);

        if (p.column > newV) {
            tempCol = p.column; //if current column is bigger than the newColumn
            colIndex = 1;
        }
        if (p.column < newV) {
            tempCol = newH; //if current column is smaller than the newColumn
            colIndex = 1;
        }
        if(p.row > newH){
            tempRow = p.row; //if current row is bigger than the newRow
            rowIndex = 1;
        }
        if(p.row < newH){
            tempRow = newV; //if current row is bigger than the newRow
            rowIndex = 1;
        }

        /////////////////COLUMN (GREEN)/////////////////////////////
        if (tempCol != 0 && moveGreen == true) { //if the column is moving to a new spot
            for (int i = 0; i < colDist; i++) { //go from 0 to how many spots we are moving in the column
                int newColIndex = tempCol - colIndex;
                //checks each index the current piece takes to the new location to see if it is overlapping any green pieces
                if (board[newColIndex][p.row].color.equalsIgnoreCase("G")) {
                    check = true;
                    colIndex++;
                } else {
                    colIndex++;
                }
            }
        }

        /////////////////COLUMN (RED)/////////////////////////////
        if (tempCol != 0 && moveRed == true) { //if the column is moving to a new spot
            for (int i = 0; i < colDist; i++) { //go from 0 to how many spots we are moving in the column
                int newColIndex = 0;
                //check if going down
                if (p.column < newV) {
                    newColIndex = p.column + colIndex;
                }
                //check if going up
                if (p.column > newV) {
                    newColIndex = p.column - colIndex;
                }
                //checks each index the current piece takes to the new location to see if it is overlapping any green pieces
                if (board[newColIndex][p.row].color.equalsIgnoreCase("R")) {
                    check = true;
                    colIndex++;
                } else {
                    colIndex++;
                }
            }
        }


        /////////////////ROW (GREEN)/////////////////////////////
        if (tempRow != 0 && moveGreen == true) { //if the row is moving to a new spot
            for (int i = 1; i <= rowDist; i++) {
                int newRowIndex;
                if (i == 1) {
                    newRowIndex = rowDist;
                } else {
                    newRowIndex = rowDist - rowIndex;
                }
                //checks each index the current piece passes to the new location to see if it is overlapping any friendly pieces
                if (i != 1) {
                    if (board[p.column][newRowIndex].color.equalsIgnoreCase("G")) {
                        check = true;
                        rowIndex++;
                    } else {
                        rowIndex++;
                    }
                }
            }
        }

        /////////////////ROW (RED)/////////////////////////////
        if (tempRow != 0 && moveRed == true) { //if the row is moving to a new spot
            for (int i = 1; i <= rowDist; i++) {
                int newRowIndex = 0;

                if(p.row > newH) {
                    if (i == 1) {
                        newRowIndex = newH;
                    } else {
                        newRowIndex = newH + rowIndex;
                    }
                }
                if(p.row < newH) {
                    if (i == 1) {
                        newRowIndex = newH;
                    } else {
                        newRowIndex = newH - rowIndex;
                    }
                }
                //checks each index the current piece passes to the new location to see if it is overlapping any friendly pieces
                if (i != 1) {
                    if (board[p.column][newRowIndex].color.equalsIgnoreCase("R")) {
                        check = true;
                        rowIndex++;
                    } else {
                        rowIndex++;
                    }
                }
            }
        }
        return check;
    }

    public boolean checkDiag(Piece p, Piece[][] board){
            int distance = Math.abs(newV- p.column);

            if(newH<p.row && newV<p.column){
                System.out.println("TOP left");
                for( int k = 1; k < distance; k++ ) {
                    System.out.println(board[p.column-k][p.row-k].type + " v: "+(p.column-k) + " h " + (p.row-k));
                    if(!board[p.column-k][p.row-k].type.equalsIgnoreCase("E")){
                        return false;
                    }
                }
            }

            if(newH>p.row && newV<p.column){
                System.out.println("TOP Right");
                for( int k = 1; k < distance; k++ ) {
                    System.out.println(board[p.column-k][p.row-k].type + " v: "+(p.column-k) + " h " + (p.row+k));
                    if(!board[p.column-k][p.row+k].type.equalsIgnoreCase("E")){
                        return false;
                    }
                }
            }

            if(newH<p.row && newV>p.column){
                System.out.println("Bottom left");
                for( int k = 1; k < distance; k++ ) {
                    System.out.println(board[p.column+k][p.row-k].type + " v: "+(p.column+k) + " h " + (p.row-k));
                    if(!board[p.column+k][p.row-k].type.equalsIgnoreCase("E")){
                        return false;
                    }
                }
            }

            if(newH>p.row && newV>p.column){
                System.out.println("Bottom left");
                for( int k = 1; k < distance; k++ ) {
                    System.out.println(board[p.column+k][p.row+k].type + " v: "+(p.column+k) + " h " + (p.row+k));
                    if(!board[p.column+k][p.row+k].type.equalsIgnoreCase("E")){
                        return false;
                    }
                }
            }
            return true;
        }

        public Node cloneNode(Node old){

            Piece[][] oldBoard = old.board;
            Piece[][] newBoard = new Piece[8][8];
            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    if(oldBoard[i][j].type.equalsIgnoreCase("P")){
                        Pawn temp1 = (Pawn) oldBoard[i][j];
                        Pawn temp2 = new Pawn(oldBoard[i][j].column, oldBoard[i][j].row, oldBoard[i][j].color, ((Pawn) oldBoard[i][j]).lastMove);
                        temp2.moved = temp1.moved;
                        newBoard[i][j] = temp2;

                    } else {
                        newBoard[i][j] = new Piece();
                    }
                    newBoard[i][j].type = oldBoard[i][j].type;
                    newBoard[i][j].color = oldBoard[i][j].color;
                    newBoard[i][j].row = oldBoard[i][j].row;
                    newBoard[i][j].column = oldBoard[i][j].column;
                }
            }
            return new Node(old, newBoard, 0, new ArrayList<Node>(), old.lastMove);
        }
        
      /*  public int eval(Piece p){
            int cost = 0;
            if(p.color.equalsIgnoreCase("R")){
            switch(p.type) {
                case "P":
                    cost = 1;
                    break;
                case "B":
                    cost = 3;
                    break;
                case "N":
                    cost = 3;
                    break;
                case "R":
                    cost = 5;
                    break;
                case "Q":
                    cost = 9;
                case "K":
                    cost = 90;
                    break;
            }
            } else{
                switch(p.type) {
                    case "P":
                        cost = -1;
                        break;
                    case "B":
                        cost = -3;
                        break;
                    case "N":
                        cost = -3;
                        break;
                    case "R":
                        cost = -5;
                        break;
                    case "Q":
                        cost = -9;
                    case "K":
                        cost = -90;
                        break;

            }
            }
            return cost;
        }

        public int evalBoard(Piece[][] board){
            int cost = 0;
            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                   cost += eval(board[i][j]);

                }
            }
            return cost;
        }*/
}

