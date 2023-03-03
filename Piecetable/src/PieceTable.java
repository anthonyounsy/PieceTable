package PieceTable;

import java.util.ArrayList;
import PieceTable.Piece;
public class PieceTable{
    private String originalBuffer; //stores the original text buffer
    private int textLength; //stores the length of the text
    private String addedBuffer; //stores the added text buffer
    private ArrayList<Piece> pieces; //stores the pieces of the text

    //Constructor
    public PieceTable(String originalBuffer){
        this.originalBuffer = originalBuffer;
        this.textLength = originalBuffer.length();
        this.addedBuffer = ""; // initialize as empty
        this.pieces = new ArrayList<>();
        this.pieces.add(new Piece(false, 0, originalBuffer.length()));
    }


    //TODO: Returns the length of the text file
    public int length(){
        return this.textLength;
    }

    //TODO: Get text from piece table
    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (Piece p: pieces) {
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
                sb.append(addedBuffer.substring(start, end));
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
                sb.append(originalBuffer.substring(start, end));
            }
        }
        return sb.toString();
    }

    //TODO: Insert/Append text into piece Table
    public void insert(int index, String text) {
        if (index == 0) {
            // Inserting at the beginning of the document
            pieces.add(0, new Piece(true, 0, text.length()));
            addedBuffer = text + addedBuffer;
            // Adjust the offset of the existing pieces
            for (int i = 1; i < pieces.size(); i++) {
                pieces.get(i).setOffset(pieces.get(i).offset() + text.length());
            }
        } else {
            int addIndex = 0;
            for (Piece p: pieces) {
                if (addIndex + p.length() >= index) {
                    int addOffset = index - addIndex;
                    int existingPieceIndex = pieces.indexOf(p);
                    Piece existingPiece = pieces.get(existingPieceIndex);
                    int existingPieceOffset = existingPiece.offset();
                    int existingPieceLength = existingPiece.length();
                    if (addOffset == existingPieceLength && existingPiece.isAdded()) {
                        // If the piece points to the end of the added buffer and we are inserting at its end,
                        // simply increase its length
                        existingPiece.setLength(existingPieceLength + text.length());
                        addedBuffer = addedBuffer.substring(0, addedBuffer.length() - existingPieceLength) + text;
                    } else {
                        Piece newPiece = new Piece(true, addedBuffer.length(), text.length());
                        int newPieceIndex = existingPieceIndex + 1;
                        if (addOffset == 0) {
                            // If the insert is at the beginning of the piece, just add the new piece before the existing piece
                            pieces.add(newPieceIndex, newPiece);
                            // Adjust the length and offset of the existing piece
                            existingPiece.setLength(existingPieceLength - text.length());
                            existingPiece.setOffset(existingPieceOffset + text.length());
                        } else if (addOffset == existingPieceLength) {
                            // If the insert is at the end of the piece, just add the new piece after the existing piece
                            pieces.add(newPieceIndex, newPiece);
                            // Adjust the length of the existing piece
                            existingPiece.setLength(existingPieceLength - text.length());
                        } else {
                            // If the insert is in the middle of the piece, split the piece into two pieces
                            Piece before = new Piece(existingPiece.isAdded(), existingPieceOffset, addOffset);
                            Piece after = new Piece(existingPiece.isAdded(), existingPieceOffset + addOffset + text.length(), existingPieceLength - addOffset);
                            pieces.set(existingPieceIndex, before);
                            pieces.add(newPieceIndex, newPiece);
                            pieces.add(newPieceIndex + 1, after);
                            // Adjust the length of the existing piece
                            existingPiece.setLength(addOffset);
                        }
                        addedBuffer += text;
                    }
                    break;
                }
                addIndex += p.length();
            }
        }
        textLength += text.length();
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
        pt.insert(pt.length(), "222");
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
