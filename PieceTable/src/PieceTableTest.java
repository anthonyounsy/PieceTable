
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class PieceTableTest {
	
	 public static void main(String[] args) throws IOException {

	 }
	
	
    @Test
	
    public void testInsert() {
    	PieceTable table= new PieceTable("Hello, world!");
    	table.insert(6, "everyone  ");
    	assertEquals("Hello,everyone   world!",table.getText());
    }
    @Test
    public void testDelete() {
//      PieceTable table = new PieceTable("Hello, world!");
//      table.delete2(0, 5);
//      assertEquals(", world!", table.getText());
      
      PieceTable table = new PieceTable("Hello, world!");
      table.delete(1, 5);
      table.getText();
      assertEquals("H world!", table.getText());
  }
    
    
    @Test
    public void testDeleteAll() {
    	PieceTable table=new PieceTable("Hello, world");
    	table.delete(0, 6);
    	assertEquals(" world",table.getText());
    }

 
    
    @Test
     public void testGet() {
    	PieceTable table = new PieceTable("Hello, world!");
    	assertEquals("Hello, world!",table.getText()); 
    
    }
    @Test
    public void  testInsertAtEnd() {
    	PieceTable table = new PieceTable("Hello, ");
    	table.insert(7, "world!");
    	assertEquals("Hello, world!", table.getText());
    }
    
    @Test
    public void testInsertAndDelete() {
        PieceTable table = new PieceTable("Hello, world!");
        table.insert(6, "everyone");
        table.delete(0,6);
        assertEquals("everyone world!", table.getText());
    }

    @Test
    public void testInsertAtBeginning() {
    	PieceTable table= new PieceTable("world!");
    	table.insert(0, "Hello, ");
    	System.out.println(table.getSequence()); 
    	assertEquals("Hello, world!", table.getText());

    	}


	@Test
	public void testDeleteEntirePiece() {
		PieceTable table = new PieceTable("Hello, world!");
	    table.delete(6,5);
	    assertEquals("Hello,d!",table.getText());
	}
    
   
}
