public class Piece {
   
    private int offset; //start of the piece in buffer
    private int length; //length of the piece in buffer

    //Constructor
    public Piece(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }

    public void setLength (int length) {
        this.length = length;
    }

    public int offset() {
        return offset;
    }
    public int length() {
        return length;
    }

    public void setOffset(int index) {
        this.offset = index;
    }
    
    public Piece[] splitPiece(int splitOffset) {
        Piece prevPiece = new Piece(offset, splitOffset - offset - 1);
        Piece newPiece = new Piece(splitOffset, length);
        Piece[] split = {prevPiece, newPiece};
        return split;
    }
}
    
 