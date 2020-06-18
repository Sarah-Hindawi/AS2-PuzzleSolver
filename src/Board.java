import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    //the original 2D array
    private int[][] array2D;
    //the array distributed in 1D so that dealing with it would be easier
    private int[] array;
    //the number of squares in each row and column
    private int size;
    //the level of the board
    private int depth;
    //the path of the parents boards
    private String path;

    

    /**
     * assigning the instance fields with values
     *
     * @param aa the original state of the board as a 2D array
     */
    public Board(int[][] aa) {
        //assigning the instance field with the passed original state
        array2D = aa;
        //instantiating the array of length equal to the number of elements in the 2d array
        array = new int[aa.length * aa.length];
        size = aa.length;
        plainArray();
    }

    /**
     * a method will be used when iterating through the neighbours
     */
    public Board(int[] aa) {
        array = aa;
        //defining the size depending on the total number of elements
        if (array.length == 25) size = 5;
        else if (array.length == 16) size = 4;
        else if (array.length == 9) size = 3;
        else size = 2;
    }

    /**
     * converting the 2d array into 1d array
     *
     * @return the 1D representation of the array
     */
    public int[] plainArray() {
        int index = 0;
        //filling the cells of the array with the elements
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < array2D[i].length; j++) {
                array[index] = array2D[i][j];
                index++;
            }
        }
        return array;
    }

    /**
     * @return the number of the elements in each row and column
     */
    public int size() {
        return size;
    }

    /**
     * @return the board's state as a string
     */
    public String toString() {
        String str = "";
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            //move to the next row if the element is on the edge
            if (index == size - 1) {
                str = str + " " + array[i] + "\n";
                index = 0;
            } else {
                str = str + " " + array[i];
                index++;
            }
        }
        return str;
    }

    /**
     * @param row the required row
     * @param col the required column
     * @return the number stored in that position of the board
     */

    public int tileAt(int row, int col) {
        //if the arguments do not represent indexes of a cell in the board
        if (row < 0 || row >= size || col < 0 || col >= size)
            throw new java.lang.IllegalArgumentException("Invalid input");
        int index = row * size + col;
        return array[index];
    }

    /**
     * @return true if the board is in the final goal state, false otherwise
     */
    public boolean isGoal() {
        //if the last element in the grid is not zero it is not the final state
        if (array[array.length - 1] != 0) return false;
        //go through ehe elements and check if they are in the correct order
        for (int i = 1; i < array.length - 2; i++) {
            //is the element is bigger than its previous by one
            if (array[i] != (array[i + 1] - 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param o the passed object that will be compared to this instance of board
     * @return true if they are equivalent, false otherwise
     */
    public boolean equals(Object o) {
        //is the object passed is a board object
        if (o instanceof Board) {
            //safe casting
            Board b = (Board) o;
            //is the board passed have the same number of elements
            if (b.size() != size)
                return false;
            //are the elements equal to the instance board
            return (Arrays.equals(array, b.plainArray()));
        } else return false;
    }

    /**
     * @return an iterable object (ArrayList) holding the neighbors of the current board
     */
    public Iterable<Board> neighbors() {
        //the Iterable that will be returned
        List<Board> l = new ArrayList<>();
        //creating a copy of the array
        int[] aa = Arrays.copyOf(array, array.length);
        //index of the 0 element
        int index = spaceIndex(array);

        //check for upper tile
        int indexUpper = index - size;
        //if it is not in the first row
        if (hasTile(indexUpper, "up")) {
            //swap the numbers between the cell that holds the zero and the cell above it
            swap(index, indexUpper, aa);
            //creating a board with the new array
            Board b = new Board(aa);
            //adding the new board into the list
            l.add(b);
        }

        //making the array hold the numbers that were in the original state, not the modified
        aa = Arrays.copyOf(array, array.length);
        //check for lower tile
        int indexLower = index + size;
        if (hasTile(indexLower, "down")) {
            swap(index, indexLower, aa);
            Board b = new Board(aa);
            l.add(b);
        }
        aa = Arrays.copyOf(array, array.length);
        //check for left tile
        int indexLeft = index - 1;
        if (hasTile(index, "left")) {
            swap(index, indexLeft, aa);
            Board b = new Board(aa);
            l.add(b);
        }

        aa = Arrays.copyOf(array, array.length);
        //check for right tile
        int indexRight = index + 1;
        if (hasTile(indexRight, "right")) {
            swap(index, indexRight, aa);
            Board b = new Board(aa);
            l.add(b);
        }
        return l;
    }

    /**
     * @param index     the index of the cell that will be checked if it exists
     * @param direction above,bellow, or in the sides
     * @return true if the cell exists, false otherwise
     */
    private boolean hasTile(int index, String direction) {
        //is it in the first row
        if (direction.equals("up")) return (index >= 0);
            //is it in the last row
        else if (direction.equals("down")) return (index < array.length);
            //is it at the edges
        else return (index % size != 0);
    }

    /**
     * @param index1 the index of the first cell that will be swapped
     * @param index2 the index of the second cell that will be swapped
     * @param aa     the array that the operations will be performed upon
     */
    private void swap(int index1, int index2, int[] aa) {
        int temp = aa[index1];
        aa[index1] = aa[index2];
        aa[index2] = temp;
    }

    /**
     * @param aa the array that the zero will be searched in
     * @return the index of zero in the array
     */
    private int spaceIndex(int[] aa) {
        //iterate through the array until the zero is found
        for (int i = 0; i < aa.length; i++) {
            if (aa[i] == 0) return i;
        }
        return 0;
    }

    /**
     *
     * @param path setting the path that is passed from BestFirstSearch to the instance path
     */
    public void setPath(String path) {
        this.path = path;
    }


    public String UpdatePathToStartState() {
        return path;
    }

    /**
     *
     * @param depth setting the depth that is passed from BestFirstSearch to the instance depth
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     *
     * @return returning the depth of the board to use it as a priority key in Breadth search
     */
    public int getDepth() {
        return depth;
    }

    /**
     *
     * @param b the board that will be calculated upon
     * @return the number of tiles that are not in their position
     */
    public int misplacedTiles(Board b) {
        int[] board = b.array;
        int count = 0;
        //go through the tiles of the board
        for (int i = 0; i < board.length - 1; i++) {
            //if they are not in the right position
            if (board[i] != i + 1) count++;
        }
        //if the last cell is not 0
        if (board[board.length - 1] != 0) count++;
        return count;
    }


    public static void main(String[] args) {
        System.out.println("The first puzzle:");
        //the tiles of the first puzzle
        int[][] array = {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};
        //a new instance of Board that will hold the previous puzzle array
        Board board = new Board(array);
        //a new instance of BestFirstSearch that the methods will call upon
        BestFirstSearch breadthSearch = new BreadthFirstSearch();
        //calling the Run method from BreadthSearch to solve the puzzle in the given time period
        breadthSearch.Run(2000, board).UpdatePathToStartState();
        System.out.println(" Solving using breadth search:\n");
        System.out.println(breadthSearch.Run(2000, board).UpdatePathToStartState());
        //calling the Run method from HeuristicSearch to solve the puzzle in the given time period
        BestFirstSearch heuristicSearch = new HeuristicSearch();
        System.out.println(" Solving using heuristic search:\n");
        System.out.println(heuristicSearch.Run(2000, board).UpdatePathToStartState());

        System.out.println("The second puzzle:");
        int[][] array2 = {{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};
        Board board2 = new Board(array2);
        System.out.println(" Solving using breadth search:\n");
        System.out.println(breadthSearch.Run(2000, board2).UpdatePathToStartState());
        System.out.println(" Solving using heuristic search:\n");
        System.out.println(heuristicSearch.Run(2000, board2).UpdatePathToStartState());

        System.out.println("The third puzzle:");
        int[][] array3 = {{0, 8, 6}, {2, 7, 5}, {1, 3, 4}};
        Board board3 = new Board(array3);
        Board bs = breadthSearch.Run(2000, board3);
        if (bs != null) {
            System.out.println(" Solving using breadth search:\n");
            System.out.println(bs.UpdatePathToStartState());
        }
        Board hs = heuristicSearch.Run(2000, board3);
        if (hs != null) {
            System.out.println(" Solving using heuristic search:\n");
            System.out.println(hs.UpdatePathToStartState());
        }
    }
}
