import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;



public class PieceTableTest {
	
	 public static void main(String[] args) {

	 }
	
	
    @Test
    public void testInsert() {
    	PieceTable table= new PieceTable("Hello, world!");
    	table.insert(6, "everyone  ");
    	assertEquals("Hello,everyone   world!",table.getText());
    }
    
    @Test
    public void testInsertInsert() {
    	PieceTable table= new PieceTable("Hello, world!");
    	table.insert(6, "everyone  ");
    	assertEquals("Hello,everyone   world!",table.getText());
    	table.insert(5, " ");
    	assertEquals("Hello ,everyone   world!",table.getText());
    }
    
    @Test
    public void testManyInserts() {
        PieceTable table = new PieceTable("");
        StringBuilder test = new StringBuilder();
        table.insert(0, " test");
        test.insert(0, " test");
        assertEquals(test.toString(), table.getText());
        table.insert(3, " INSERT2INDEX0");
        test.insert(3, " INSERT2INDEX0");
        assertEquals(test.toString(), table.getText());
        table.insert(3, " 1234567");
        test.insert(3, " 1234567");
        assertEquals(test.toString(), table.getText());
        
        table.insert(6, " hello world");
        test.insert(6, " hello world");

        assertEquals(test.toString(), table.getText());
        
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
    	assertEquals("Hello, world!", table.getText());

    	}

	@Test
	public void testDeleteEntirePiece() {
		PieceTable table = new PieceTable("Hello, world!");
	    table.delete(6,5);
	    assertEquals("Hello,d!",table.getText());
	}

	
	 static String getAlphaNumericString(int n)
	 {
	 
	  // choose a Character random from this String
	  String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	         + "0123456789"
	         + "abcdefghijklmnopqrstuvxyz";
	 
	  // create StringBuffer size of AlphaNumericString
	  StringBuilder sb = new StringBuilder(n);
	 
	  for (int i = 0; i < n; i++) {
	 
	   // generate a random number between
	   // 0 to AlphaNumericString variable length
	   int index
	    = (int)(AlphaNumericString.length()
	      * Math.random());
	 
	   // add Character one by one in end of sb
	   sb.append(AlphaNumericString
	      .charAt(index));
	  }
	  return sb.toString();
	 }
	
	
	
	public class PerformanceTest {
		  private static final long MEGABYTE = 1024L * 1024L;

		  public static long bytesToMegabytes(long bytes) {
		    return bytes / MEGABYTE;
		  }

		  public static void main(String[] args) {
			  PieceTable table = new PieceTable("Hello, world!");
	
		   ArrayList<Piece>test = new ArrayList<Piece>();
		    for (int i = 0; i <= 100; i++) {
		      table.insert(8, getAlphaNumericString(5));
		    }
		    // Get the Java runtime
		    Runtime runtime = Runtime.getRuntime();
		    // Run the garbage collector
		    runtime.gc();
		    // Calculate the used memory
		    long memory = runtime.totalMemory() - runtime.freeMemory();
		    System.out.println("Used memory is bytes: " + memory);
		    System.out.println("Used memory is megabytes: "
		        + bytesToMegabytes(memory));
		  }		} 
	
}
