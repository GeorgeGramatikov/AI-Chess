public class King extends Piece{
    boolean castle = true;
    King(int column, int row, String color){
        super.type = "K";
        super.column = column;
        super.row = row;
        this.color = color;
    }
}
