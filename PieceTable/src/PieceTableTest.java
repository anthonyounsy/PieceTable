
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
        PieceTable table = new PieceTable("Hello, world!");
        StringBuffer insert1 = new StringBuffer("everyone ");
        table.insert(6, insert1.toString());
        assertEquals("Hello, everyone world!", table.getText());
    }
    
    @Test
   public void testDelete() {
        PieceTable table = new PieceTable("Hello, world!");
 //       table.delete(0, 5);
        //assertEquals(", world!", table.getText());
    }

    @Test
    public void testInsertAndDelete() {
        PieceTable table = new PieceTable("Hello, world!");
        table.insert(7, "everyone ");
        //table.delete(0, 5);
        assertEquals("everyone world!", table.getText());
    }
//
    @Test
    public void testGetText() throws IOException {
          PieceTable table = new PieceTable("Hello, world!");
        assertEquals("Hello, world!", table.getText());
    }
   //
}