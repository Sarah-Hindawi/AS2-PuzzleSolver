import java.util.*;

public class GameTree {

    //The original board
    private Board board;
    //the required depth
    private int depth;
    //the boards as string in DFS
    private String boards = "";

    /**
     * @param initial the root board
     * @param depth   the level of children
     */
    public GameTree(Board initial, int depth) {
        board = initial;
        this.depth = depth;
    }

    /**
     * @return a String representation of the children of the root node using Breadth First Search
     */
    public String BFS() {
        //string that will be returned
        String str = "";
        //queue that will be used to perform searching upon
        Deque<Board> q = new LinkedList<>();
        //adding the root node to the queue
        q.add(board);
        while (!q.isEmpty() && depth >= 0) {
            //removing it to enqueue its children
            Board p = q.removeFirst();
            //storing the removed board in the string
            str = str + p.toString() + "\n";
            //an iterable of the children
            Iterable<Board> l = p.neighbors();
            depth--;
            //enqueuing the children to the queue
            for (Board bb : l) q.addLast(bb);
        }
        return str;
    }


    /**
     * @return a String of the children of the root node using Depth First Search
     */
    public String DFS(Board p, int currentDepth) {
        boards = boards + p.toString();
        //printing the board
        System.out.println(p.toString());
        //if it is not the required depth yet
        if (currentDepth != depth) {
            //get the children in the next depth
            Iterable<Board> l = p.neighbors();
            for (Board child : l) {
                //recursive call for every child
                DFS(child, currentDepth + 1);
            }
        }
        return boards;
    }
}
