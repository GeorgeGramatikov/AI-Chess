public class Pawn extends Piece{
    boolean moved = true; //if the pawn has already moved
    boolean lastMove = false;
    Pawn(int column, int row, String color, boolean lastMove){
        super.type = "P";
        super.column = column;
        super.row = row;
        this.color = color;
        this.lastMove = lastMove;
    }
}
