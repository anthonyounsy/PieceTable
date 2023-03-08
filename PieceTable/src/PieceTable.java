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
			SequenceLength += newText.length();
			sequenceBuffer = newText + sequenceBuffer;
		}
		if(newIndex == SequenceLength) {
			//Insert at the end of the file
			pieces.add(new Piece(SequenceLength, newText.length()-1));
			sequenceBuffer += newText;
			SequenceLength += newText.length();
		}
		
		else {
			//Find the piece that contains the index
			Piece piece = findPiece(newIndex);
			//if the new piece is inserted at the end of an existing node
			if(piece.length() == newIndex) {
				pieces.add(new Piece(SequenceLength, newText.length()));
				SequenceLength += newText.length();
				sequenceBuffer += newText;
			}
	        int splitIndex = newIndex - piece.offset();
	        if(splitIndex > 0 && splitIndex < piece.length()) {
	        	//Update the piece by splitting the pieces at its index
				Piece[] splitPieces = piece.splitPiece(splitIndex);			
				Piece p1 = splitPieces[0];
				Piece p2 = splitPieces[1];
				System.out.println("offset: "  + p1.offset() +   "length: " + p1.length());
				System.out.println("offset: "  + p2.offset() +   "length: " + p2.length());
				//update current piece
				piece.setPiece(splitPieces[0]);
				//update split node
				pieces.add(pieces.indexOf(piece)+1, new Piece(SequenceLength, newText.length()-2));
				
				pieces.add(pieces.indexOf(piece) + 2, splitPieces[1]);
			
				//insert new piece

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
	        int runningTotalLength = 0;
	      
	        for (Piece piece : pieces) {
	                if(pieces.indexOf(piece) == 0) {
	                    sb.append(sequenceBuffer.substring(piece.offset(), piece.length() + piece.offset())) ;
	                    //runningTotalLength +=piece.offset();
	             
	                }
	                else{
	                    sb.append(sequenceBuffer.substring(piece.offset(), piece.length() + piece.offset()));
	                    //runningTotalLength +=piece.offset();
	                    
	                }
	        }
	        return sb.toString();        
	    }
	   
	     public void delete(int index, int deletionLength)
	      {
	          if (deletionLength == 0)
	              return;
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
	
	private void printpt() {
		for(Piece p: pieces) {
			
			System.out.println("Piece: " +pieces.indexOf(p)  + "   offset: "  + p.offset() +   "length: " + p.length());
		}
	}
	
	

	public static void main(String[] args) {
		//PieceTable pt = new PieceTable("the quick brown fox" + "jumped over the lazy dog");
		PieceTable pt = new PieceTable("Hello World");
		pt.getText();
		
		// Test inserting at the beginning
		//pt.insert(0, "111");
		//System.out.println(pt.getText());
		//pt.printPieces();
		//System.out.println(pt.getSequence());
		//pt.printpt();
		// Test inserting at the end
		//pt.insert(pt.SequenceLength, "222");
		//System.out.println(pt.getText());
		//pt.printPieces();

		// Test inserting multiple times
		pt.insert(5, " Everyone");
		//pt.insert(22, "444");
		//System.out.println(pt.getText());
		pt.printpt();
		System.out.println(pt.getSequence());
		//System.out.println(pt.getText());
	
	}
}
