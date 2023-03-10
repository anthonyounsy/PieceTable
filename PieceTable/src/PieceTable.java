import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

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
			Piece nextPiece = pieces.get(1);
			nextPiece.setOffset(newText.length());
			SequenceLength += newText.length();
			sequenceBuffer = newText + sequenceBuffer;
			return;
		}
		if(newIndex == SequenceLength) {
			//Insert at the end of the file
			pieces.add(new Piece(SequenceLength, newText.length()));
			sequenceBuffer += newText;
			SequenceLength += newText.length();
			return;
		}
		else {
			//Find the piece that contains the index
			Piece piece = findPiece(newIndex);

	        int splitIndex = newIndex - piece.offset();
	        System.out.println(splitIndex);
	        System.out.println(piece.offset());
	        if(splitIndex > 0 && splitIndex < piece.length()) {
	        	//Update the piece by splitting the pieces at its index
				Piece[] splitPieces = piece.splitPiece(splitIndex);		
			
				//update current piece
				piece.setPiece(splitPieces[0]);
				//update split node		
				pieces.add(pieces.indexOf(piece)+1, new Piece(SequenceLength, newText.length()));
				pieces.add(pieces.indexOf(piece) + 2, splitPieces[1]);
			
				//insert new piece
				SequenceLength += newText.length();
				sequenceBuffer += newText;
				return;

	        }
	        else{
	        	//if new piece is not at the beginning of the text or end, or in the middle of a piece
				pieces.add(pieces.indexOf(piece)-1, new Piece(SequenceLength, newText.length()));
				SequenceLength += newText.length();
				sequenceBuffer += newText;

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
	 
	        for (Piece piece : pieces) { 	
	                    sb.append(sequenceBuffer.substring(piece.offset(),piece.offset()+piece.length()));

	        }
	        return sb.toString();        
	    }
	   
	   public void delete(int index, int deletionLength)
       {
           if (index < 0 || index + deletionLength > SequenceLength)
                 throw new IndexOutOfBoundsException("Index out of range");
           List<Piece> toBeDeleted = new ArrayList<>(); 
           Piece start = findPiece(index);
           int totalNodeLength = -start.offset();
           int savedIndex = pieces.indexOf(start);
           for(int i = pieces.indexOf(start); i < pieces.size(); i++) 
           {
               Piece currentPiece = pieces.get(i);
               totalNodeLength += currentPiece.length();
               toBeDeleted.add(currentPiece);
               if(totalNodeLength >= deletionLength) {
                   //savedIndex = i;
                   break;
               }
           }
           
           pieces.removeAll(toBeDeleted);
           if(totalNodeLength > deletionLength) //EDGECASE: if the end of the delete splits the node at the end of the span.
           {
               Piece endEdge = toBeDeleted.get(toBeDeleted.size() - 1); //error
               Piece[] endEdgeSplit = endEdge.splitPiece(totalNodeLength - (totalNodeLength - deletionLength) + (index - savedIndex));
               pieces.add(savedIndex, endEdgeSplit[1]);
           }
           
           if((start.offset() - index) != 0) {
               Piece startEdge = toBeDeleted.get(0);
               Piece[] startEdgeSplit = startEdge.splitPiece(index - savedIndex);
               pieces.add(savedIndex, startEdgeSplit[0]);
           }
       }
     
	    
    
	
	public String getSequence() {
		return (this.sequenceBuffer + " Length: " + this.SequenceLength);
	}
	
	public void printpt() {
		for(Piece p: pieces) {
			
			System.out.println("Piece: " +pieces.indexOf(p)  + "   offset: "  + p.offset() +   "length: " + p.length());
		}
	}
	
	

	public static void main(String[] args) {

	
	}
}
