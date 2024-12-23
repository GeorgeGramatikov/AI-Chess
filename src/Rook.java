public class Rook extends Piece {
    boolean castle = true;
    Rook(int column, int row, String color){
        super.type = "R";
        super.column = column;
        super.row = row;
        this.color = color;
    }

}
