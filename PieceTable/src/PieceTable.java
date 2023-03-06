
import java.util.Arrays;
import java.util.ArrayList;

public class PieceTable {
	private String sequenceBuffer; //stores the Sequence
	private int SequenceLength; // stores the length of the text
	//private String sequenceBuffer; // stores the added text buffer
	private ArrayList<Piece> pieces; // stores the pieces of the text

	// Constructor
	public PieceTable(String sequenceBuffer) {
		this.sequenceBuffer = sequenceBuffer;
		this.SequenceLength = sequenceBuffer.length();
		//this.sequenceBuffer = ""; // initialize as empty
		this.pieces = new ArrayList<>();
		this.pieces.add(new Piece(0, sequenceBuffer.length()));
	}

	public void insert(int newIndex, String newText) {
		if (newIndex > SequenceLength) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		if (newIndex == 0) {
			// Inserting at the beginning of the document
			pieces.add(0, new Piece( 0, newText.length()));
			SequenceLength += newText.length();
			sequenceBuffer = newText + sequenceBuffer;
		}
		if(newIndex == SequenceLength) {
			//Insert at the end of the file
			pieces.add(new Piece(SequenceLength, newText.length()));
			sequenceBuffer += newText;
			SequenceLength += newText.length();
		}
		else {
			//Find the piece that contains the index
			Piece piece = findPiece(newIndex);
	        int splitIndex = newIndex - piece.offset();
	        if(splitIndex > 0 && splitIndex < piece.length()) {
	        	//Update the piece the split pieces at its index
				Piece[] splitPieces = piece.splitPiece(splitIndex);			
				Piece p1 = splitPieces[0];
				Piece p2 = splitPieces[1];
				System.out.println("offset: "  + p1.offset() +   "length: " + p1.length());
				System.out.println("offset: "  + p2.offset() +   "length: " + p2.length());
				piece = splitPieces[0];
				pieces.add(pieces.indexOf(piece) + 1, splitPieces[1]);
				sequenceBuffer += newText;
				SequenceLength += newText.length();				
				Piece newPiece = new Piece(SequenceLength, newText.length());

	        	}
		}
	}

	private Piece findPiece(int index) {
		for (Piece piece : pieces) {
			if (index >= piece.offset() && index < piece.offset() + piece.length()) {
				return piece;
			}
		}
		return null;
	}
	
	public String getText() {
		StringBuilder sb = new StringBuilder();
		int runningTotalLength = 0;
		for (Piece piece : pieces) {
				if(pieces.indexOf(piece) == 0) {
					sb.append(sequenceBuffer.substring(piece.offset(), piece.length()));
					runningTotalLength += piece.length();
				}
				else{
					sb.append(sequenceBuffer.substring(runningTotalLength, piece.length() + runningTotalLength));
					runningTotalLength += piece.length();
				}

		}
		return sb.toString();
		
	}
	
	  public void delete(int index, int deletionLength){ 
	    {
	  
	    	//Quick Cases (delete nothing, delete backwards, out of bounds...):
	    	if (deletionLength == 0)
	    		return;
	    	if (deletionLength < 0)
	    		delete(index - deletionLength, -deletionLength);
	    	if (index < 0)
	    		throw new IndexOutOfBoundsException("Index out of range");
	    	
	    	Piece specialPiece; //in case of node removal to avoid error.
	    	boolean deleteMultiple = false;
	    	int deleteIndex = 0; //starting character index of each individual piece
	    	
	    	for (Piece currentPiece : pieces)
	    	{
	    		int deleteOffset = deleteIndex - index; //offset within currentPiece.
	    		if(deleteIndex + currentPiece.length() >= index) //when you find the correct piece.
	    		{
	    			if(currentPiece.length() == deletionLength && deleteOffset == 0) //CASE: single whole piece to be deleted
	    			{
	    				specialPiece = currentPiece;
	    				break; //remove outside of the loop to avoid concurrent modification error;
	    			}
	    			
	    			else if(deletionLength < currentPiece.length() - deleteIndex)//CASE: Single Piece modification
	    			{
	    				if(deleteOffset == 0) //If delete starts at the beginning
	    				{
	    					currentPiece.setLength(currentPiece.length() - deletionLength);
	    					currentPiece.setOffset(currentPiece.offset() + deletionLength);
	    				}
	    				else if(deletionLength == currentPiece.length() - deleteOffset) //Deleting from the end of a single piece
	    				{
	    					currentPiece.setLength(currentPiece.length() - deletionLength);
	    				}
	    				
	    				else //CASE: split one single array
	    				{
	    					Piece[] splitArray = currentPiece.splitPiece(deleteOffset);
	    					pieces.set(pieces.indexOf(currentPiece), splitArray[0]);
	    					pieces.add(pieces.indexOf(currentPiece) + 1, splitArray[1]);
	    				}
	    			}
	    		}
	    		deleteIndex += currentPiece.length(); //keep deleteIndex updated
	    	}
	    	if(specialPiece != null) { pieces.remove(specialPiece) };
	    	
	    	SequenceLength -= deletionLength;
	    }
	  }


	
  

	// TODO: print text from piecetable
	public void printPieces() {
		System.out.println("Piece Table Contents");
		System.out.println("Offset   Length");
		System.out.println("----------------------------");
		for (Piece p : pieces) {
			String text = "";
				int start = p.offset();
				int end = start + p.length();
				if (start >= sequenceBuffer.length()) {
					// The piece is outside the bounds of the added buffer
					continue;
				}
				if (end > sequenceBuffer.length()) {
					// The piece goes beyond the end of the added buffer
					end = sequenceBuffer.length();
			} 
				if (start >= sequenceBuffer.length()) {
					// The piece is outside the bounds of the original buffer
					continue;
				}
				if (end > sequenceBuffer.length()) {
					// The piece goes beyond the end of the original buffer
					end = sequenceBuffer.length();
				}
				text = sequenceBuffer.substring(start, end);
			System.out.printf("   %-6d   %-6d  %s%n", p.offset(), p.length(), text);
		}
	}
	
	public String getSequence() {
		return (this.sequenceBuffer + " Length: " + this.SequenceLength);
	}
	
	

	public static void main(String[] args) {
		//PieceTable pt = new PieceTable("the quick brown fox" + "jumped over the lazy dog");
		PieceTable pt = new PieceTable("Hello World");
		
		// Test inserting at the beginning
		//pt.insert(0, "111");
		//System.out.println(pt.getText());
		//pt.printPieces();
		System.out.println(pt.getSequence());

		// Test inserting at the end
		//pt.insert(pt.SequenceLength, "222");
		//System.out.println(pt.getText());
		//pt.printPieces();

		// Test inserting multiple times
		pt.insert(5, " Everyone");
		//pt.insert(22, "444");
		//System.out.println(pt.getText());
		pt.printPieces();
		System.out.println(pt.getSequence());
		//System.out.println(pt.getText());
	
	}
}
