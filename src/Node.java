import java.util.ArrayList;

public class Node {

    //Used for AI
    Node parent;
    Piece[][] board;
    ArrayList<Node> children = new ArrayList<Node>();
    int value;
    aMove lastMove;

    public Node(Node p, Piece[][] b, int v, ArrayList<Node> c, aMove l){
        children = c;
        value = v;
        board = b;
        lastMove = l;
        parent = p;
    }
}
