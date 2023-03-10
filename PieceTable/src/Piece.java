public class Piece {
   
    private int offset; //start of the piece in buffer
    int length; //length of the piece in buffer

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
    	if(splitOffset - offset >= 0) {
    		 	Piece newPiece = new Piece(splitOffset, length-splitOffset);
    	        Piece prevPiece = new Piece(offset, splitOffset - offset);
    	        Piece[] split = {prevPiece, newPiece};
    	        return split;
    	}
    	else {
		 	throw new IllegalArgumentException("Cannot have negative split on splitoffset:" + splitOffset +" offset:"+ offset);
    	}
    	
    }


	public void setPiece(Piece newPiece) {
		// TODO Auto-generated method stub
		this.offset = newPiece.offset();
		this.length = newPiece.length();
	}
}
