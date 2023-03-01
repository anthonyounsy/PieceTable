public class Node {
    private int bufferIndex;
    private int start;
    int length;
    int[] lineStarts;
    private int leftSubtreeLength;
    private int leftSubtreeLeafcount;
    private Node left;
    private Node right;
    private Node parent;


    public Node(int bufferIndex, int start, int length, int[] lineStarts, int leftSubtreeLength, int leftSubtreeLeafcount, Node left, Node right, Node parent) {
        this.bufferIndex = bufferIndex;
        this.start = start;
        this.length = length;
        this.lineStarts = lineStarts;
        this.leftSubtreeLength = leftSubtreeLength; //Length of text
        this.leftSubtreeLeafcount = leftSubtreeLeafcount; //line break count
        this.left = left;
        this.right = right;
        this.parent = parent;

    }
}