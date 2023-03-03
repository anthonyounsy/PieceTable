package PieceTable;


public class Piece {
    private boolean isAdded; //true if the piece is not from original text
    private int offset; //start of the piece in buffer
    private int length; //length of the piece in buffer

    //Constructor
    public Piece(boolean isAdded, int offset, int length) {
        this.isAdded = isAdded;
        this.offset = offset;
        this.length = length;
    }

    public void setLength (int length) {
        this.length = length;
    }
    public boolean isAdded() {
        return isAdded;
    }

    public int offset() {
        return offset;
    }
    public int length() {
        return length;
    }

    public String getOriginalOrAppend() {
        if (this.isAdded()) {
            return "Append";
        }
        else {
            return "Original";
        }
    }

    public void setOffset(int index) {
        this.offset = index;
    }
}
