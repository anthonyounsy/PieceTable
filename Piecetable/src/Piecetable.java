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

    //TODO: Insert text into piece Table




    //TODO: Delete text from piece Table


    public void printPieces() {
        System.out.println("Piece Table Contents");
        System.out.println("---------------------");
        for (Piece p: pieces) {
            System.out.println("Pieces: " + p.getOriginalOrAppend() + " " + p.offset() + " " + p.length());
        }
    }

    public static void main(String[] args) {
        PieceTable pt = new PieceTable("Hello World");
        pt.printPieces();
        System.out.println(pt.getText());
    }
}
