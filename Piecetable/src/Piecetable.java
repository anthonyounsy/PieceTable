package piecetable;

import java.util.ArrayList;
import piecetable.Piece;
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
                sb.append(addedBuffer.substring(p.offset(), p.offset() + p.length()));
            } else {
                sb.append(originalBuffer.substring(p.offset(), p.offset() + p.length()));
            }
        }
        return sb.toString();
    }

    //TODO: Insert/Append text into piece Table
    public void insert(int index, String text) {
        int addIndex = 0;
        for(Piece p: pieces) {
            if(addIndex + p.length() >= index) {
                int addOffSet = index - addIndex;
                pieces.add(pieces.indexOf(p) + 1, new Piece(true, addedBuffer.length(), text.length()));
                p.setLength(addOffSet, p);
                break;
            }
            addIndex += p.length();
        }
        pieces.add(pieces.indexOf(pieces.get(pieces.size() - 1)) + 1, new Piece(true,originalBuffer.length(), text.length()));
    }




    //TODO: Delete text from piece Table
    public void printPieces() {
        System.out.println("Piece Table Contents");
        System.out.println(" Source    Offset   Length");
        System.out.println("----------------------------");
        for (Piece p: pieces) {
            String source = p.getOriginalOrAppend();
            String offset = String.format("%-6d", p.offset());
            String length = String.format("%-6d", p.length());
            System.out.println(String.format("  %-6s   %-6s   %-6s  ", source, offset, length));;
        }
    }

    public static void main(String[] args) {
        PieceTable pt = new PieceTable("the quick brown fox\n" +  "jumped over the lazy dog");
        pt.printPieces();
        System.out.println(pt.getText());
        pt.insert(11, "!");
        pt.printPieces();
        System.out.println(pt.getText());
    }
}
