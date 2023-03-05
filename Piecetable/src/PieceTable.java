package PieceTable;

import java.util.ArrayList;

public class PieceTable {
	private String originalBuffer; // stores the original text buffer
	private int SequenceLength; // stores the length of the text
	private String addedBuffer; // stores the added text buffer
	private ArrayList<Piece> pieces; // stores the pieces of the text

	// Constructor
	public PieceTable(String originalBuffer) {
		this.originalBuffer = originalBuffer;
		this.SequenceLength = originalBuffer.length();
		this.addedBuffer = ""; // initialize as empty
		this.pieces = new ArrayList<>();
		this.pieces.add(new Piece(false, 0, originalBuffer.length()));
	}

	public void addText(String text) {
		addedBuffer += text;
		Piece newPiece = new Piece(true, SequenceLength, text.length());
		pieces.add(newPiece);
		SequenceLength += text.length();
		for (int i = pieces.indexOf(newPiece) + 1; i < pieces.size(); i++) {
			Piece piece = pieces.get(i);
			piece.setOffset(piece.offset() + text.length());
		}
	}

	public void insert(int newIndex, String newText) {
		if (newIndex > SequenceLength) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		// Add at end of document
		if (newIndex == SequenceLength) {
			addText(newText);
		}
		if (newIndex == 0) {
			// Inserting at the beginning of the document
			pieces.add(0, new Piece(true, 0, newText.length()));
			SequenceLength += newText.length();
			addedBuffer += newText;
			// Adjust the offset of the existing pieces
			for (int i = 1; i < pieces.size(); i++) {
				pieces.get(i).setOffset(pieces.get(i).offset() + newText.length());
			}
		} else {
			// Find the piece that contains the index
			Piece piece = findPiece(newIndex);
			int splitIndex = newIndex - piece.offset();
			Piece[] splitPieces = piece.splitPiece(splitIndex);
			Piece newPiece = new Piece(true, SequenceLength, newText.length());
			addedBuffer += newText;
			pieces.add(pieces.indexOf(piece), splitPieces[0]);
			pieces.add(pieces.indexOf(piece) + 1, newPiece);
			pieces.add(pieces.indexOf(piece) + 2, splitPieces[1]);

			// Update the offset of the pieces after the inserted text
			for (int i = pieces.indexOf(newPiece) + 1; i < pieces.size(); i++) {
				Piece p = pieces.get(i);
				p.setOffset(p.offset() + newText.length());
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
			if (piece.isAdded()) {
				int addedBufferOffset = piece.offset() - SequenceLength;
				if (addedBufferOffset < 0) {
					addedBufferOffset = 0;
				}
				sb.append(addedBuffer.substring(addedBufferOffset, addedBufferOffset + piece.length()));
			} else {
				sb.append(originalBuffer.substring(piece.offset(), piece.offset() + piece.length()));
			}
		}
		return sb.toString();
	}
    
    //TODO: delete text from piecetable



    //TODO: print text from piecetable
    public void printPieces() {
        System.out.println("Piece Table Contents");
        System.out.println(" Source    Offset   Length");
        System.out.println("----------------------------");
        for (Piece p: pieces) {
            String source = p.getOriginalOrAppend();
            String offset = String.format("%-6d", p.offset());
            String length = String.format("%-6d", p.length());
            String text = "";
            if (p.isAdded()) {
                int start = p.offset();
                int end = start + p.length();
                if (start >= addedBuffer.length()) {
                    // The piece is outside the bounds of the added buffer
                    continue;
                }
                if (end > addedBuffer.length()) {
                    // The piece goes beyond the end of the added buffer
                    end = addedBuffer.length();
                }
                text = addedBuffer.substring(start, end);
            } else {
                int start = p.offset();
                int end = start + p.length();
                if (start >= originalBuffer.length()) {
                    // The piece is outside the bounds of the original buffer
                    continue;
                }
                if (end > originalBuffer.length()) {
                    // The piece goes beyond the end of the original buffer
                    end = originalBuffer.length();
                }
                text = originalBuffer.substring(start, end);
            }
            System.out.printf("  %-6s   %-6d   %-6d  %s%n", source, p.offset(), p.length(), text);
        }
    }


    public static void main(String[] args) {
        PieceTable pt = new PieceTable("the quick brown fox" +  "jumped over the lazy dog");

        // Test inserting at the beginning
        pt.insert(0, "111");
        System.out.println(pt.getText());
        pt.printPieces();

        // Test inserting at the end
        pt.insert(pt.textLength, "222");
        System.out.println(pt.getText());
        pt.printPieces();

        // Test inserting multiple times
        pt.insert(6, "333");
        pt.insert(22, "444");
        System.out.println(pt.getText());
        pt.printPieces();
        System.out.println(pt.getText());
    }
}
