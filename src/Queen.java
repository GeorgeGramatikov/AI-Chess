public class Queen extends Piece{
    public Queen(int column, int row, String color){
        super.type = "Q";
        super.column = column;
        super.row = row;
        this.color = color;
    }
}
