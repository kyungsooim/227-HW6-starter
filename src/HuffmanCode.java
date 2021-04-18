import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * This class handles building and processing huffman tree
 */
public class HuffmanCode{
    private PriorityQueue<HuffmanNode> priorityQueue;
    private HuffmanNode overallRoot;

    /**
     * Constructs a Huffman code with given array of frequency of characters
     * @param frequencies
     */
    public HuffmanCode(int[] frequencies){
        //turn frequencies into nodes and addHuffmanNode into priority queue
        priorityQueue = new PriorityQueue();
        for(int ascii = 0 ; ascii < frequencies.length ; ascii++) {
            if(frequencies[ascii] > 0) {
                priorityQueue.add(new HuffmanNode(ascii, frequencies[ascii]));
            }
        }
        buildHuffmanTree();
    }

    /**
     * Constructs Huffman Tree when given the code
     * @param input
     * pre: input has always some data and has valid form
     */
    public HuffmanCode(Scanner input){
        overallRoot = new HuffmanNode(-1);
        while(input.hasNextLine()){
            int ascii = Integer.parseInt(input.nextLine()); //should never throw the NumberFormatException
            String code = input.nextLine(); //code will be never an empty string
            addHuffmanNode(ascii, code);
        }
    }


    public void save(PrintStream output){
        //
        //TODO your code here
        //
    }


    public void translate(BitInputStream input, PrintStream output) {
        //
        //TODO your code here
        //
    }

    /**
     * Helper
     * Adds node with desired huffman code and ascii code
     * @param ascii
     * @param code
     */
    private void addHuffmanNode(int ascii, String code) {
        addHuffmanNode(ascii, code, overallRoot);
    }

    /**
     * Helper
     * Adds node according to ascii and code given
     * Starts at overallRoot
     * @param ascii
     * @param code
     * @param root
     * @return returns the root of the entire Huffman tree
     * pre: code is never an empty string always has size 1 or greater
     */
    private void addHuffmanNode(int ascii, String code, HuffmanNode root){
        if(code.length() == 1 ) {
            if(code.equals("0"))
                root.left = new HuffmanNode(ascii);
            else
                root.right = new HuffmanNode(ascii);
        }
        else{
            if(code.substring(0,1).equals("0")){
                if(root.left == null)
                    root.left = new HuffmanNode(-1);
                addHuffmanNode(ascii, code.substring(1), root.left);
            }
            else {
                if(root.right == null)
                    root.right = new HuffmanNode(-1);
                addHuffmanNode(ascii, code.substring(1), root.right);
            }
        }
    }

    /**
     * helper method to create the tree inside of the priority Queue
     * Until the current queue size is 1 (all nodes is merged to 1) build tree and set the overallRoot
     */
    private void buildHuffmanTree(){
        HuffmanNode root = null;
        while(priorityQueue.size() > 1){
            HuffmanNode min1 = priorityQueue.remove();
            HuffmanNode min2 = priorityQueue.remove();
            root = new HuffmanNode(-1, min1.frequency + min2.frequency, min1, min2);
            priorityQueue.add(root);
        }
        overallRoot = root;
    }


    /**
     * Each node represents a character and its frequency when
     * building the Huffman code
     */
    private static class HuffmanNode implements Comparable<HuffmanNode>{
        public final int ascii; //ascii being stored
        public HuffmanNode left; //least value
        public HuffmanNode right; //greater than left node, less than root node
        public final int frequency; //nodes are ordered by frequency

        /**
         * Constructs a Huffman node given the char
         * @param ascii - desired character to be stored
         * @param frequency - number of times character appears
         * @param left - node to the left
         * @param right - node to right
         */
        public HuffmanNode(int ascii, int frequency, HuffmanNode left, HuffmanNode right) {
            this.ascii = ascii;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        //Constructor sets huffman lef node with given ascii and 0 frequency
        public HuffmanNode(int ascii) {
            this(ascii, 0, null, null);
        }

        //Constructor sets huffman lef node with given ascii and given frequency
        public HuffmanNode(int ascii, int frequency){
            this(ascii, frequency, null, null);
        }

        /**
         * implemented for priority queue in Huffman code
         * @param other - Huffman node being compared
         * @return compared by frequency
         * if this frequency is less than other frequency than return >0
         * if equal return 0
         * else return < 0
         */
        @Override
        public int compareTo(HuffmanNode other){
            return this.frequency - other.frequency;
        }
    }
}